package biblio.demo.model;

import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "prolongations")
public class Prolongation {

    @Id
    @Column(name = "prolongation_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "emprunt_id", nullable = false)
    private Emprunt emprunt;

    private LocalDate dateDemande;

    @Enumerated(EnumType.STRING)
    private StatutDemande statutDemande = StatutDemande.en_attente;

    @Enumerated(EnumType.STRING)
    private StatutProlongation statutProlongation = StatutProlongation.en_attente;

    @JoinColumn(name = "nouvelle_date_retour")
    private LocalDate nouvelleDateRetour;

    // Enums pour les statuts
    public enum StatutDemande {
        en_attente,
        acceptee,
        refusee
    }

    public enum StatutProlongation {
        en_attente,
        en_cours,
        termine
    }

    // Getters & setters
    // ...
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }


    public Emprunt getEmprunt() {
        return emprunt;
    }

    public void setEmprunt(Emprunt emprunt) {
        this.emprunt = emprunt;
    }


    public LocalDate getDateDemande() {
        return dateDemande;
    }

    public void setDateDemande(LocalDate dateDemande) {
        this.dateDemande = dateDemande;
    }



    public StatutDemande getStatutDemande() {
        return statutDemande;
    }

    public void setStatutDemande(StatutDemande statutDemande) {
        this.statutDemande = statutDemande;
    }


    public StatutProlongation getStatutProlongation() {
        return statutProlongation;
    }

    public void setStatutProlongation(StatutProlongation statutProlongation) {
        this.statutProlongation = statutProlongation;
    }


    public LocalDate getNouvelleDateRetour() {
        return nouvelleDateRetour;
    }

    public void setNouvelleDateRetour(LocalDate nouvelleDateRetour) {
        this.nouvelleDateRetour = nouvelleDateRetour;
    }
}
