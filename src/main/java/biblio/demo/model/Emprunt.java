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
@Table(name = "emprunts")
public class Emprunt {

    @Id
    @Column(name = "emprunt_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Association avec l'adhérent
    @ManyToOne
    @JoinColumn(name = "adherent_id", nullable = false)
    private Adherent adherent;

    // Association avec le livre
    // @ManyToOne
    // @JoinColumn(name = "id_livre", nullable = false)
    // private Livre livre;

    @Column(nullable = false, name = "type_pret")
    private String typePret; // "sur_place" ou "emporte"

    @Column(nullable = false, name = "date_emprunt")
    private LocalDate datePret;

    @Column(nullable = false, name = "date_retour_prevue")
    private LocalDate dateRetourPrevu;

    @Column(name = "date_retour_reelle")
    private LocalDate dateRetourReel;

    @Column(name = "is_prolonge")
    private boolean prolongation; // indique si le prêt a été prolongé

    @Column(name = "nb_prolongements")
    private int nbprolongations;  // nombre de fois prolongé


    @Column(name = "penalite_active")
    private boolean penaliteActive; // indique si une pénalité a été appliquée pour ce prêt

    public enum StatutEmprunt {
        en_cours,
        termine
    }

    @Enumerated(EnumType.STRING)
    private StatutEmprunt statut = StatutEmprunt.en_cours;


    public StatutEmprunt getStatut() {
        return statut;
    }

    public void setStatut(StatutEmprunt statut) {
        this.statut = statut;
    }

    // --- Getters et Setters ---
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Adherent getAdherent() {
        return adherent;
    }

    public void setAdherent(Adherent adherent) {
        this.adherent = adherent;
    }

    // public Livre getLivre() {
    //     return livre;
    // }

    // public void setLivre(Livre livre) {
    //     this.livre = livre;
    // }

    public String getTypePret() {
        return typePret;
    }

    public void setTypePret(String typePret) {
        this.typePret = typePret;
    }

    public LocalDate getDatePret() {
        return datePret;
    }

    public void setDatePret(LocalDate datePret) {
        this.datePret = datePret;
    }

    public LocalDate getDateRetourPrevu() {
        return dateRetourPrevu;
    }

    public void setDateRetourPrevu(LocalDate dateRetourPrevu) {
        this.dateRetourPrevu = dateRetourPrevu;
    }

    public LocalDate getDateRetourReel() {
        return dateRetourReel;
    }

    public void setDateRetourReel(LocalDate dateRetourReel) {
        this.dateRetourReel = dateRetourReel;
    }

    public boolean isProlongation() {
        return prolongation;
    }

    public void setProlongation(boolean prolongation) {
        this.prolongation = prolongation;
    }

    public int getNbprolongations() {
        return nbprolongations;
    }

    public void setNbprolongations(int nbprolongations) {
        this.nbprolongations = nbprolongations;
    }

    @Column(name = "is_rendu")
    private Boolean rendu;

    public Boolean getRendu() {
        return rendu;
    }

    public void setRendu(Boolean rendu) {
        this.rendu = rendu;
    }


    public boolean isPenaliteActive() {
        return penaliteActive;
    }

    public void setPenaliteActive(boolean penaliteActive) {
        this.penaliteActive = penaliteActive;
    }

    // autres champs ...

    @ManyToOne
    @JoinColumn(name = "exemplaire_id")
    private Exemplaire exemplaire;

    // getter
    public Exemplaire getExemplaire() {
        return exemplaire;
    }

    // setter à implémenter correctement
    public void setExemplaire(Exemplaire exemplaire) {
        this.exemplaire = exemplaire;
    }

    // autres méthodes...
}


