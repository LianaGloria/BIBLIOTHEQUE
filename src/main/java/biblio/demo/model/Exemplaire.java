package biblio.demo.model;

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
@Table(name = "exemplaires")
public class Exemplaire {

    @Id
    @Column(name = "exemplaire_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "livre_id", nullable = false)
    private Livre livre;

    public enum StatutExemplaire {
        disponible,
        emprunte,
        reserve
    }

    @Enumerated(EnumType.STRING)
    private StatutExemplaire statut;
    

    // @Column(name = "date_reservation")
    // private LocalDate dateReservation;

    // public void setDateReservation(LocalDate dateReservation) {
    //     this.dateReservation = dateReservation;
    // }

    // public LocalDate getDateReservation() {
    //     return dateReservation;
    // }

    // Getters & Setters
    public Long getId() {
        return id;
    }

    public Livre getLivre() {
        return livre;
    }

    public void setLivre(Livre livre) {
        this.livre = livre;
    }

    public StatutExemplaire getStatut() {
        return statut;
    }

    public void setStatut(StatutExemplaire statut) {
        this.statut = statut;
    }
}
 
