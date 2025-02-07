package mg.itu.auth.models;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.*;

@Entity
@Table(name = "Utilisateur")
public class Utilisateur {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_utilisateur")
    private Long id;

    @Column(name = "identifiant", nullable = false, unique = true, length = 50)
    private String identifiant;

    @Column(name = "email", nullable = false, length = 50)
    private String email;

    @Column(name = "mot_de_passe", nullable = false, length = 255)
    private String motDePasse;

    @Column(name = "mail_valide", nullable = false)
    private boolean isValide;

    @Column(name = "code_validation")
    private Integer codeValidation;

    @OneToMany(mappedBy = "utilisateur", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Token> tokens = new ArrayList<>();

    public Utilisateur() {}

    public Utilisateur(String identifiant, String email, String motDePasse, boolean isValide, Integer codeValidation) {
        this.identifiant = identifiant;
        this.email = email;
        this.motDePasse = motDePasse;
        this.isValide = isValide;
        this.codeValidation = codeValidation;
    }

    // Getters et Setters
    public Utilisateur(Long id, String identifiant, String email) {
        this.id = id;
        this.identifiant = identifiant;
        this.email = email;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getIdentifiant() {
        return identifiant;
    }

    public void setIdentifiant(String identifiant) {
        this.identifiant = identifiant;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getMotDePasse() {
        return motDePasse;
    }

    public void setMotDePasse(String motDePasse) {
        this.motDePasse = motDePasse;
    }

    public boolean isValide() {
        return isValide;
    }

    public void setValide(boolean isValide) {
        this.isValide = isValide;
    }

    public Integer getCodeValidation() {
        return codeValidation;
    }

    public void setCodeValidation(Integer codeValidation) {
        this.codeValidation = codeValidation;
    }

    public List<Token> getTokens() {
        return tokens;
    }

    public void setTokens(List<Token> tokens) {
        this.tokens = tokens;
    }    
}
