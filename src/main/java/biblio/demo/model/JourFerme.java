package biblio.demo.model;

import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "jours_fermes")
public class JourFerme {

    @Id
    @Column(name = "jour_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "date_fermeture", nullable = false, unique = true)
    private LocalDate date;

    // --- Getters et Setters ---
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    // --- Constructeurs ---
    public JourFerme() {
    }

    public JourFerme(LocalDate date) {
        this.date = date;
    }

    // --- toString (optionnel) ---
    @Override
    public String toString() {
        return "JourFerme{" +
                "id=" + id +
                ", date=" + date +
                '}';
    }
}
