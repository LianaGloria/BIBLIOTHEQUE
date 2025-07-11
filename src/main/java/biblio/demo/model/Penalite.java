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
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

@Entity
@Table(name= "penalites")
public class Penalite {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "penalite_id") // <-- correction ici
    private Long id;

    @OneToOne
    @JoinColumn(name = "emprunt_id", nullable = false) // <-- correction ici
    private Emprunt emprunt;

    @Column(name = "nb_jours_retard")
    private int nbJoursRetard;

    @Column(name = "nb_jours_penalite")
    private int nbJoursPenalite;

    @Column(name = "date_creation")
    private LocalDate dateCreation;

    @Column(name = "date_autorisation_emprunt") // <-- correction ici
    private LocalDate dateEmpruntAutorise;

        public enum StatutPenalite {
        en_cours,
        termine
    }

    @Enumerated(EnumType.STRING)
    @Column(name = "statut")  // ajoute cette ligne pour plus de clarté
    private StatutPenalite statut = StatutPenalite.en_cours;

    // Getters & Setters
    public Long getId() { return id; }

    public Emprunt getEmprunt() { return emprunt; }
    public void setEmprunt(Emprunt emprunt) { this.emprunt = emprunt; }

    public int getNbJoursRetard() { return nbJoursRetard; }
    public void setNbJoursRetard(int nbJoursRetard) { this.nbJoursRetard = nbJoursRetard; }

    public int getNbJoursPenalite() { return nbJoursPenalite; }
    public void setNbJoursPenalite(int nbJoursPenalite) { this.nbJoursPenalite = nbJoursPenalite; }

    public LocalDate getDateCreation() { return dateCreation; }
    public void setDateCreation(LocalDate dateCreation) { this.dateCreation = dateCreation; }

    public LocalDate getDateEmpruntAutorise() { return dateEmpruntAutorise; }
    public void setDateEmpruntAutorise(LocalDate dateEmpruntAutorise) { this.dateEmpruntAutorise = dateEmpruntAutorise; }

    public StatutPenalite getStatut() { return statut; }
    public void setStatut(StatutPenalite statut) { this.statut = statut; }
}
