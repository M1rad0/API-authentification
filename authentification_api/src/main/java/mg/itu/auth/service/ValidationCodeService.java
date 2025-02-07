package mg.itu.auth.service;

import org.springframework.stereotype.Service;
import java.security.SecureRandom;

@Service
public class ValidationCodeService {
    private static final int CODE_LENGTH = 4;
    private static final SecureRandom random = new SecureRandom();

    public int generateValidationCode() {
        int min = (int) Math.pow(10, CODE_LENGTH - 1); // 100000
        int max = (int) Math.pow(10, CODE_LENGTH) - 1; // 999999
        return random.nextInt(max - min + 1) + min; // Génère un nombre entre 100000 et 999999
    }
}