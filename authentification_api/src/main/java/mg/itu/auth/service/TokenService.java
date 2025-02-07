package mg.itu.auth.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import mg.itu.auth.models.Token;
import mg.itu.auth.models.Utilisateur;
import mg.itu.auth.repositories.TokenRepository;

import java.time.LocalDateTime;

@Service
public class TokenService {

    private final TokenRepository tokenRepository;
    private final TokenGeneratorService tokenGeneratorService;
    private final ValidationCodeService validationCodeService;
    @Value("${token.lifespan}")
    private int tokenLifeSpan;

    public TokenService(TokenRepository tokenRepository, TokenGeneratorService tokenGeneratorService, ValidationCodeService validationCodeService) {
        this.tokenRepository = tokenRepository;
        this.tokenGeneratorService = tokenGeneratorService;
        this.validationCodeService = validationCodeService;
    }

    @Transactional
    public Token createToken(Utilisateur utilisateur) {
        String tokenValue = tokenGeneratorService.generateToken();

        Token token = new Token();
        token.setValeur(tokenValue);
        token.setUtilisateur(utilisateur);
        token.setCodeValidation(validationCodeService.generateValidationCode());
        token.setValide(false); // Non valide au départ
        token.setLogged(true);
        token.setExpiration(LocalDateTime.now().plusMinutes(tokenLifeSpan));

        return tokenRepository.save(token);
    }
}

