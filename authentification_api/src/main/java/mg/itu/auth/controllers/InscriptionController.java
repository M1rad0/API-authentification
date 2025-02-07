package mg.itu.auth.controllers;

import jakarta.mail.MessagingException;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import mg.itu.auth.service.InscriptionService;

@Tag(name = "Fonctionnalités d'inscription", description = "Ensemble des fonctionnalités qui gèrent l'inscription")
@RestController
@RequestMapping("/inscription")
public class InscriptionController {

    private final InscriptionService inscriptionService;

    public InscriptionController(InscriptionService inscriptionService) {
        this.inscriptionService = inscriptionService;
    }

    @Operation(summary = "Inscription", description = "Inscrit un utilisateur avec les paramètres donnés")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Inscription réussie"),
        @ApiResponse(responseCode = "400", description = "Email ou identifiant déjà utilisé")
    })
    @PostMapping
    public ResponseEntity<String> inscrire(@RequestParam(required=true) String email,
                                           @RequestParam(required=true) String identifiant,
                                           @RequestParam(required=true) String motDePasse) throws MessagingException {
        inscriptionService.inscrire(email, identifiant, motDePasse);
        return ResponseEntity.ok("Inscription réussie. Vérifiez votre email pour valider votre compte.");
    }

    @Operation(summary = "Valider Inscription", description = "Envoyer code de validation pour le mail donné")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Validation réussie"),
        @ApiResponse(responseCode = "400", description = "Code invalide"),
        @ApiResponse(responseCode = "400", description = "Email introuvable")
    })
    @PostMapping("/valider")
    public ResponseEntity<String> validateInscription(@RequestParam(required=true) String email,
                                                      @RequestParam(required=true) int codeValidation) {
        inscriptionService.validateInscription(email, codeValidation);
        return ResponseEntity.ok("Inscription validée avec succès.");
    }
}