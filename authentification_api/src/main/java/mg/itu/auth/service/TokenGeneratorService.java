package mg.itu.auth.service;

import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.util.Base64;

@Service
public class TokenGeneratorService {
    
    private static final int TOKEN_LENGTH = 32; // Longueur en bytes

    public String generateToken() {
        byte[] randomBytes = new byte[TOKEN_LENGTH];
        new SecureRandom().nextBytes(randomBytes); // Génère un token aléatoire sécurisé
        return Base64.getUrlEncoder().withoutPadding().encodeToString(randomBytes); // Encode en Base64
    }
}