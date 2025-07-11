package biblio.demo.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "livres")
public class Livre {

    @Id
    @Column(name = "livre_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String titre;

    private String auteur;

    @Column(columnDefinition = "TEXT")
    private String description;

    @Column(name = "image_url")
    private String image;

    @Column(name = "age_minimum")
    private int ageMin;

    public Livre() {
    }

    // 💡 Constructeur personnalisé utilisé dans livreservice
    public Livre(String titre, String auteur) {
        this.titre = titre;
        this.auteur = auteur;
    }

    // Optionnel : constructeur complet si besoin
    public Livre(String titre, String auteur, String description, String image, int ageMin) {
        this.titre = titre;
        this.auteur = auteur;
        this.description = description;
        this.image = image;
        this.ageMin = ageMin;
    }

    // Getters et setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitre() {
        return titre;
    }

    public void setTitre(String titre) {
        this.titre = titre;
    }

    public String getAuteur() {
        return auteur;
    }

    public void setAuteur(String auteur) {
        this.auteur = auteur;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public int getAgeMin() {
        return ageMin;
    }

    public void setAgeMin(int ageMin) {
        this.ageMin = ageMin;
    }

    public String getStatut() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getStatut'");
    }
}
