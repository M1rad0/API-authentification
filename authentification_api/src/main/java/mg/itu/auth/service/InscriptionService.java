package mg.itu.auth.service;

import org.springframework.stereotype.Service;

import mg.itu.auth.exceptions.CannotFindUserException;
import mg.itu.auth.exceptions.EmailAlreadyUsedException;
import mg.itu.auth.exceptions.IdentifiantAlreadyUsedException;
import mg.itu.auth.exceptions.InvalidValidationCodeException;
import mg.itu.auth.models.Utilisateur;
import mg.itu.auth.repositories.UtilisateurRepository;

import jakarta.mail.MessagingException;
import jakarta.transaction.Transactional;

@Service
public class InscriptionService {
    private final UtilisateurRepository utilisateurRepository;
    private final PasswordEncryptionService passwordEncryptionService;
    private final MailService mailService;
    private final ValidationCodeService validationCodeService;

    public InscriptionService(
            UtilisateurRepository utilisateurRepository,
            PasswordEncryptionService passwordEncryptionService,
            MailService mailService,
            ValidationCodeService validationCodeService
    ) {
        this.utilisateurRepository = utilisateurRepository;
        this.passwordEncryptionService = passwordEncryptionService;
        this.mailService = mailService;
        this.validationCodeService = validationCodeService;
    }

    @Transactional
    public void inscrire(String email, String identifiant, String motDePasse) throws MessagingException {
        // Vérification si l'utilisateur existe déjà
        if (utilisateurRepository.existsByEmail(email)) {
            throw new EmailAlreadyUsedException("Email déjà utilisé.");
        }

        if(utilisateurRepository.existsByIdentifiant(identifiant)){
            throw new IdentifiantAlreadyUsedException("Identifiant déjà utilisé");
        }

        // Hachage du mot de passe
        String motDePasseCrypte = passwordEncryptionService.crypt(motDePasse);

        // Génération d'un code de validation
        int codeValidation = validationCodeService.generateValidationCode();

        // Création et enregistrement de l'utilisateur
        Utilisateur utilisateur = new Utilisateur(identifiant, email, motDePasseCrypte, false, codeValidation);
        utilisateurRepository.save(utilisateur);

        // Envoi du code de validation par mail
        mailService.sendCodeValidationMail(email, codeValidation);
    }

    @Transactional
    public void validateInscription(String email, int codeValidation) {
        Utilisateur utilisateur = utilisateurRepository.findByEmail(email)
                .orElseThrow(() -> new CannotFindUserException("Utilisateur non trouvé"));

        if (utilisateur.getCodeValidation() == codeValidation) {
            utilisateur.setValide(true);
            utilisateurRepository.save(utilisateur);
        } else {
            throw new InvalidValidationCodeException("Code de validation incorrect");
        }
    }
}