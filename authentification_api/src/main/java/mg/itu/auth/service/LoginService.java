package mg.itu.auth.service;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import jakarta.mail.MessagingException;
import jakarta.transaction.Transactional;
import mg.itu.auth.exceptions.CannotFindUserException;
import mg.itu.auth.exceptions.ExpiredTokenException;
import mg.itu.auth.exceptions.InvalidLoginCodeException;
import mg.itu.auth.exceptions.InvalidTokenException;
import mg.itu.auth.exceptions.LoginNotValideException;
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
    }

    @Transactional
    public String login(String emailOrIdentifiant, String motDePasse) throws MessagingException {
        Optional<Utilisateur> utilisateurOpt = utilisateurRepository.findByEmailOrIdentifiant(emailOrIdentifiant, emailOrIdentifiant);

        if (utilisateurOpt.isEmpty()) {
            throw new CannotFindUserException("Identifiant ou mot de passe incorrect");
        }

        Utilisateur utilisateur = utilisateurOpt.get();

        if (!passwordEncoder.matches(motDePasse, utilisateur.getMotDePasse())) {
            throw new CannotFindUserException("Identifiant ou mot de passe incorrect");
        }

        if (!utilisateur.isValide()) {
            throw new LoginNotValideException("Veuillez valider votre inscription par mail");
        }

        // Générer un token
        Token token=tokenService.createToken(utilisateur);

        // Envoyer le code de validation par mail
        mailService.sendCodeValidationLogin(utilisateur.getEmail(), token.getCodeValidation());

        return token.getValeur();
    }

    @Transactional
    public void validerToken(String tokenValue, int codeValidation) {
        Token token = tokenRepository.findByTokenValue(tokenValue)
                .orElseThrow(() -> new InvalidTokenException("Token invalide"));

        if (token.getCodeValidation() != codeValidation) {
            throw new InvalidLoginCodeException("Code de validation incorrect");
        }

        token.setValide(true);
        tokenRepository.save(token);
    }

    @Transactional
    public Utilisateur verifyConnection(String tokenValue) {
        Optional<Token> tokenOpt = tokenRepository.findByTokenValue(tokenValue);

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

    @Transactional
    public void destroyToken(String tokenValue) {
        Optional<Token> tokenOpt = tokenRepository.findByTokenValue(tokenValue);

        if (tokenOpt.isEmpty()) {
            throw new InvalidTokenException("Token invalide ou inexistant");
        }

        Token token = tokenOpt.get();
        token.setLogged(false);
        tokenRepository.save(token);
    }

}