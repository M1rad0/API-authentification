package mg.itu.auth.controllers;

import jakarta.mail.MessagingException;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import mg.itu.auth.service.InscriptionService;

@RestController
@RequestMapping("/inscription")
public class InscriptionController {

    private final InscriptionService inscriptionService;

    public InscriptionController(InscriptionService inscriptionService) {
        this.inscriptionService = inscriptionService;
    }

    @PostMapping
    public ResponseEntity<String> inscrire(@RequestParam(required=true) String email,
                                           @RequestParam(required=true) String identifiant,
                                           @RequestParam(required=true) String motDePasse) throws MessagingException {
        inscriptionService.inscrire(email, identifiant, motDePasse);
        return ResponseEntity.ok("Inscription réussie. Vérifiez votre email pour valider votre compte.");
    }

    @PostMapping("/valider")
    public ResponseEntity<String> validateInscription(@RequestParam(required=true) String email,
                                                      @RequestParam(required=true) int codeValidation) {
        inscriptionService.validateInscription(email, codeValidation);
        return ResponseEntity.ok("Inscription validée avec succès.");
    }
}