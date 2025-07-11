package biblio.demo.model;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "profils")
public class Profil {

    @Id
    @Column(name = "profil_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String nom;

    @Column(name = "duree_sur_place")
    private int dureeSurPlace;

    @Column(name = "duree_emporte")
    private int dureeEmporte;

    @Column(name = "duree_prolongation")
    private int dureeProlongation;

    @Column(name = "duree_adhesion")
    private int dureeAdhesion;

    private double cotisation;

    private int penalite;

    // === Getters & Setters ===

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public int getDureeSurPlace() {
        return dureeSurPlace;
    }

    public void setDureeSurPlace(int dureeSurPlace) {
        this.dureeSurPlace = dureeSurPlace;
    }

    public int getDureeEmporte() {
        return dureeEmporte;
    }

    public void setDureeEmporte(int dureeEmporte) {
        this.dureeEmporte = dureeEmporte;
    }

    public int getDureeProlongation() {
        return dureeProlongation;
    }

    public void setDureeProlongation(int dureeProlongation) {
        this.dureeProlongation = dureeProlongation;
    }

    public int getDureeAdhesion() {
        return dureeAdhesion;
    }

    public void setDureeAdhesion(int dureeAdhesion) {
        this.dureeAdhesion = dureeAdhesion;
    }

    public double getCotisation() {
        return cotisation;
    }

    public void setCotisation(double cotisation) {
        this.cotisation = cotisation;
    }

    @Override
    public String toString() {
        return nom;
    }

    @OneToOne(mappedBy = "profil", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Quotas quotas;
    
    public Quotas getquotas() {
        return quotas;
    }
    
    public void setquotas(Quotas quotas) {
        this.quotas = quotas;
    }

    public int getPenalite() {
        return this.penalite;
    }

    public void setPenalite(int penalite) {
        this.penalite = penalite;
    }

}
