package mg.itu.auth.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import jakarta.mail.MessagingException;
import jakarta.transaction.Transactional;
import mg.itu.auth.exceptions.CannotFindUserException;
import mg.itu.auth.exceptions.ExpiredTokenException;
import mg.itu.auth.exceptions.InvalidCodeException;
import mg.itu.auth.exceptions.InvalidPasswordException;
import mg.itu.auth.exceptions.InvalidTokenException;
import mg.itu.auth.exceptions.LoginNotValideException;
import mg.itu.auth.exceptions.TokenDestroyedException;
import mg.itu.auth.exceptions.TooManyAttemptsException;
import mg.itu.auth.exceptions.UnverifiedTokenException;
import mg.itu.auth.models.Token;
import mg.itu.auth.models.Utilisateur;
import mg.itu.auth.repositories.TokenRepository;
import mg.itu.auth.repositories.UtilisateurRepository;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class LoginService {

    private final UtilisateurRepository utilisateurRepository;
    private final TokenRepository tokenRepository;
    private final TokenService tokenService;
    private final MailService mailService;
    private final BCryptPasswordEncoder passwordEncoder;
    private final ValidationCodeService validationCodeService;
    @Value("${security.nbTentativesMax}")
    private int nbTentativesMax;

    public LoginService(UtilisateurRepository utilisateurRepository,
                        TokenRepository tokenRepository,
                        TokenService tokenService,
                        MailService mailService,
                        ValidationCodeService validationCodeService) {
        this.utilisateurRepository = utilisateurRepository;
        this.tokenRepository = tokenRepository;
        this.tokenService = tokenService;
        this.mailService = mailService;
        this.passwordEncoder = new BCryptPasswordEncoder();
        this.validationCodeService = validationCodeService;
    }

    public String login(String emailOrIdentifiant, String motDePasse) throws MessagingException {
        Optional<Utilisateur> utilisateurOpt = utilisateurRepository.findByEmailOrIdentifiant(emailOrIdentifiant, emailOrIdentifiant);

        if (utilisateurOpt.isEmpty()) {
            throw new CannotFindUserException("Identifiant inconnu");
        }

        Utilisateur utilisateur = utilisateurOpt.get();

        if (!utilisateur.isValide()) {
            throw new LoginNotValideException("Veuillez valider votre inscription par mail");
        }

        if (!passwordEncoder.matches(motDePasse, utilisateur.getMotDePasse())) {
            utilisateur.setNbTentativePassWord(utilisateur.getNbTentativePassWord() + 1);
            utilisateurRepository.save(utilisateur);
            utilisateurRepository.flush();

            if (utilisateur.getNbTentativePassWord() >= nbTentativesMax) {
                utilisateur.setValide(false);
                utilisateur.setCodeValidation(validationCodeService.generateValidationCode());
                utilisateurRepository.save(utilisateur);
                utilisateurRepository.flush();

                mailService.sendCodeValidationMail(utilisateur.getEmail(), utilisateur.getCodeValidation());
                throw new TooManyAttemptsException("Compte verrouillé. Réactiver le mail.");
            }

            int nbTentativesRestantes=nbTentativesMax-utilisateur.getNbTentativePassWord();
            throw new InvalidPasswordException("Mot de passe incorrect.",nbTentativesRestantes);
        }

        // Réinitialiser les tentatives en cas de succès
        utilisateur.setNbTentativePassWord(0);
        utilisateurRepository.save(utilisateur);
        utilisateurRepository.flush();

        // Générer un token
        Token token=tokenService.createToken(utilisateur);

        // Envoyer le code de validation par mail
        mailService.sendCodeValidationLogin(utilisateur.getEmail(), token.getCodeValidation());

        return token.getValeur();
    }

    public void validerToken(String tokenValue, int codeValidation) {
        Token token = tokenRepository.findByValeur(tokenValue)
                .orElseThrow(() -> new InvalidTokenException("Token invalide"));

        if (token.getCodeValidation() != codeValidation) {
            System.err.println("TOKEN CODE :"+token.getCodeValidation());
            System.err.println("WRITTEN CODE :"+codeValidation);
            token.setNbTentative(token.getNbTentative()+1);
            tokenRepository.save(token);
            tokenRepository.flush();
            
            if(token.getNbTentative()>=nbTentativesMax){
                token.setLogged(false);
                throw new TokenDestroyedException("Trop de tentatives, token détruit");
            }
            
            int nbTentativesRestantes=nbTentativesMax-token.getNbTentative();
            throw new InvalidCodeException("Code de validation incorrect",nbTentativesRestantes);
        }

        token.setValide(true);
        tokenRepository.save(token);
        tokenRepository.flush();
    }

    @Transactional
    public Utilisateur verifyConnection(String tokenValue) {
        Optional<Token> tokenOpt = tokenRepository.findByValeur(tokenValue);

        if (tokenOpt.isEmpty()) {
            throw new InvalidTokenException("Token invalide ou inexistant");
        }

        Token token = tokenOpt.get();

        if (!token.isValide()) {
            throw new UnverifiedTokenException("Session non vérifiée");
        }

        if (token.getExpiration().isBefore(LocalDateTime.now()) || !token.isLogged()) {
            throw new ExpiredTokenException("Session expirée ou déconnectée");
        }

        // Récupérer l'utilisateur associé
        Utilisateur utilisateur = token.getUtilisateur();

        // Retourner un nouvel objet contenant uniquement les données publiques
        return new Utilisateur(utilisateur.getId(), utilisateur.getIdentifiant(), utilisateur.getEmail());  
    }

    public void destroyToken(String tokenValue) {
        Optional<Token> tokenOpt = tokenRepository.findByValeur(tokenValue);

        if (tokenOpt.isEmpty()) {
            throw new InvalidTokenException("Token invalide ou inexistant");
        }

        Token token = tokenOpt.get();
        token.setLogged(false);
        tokenRepository.save(token);
        tokenRepository.flush();
    }
}