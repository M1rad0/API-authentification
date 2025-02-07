package mg.itu.auth.service;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class PasswordEncryptionService {

    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    // Fonction pour crypter un mot de passe
    public String crypt(String password) {
        return passwordEncoder.encode(password);
    }

    // Fonction pour comparer un mot de passe avec un hachage
    public boolean compareWithCrypted(String toCompare, String crypted) {
        return passwordEncoder.matches(toCompare, crypted);
    }
}