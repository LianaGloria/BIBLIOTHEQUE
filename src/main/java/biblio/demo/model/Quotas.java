package biblio.demo.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;

@Entity
public class Quotas {
    @Id
    @Column(name = "quota_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "nb_prets ")
    private int pret;

    @Column(name = "nb_prolongations")
    private int prolongation;

    @Column(name = "nb_reservations")
    private int reservation;

    @OneToOne
    @JoinColumn(name = "profil_id", nullable = false)
    private Profil profil;

    // Getters & Setters
    public Long getId() { return id; }

    public int getPret() { return pret; }
    public void setPret(int pret) { this.pret = pret; }

    public int getProlongation() { return prolongation; }
    public void setProlongation(int prolongation) { this.prolongation = prolongation; }

    public int getReservation() { return reservation; }
    public void setReservation(int reservation) { this.reservation = reservation; }

    public Profil getProfil() { return profil; }
    public void setProfil(Profil profil) { this.profil = profil; }
}
