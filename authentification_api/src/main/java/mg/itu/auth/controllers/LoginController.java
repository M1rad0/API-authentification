package mg.itu.auth.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import mg.itu.auth.models.Utilisateur;
import mg.itu.auth.service.LoginService;
import jakarta.mail.MessagingException;
import jakarta.validation.constraints.NotBlank;

@RestController
@RequestMapping("/login")
public class LoginController {

    private final LoginService loginService;

    public LoginController(LoginService loginService) {
        this.loginService = loginService;
    }

    @PostMapping
    public ResponseEntity<String> login(@RequestParam @NotBlank String emailOrIdentifiant,
                                        @RequestParam @NotBlank String motDePasse) throws MessagingException {
        String token = loginService.login(emailOrIdentifiant, motDePasse);
        return ResponseEntity.ok(token);
    }

    @PostMapping("/sendCodeLogin")
    public ResponseEntity<String> sendCodeLogin(@RequestHeader("Authorization") String bearerToken,
                                                @RequestParam @NotBlank int codeValidation) {
        String tokenValue = bearerToken.replace("Bearer ", "");
        loginService.validerToken(tokenValue, codeValidation);
        return ResponseEntity.ok("Connexion validée !");
    }

    @GetMapping("/verifyConnection")
    public ResponseEntity<Utilisateur> verifyConnection(@RequestHeader("Authorization") String bearerToken) {
        String tokenValue = bearerToken.replace("Bearer ", "");
        Utilisateur result = loginService.verifyConnection(tokenValue);

        return ResponseEntity.ok(result);
    }

    @GetMapping("/destroyToken")
    public ResponseEntity<String> destroyConnection(@RequestHeader("Authorization") String bearerToken) {
        String tokenValue = bearerToken.replace("Bearer ", "");
        
        loginService.destroyToken(tokenValue);

        return ResponseEntity.ok("Token détruit avec succès");
    }
}
