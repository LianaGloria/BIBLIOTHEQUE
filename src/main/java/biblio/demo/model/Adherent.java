package biblio.demo.model;

import java.time.LocalDate;

import org.springframework.format.annotation.DateTimeFormat;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "adherents")
public class Adherent {

    @Id
    @Column(name = "adherent_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    private String nom;
    private String prenom;
        
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @Column(name="date_naissance")
    private LocalDate dateNaissance;

    private int age;
    private String email;

    @Column(name="mot_de_passe")
    private String motDePasse;
    private String adresse;

    @ManyToOne
    @JoinColumn(name = "profil_id") // ← la colonne FK dans la BDD
    private Profil profil;

    private String statut;  // ou Enum, selon ta conception


    // Getters & Setters
    public Long getId() { return id; }

    public String getNom() { return nom; }
    public void setNom(String nom) { this.nom = nom; }

    public String getPrenom() { return prenom; }
    public void setPrenom(String prenom) { this.prenom = prenom; }

    public LocalDate getDateNaissance() { return dateNaissance; }
    public void setDateNaissance(LocalDate dateNaissance) { this.dateNaissance = dateNaissance; }

    public int getAge() { return age; }
    public void setAge(int age) { this.age = age; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getMotDePasse() { return motDePasse; }
    public void setMotDePasse(String motDePasse) { this.motDePasse = motDePasse; }

    public String getAdresse() { return adresse; }
    public void setAdresse(String adresse) { this.adresse = adresse; }

    public Profil getProfil() { return profil; }
    public void setProfil(Profil profil) { this.profil = profil; }

    
    public String getStatut() {
        return statut;
    }

    public void setStatut(String statut) {
        this.statut = statut;
    }



    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @Column(name = "date_expiration")
    private LocalDate dateExpiration;

    public LocalDate getDateExpiration() {
        return dateExpiration;
    }

    public void setDateExpiration(LocalDate dateExpiration) {
        this.dateExpiration = dateExpiration;
    }


    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @Column(name = "date_adhesion")
    private LocalDate dateAdhesion;

    public LocalDate getDateAdhesion() {
        return dateAdhesion;
    }

    public void setDateAdhesion(LocalDate dateAdhesion) {
        this.dateAdhesion = dateAdhesion;
    }

    @Column(name = "nb_emprunts_restants")
    private int restePret;

    public int getRestePret() {
        return restePret;
    }

    public void setRestePret(int restePret) {
        this.restePret = restePret;
    }



    @Column(name = "nb_prolongations_restantes")
    private int resteProlongation;

    public int getResteProlongation() {
        return resteProlongation;
    }

    public void setResteProlongation(int resteProlongation) {
        this.resteProlongation = resteProlongation;
    }



    @Column(name = "nb_reservations_restantes")
    private int resteReservation;

    public int getResteReservation() {
        return resteReservation;
    }

    public void setResteReservation(int resteReservation) {
        this.resteReservation = resteReservation;
    }

    @Column(name = "penalite_active")
    private boolean penaliteActive;

    public boolean isPenaliteActive() {
        return penaliteActive;
    }

    public void setPenaliteActive(boolean penaliteActive) {
        this.penaliteActive = penaliteActive;
    }



}

