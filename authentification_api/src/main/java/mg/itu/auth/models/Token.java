package mg.itu.auth.models;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "Token")
public class Token {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_token")
    private Long id;

    @Column(name = "token_value", nullable = false, unique = true, length = 255)
    private String valeur;

    @Column(name = "code_validation")
    private Integer codeValidation;

    @Column(name = "login_valide", nullable = false)
    private boolean isValide;

    @Column(name = "date_expiration", nullable = false)
    private LocalDateTime expiration;

    @Column(name = "is_logged", nullable = false)
    private boolean isLogged;

    @Column(name = "nbTentative")
    private Integer nbTentative = 0;

    @ManyToOne
    @JoinColumn(name = "id_utilisateur", nullable = false)
    private Utilisateur utilisateur;

    public Token() {}

    public Token(String valeur, Integer codeValidation, boolean isValide, boolean isLogged, LocalDateTime expiration, Utilisateur utilisateur) {
        this.valeur = valeur;
        this.codeValidation = codeValidation;
        this.isValide = isValide;
        this.expiration = expiration;
        this.isLogged=isLogged;
        this.utilisateur = utilisateur;
    }
    // Getters et Setters

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getValeur() {
        return valeur;
    }

    public void setValeur(String valeur) {
        this.valeur = valeur;
    }

    public Integer getCodeValidation() {
        return codeValidation;
    }

    public void setCodeValidation(int codeValidation) {
        this.codeValidation = codeValidation;
    }

    public boolean isValide() {
        return isValide;
    }

    public void setValide(boolean isValide) {
        this.isValide = isValide;
    }

    public LocalDateTime getExpiration() {
        return expiration;
    }

    public void setExpiration(LocalDateTime expiration) {
        this.expiration = expiration;
    }

    public Utilisateur getUtilisateur() {
        return utilisateur;
    }

    public void setUtilisateur(Utilisateur utilisateur) {
        this.utilisateur = utilisateur;
    }

    public boolean isLogged() {
        return isLogged;
    }

    public void setLogged(boolean isLogged) {
        this.isLogged = isLogged;
    }

    public void setCodeValidation(Integer codeValidation) {
        this.codeValidation = codeValidation;
    }

    public Integer getNbTentative() {
        return nbTentative;
    }

    public void setNbTentative(Integer nbTentative) {
        this.nbTentative = nbTentative;
    }
}