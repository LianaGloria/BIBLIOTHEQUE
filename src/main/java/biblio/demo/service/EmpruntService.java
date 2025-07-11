package biblio.demo.service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import biblio.demo.model.Adherent;
import biblio.demo.model.Emprunt;
import biblio.demo.model.Exemplaire;
import biblio.demo.model.JourFerme;
import biblio.demo.model.Livre;
import biblio.demo.model.Penalite;
import biblio.demo.repository.EmpruntRepository;
import biblio.demo.repository.ExemplaireRepository;
import biblio.demo.repository.JourFermeRepository;
import biblio.demo.repository.PenaliteRepository;

@Service
public class EmpruntService {
    @Autowired
    private ExemplaireRepository exemplaireRepository;

    @Autowired
    private EmpruntRepository empruntRepository;

    @Autowired
    private JourFermeRepository JourFermeRepository;

    @Autowired
    private PenaliteRepository penaliteRepository;


public String verifierEligibilite(Adherent a, Exemplaire e, LocalDate datePret, String typePret) {
    // Vérifie le statut de l'adhérent
    if (!"actif".equalsIgnoreCase(a.getStatut())) {
        return "Le statut de l'adhérent n'est pas actif.";
    }

    // Vérifie la pénalité
    if (a.isPenaliteActive()) {
        Optional<Penalite> penaliteOpt = penaliteRepository
                .findTopByEmprunt_AdherentAndStatutOrderByDateCreationDesc(a, Penalite.StatutPenalite.en_cours);

        if (penaliteOpt.isPresent()) {
            Penalite penalite = penaliteOpt.get();
            if (datePret.isBefore(penalite.getDateEmpruntAutorise())) {
                return "L'adhérent a une pénalité active et ne peut pas encore emprunter à cette date.";
            }
        }
    }

    // Vérifie le quota de prêt emporté
    if ("emporte".equalsIgnoreCase(typePret) && a.getRestePret() <= 0) {
        return "Quota de prêt emporté épuisé.";
    }

    // Vérifie l’âge minimum
    Livre l = e.getLivre();
    if (l.getAgeMin() > a.getAge()) {
        return "L'adhérent est trop jeune pour emprunter ce livre.";
    }

    // Vérifie que l’exemplaire n’est pas déjà emprunté
    // if (e.getStatut() == Exemplaire.StatutExemplaire.emprunte) {
    //     return "Cet exemplaire est déjà emprunté.";
    // }

    // Vérifie s’il y a un doublon d’emprunt actif sur cet exemplaire
    boolean doublon = existeConflitEmpruntPourExemplaire(a, e, datePret);
    if (doublon) {
        return "Cet adhérent a déjà emprunté ce livre durant cette période.";
    }

    return null; // Tout est bon
}




    
    public LocalDate calculerDateRetourAvecFeries(LocalDate datePret, int duree) {
        LocalDate date = datePret.minusDays(1);
        int joursAjoutes = 0;

        // Récupère les jours fériés à partir de maintenant (optimisation)
        List<LocalDate> joursFermes = JourFermeRepository.findAll()
            .stream()
            .map(JourFerme::getDate)
            .collect(Collectors.toList());

        while (joursAjoutes < duree) {
            date = date.plusDays(1);
            if (!joursFermes.contains(date)) {
                joursAjoutes++;
            }
        }

        return date;
    }


public boolean existeConflitEmpruntPourExemplaire(Adherent adherent, Exemplaire exemplaire, LocalDate datePret) {
    List<Emprunt> emprunts = empruntRepository.findByExemplaireAndexemplairestatutIn(exemplaire);

    for (Emprunt e : emprunts) {
        Exemplaire ex = e.getExemplaire();
        LocalDate debut = e.getDatePret();
        LocalDate fin = e.getDateRetourPrevu();

        // Vérifie si même exemplaire ET si datePret est comprise dans la période existante
        if (ex.getId().equals(exemplaire.getId()) &&
            !datePret.isBefore(debut) && !datePret.isAfter(fin)) {
            return true; // conflit détecté
        }
    }
    return false; // aucun conflit

}




    // public LocalDate calculerDateRetourAvecFeries(LocalDate dateDebut, int duree) {
    //     LocalDate dateRetour = dateDebut;
    //     int joursAjoutes = 0;

    //     while (joursAjoutes < duree) {
    //         dateRetour = dateRetour.plusDays(1);

    //         if (!jourFerieRepository.isFerie(dateRetour)) {
    //             joursAjoutes++;
    //         }
    //     }

    //     return dateRetour;
    // }

    
}





// package biblio.demo.service;

// import biblio.demo.model.Adherent;
// import biblio.demo.model.Emprunt;
// import biblio.demo.model.Livre;
// import biblio.demo.repository.EmpruntRepository;
// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.stereotype.Service;

// @Service
// public class empruntservice {

//     @Autowired
//     private EmpruntRepository empruntRepository;

//     /**
//      * Vérifie si un adhérent peut emprunter un livre avec un certain type de prêt.
//      * @return null si OK, sinon un message d'erreur.
//      */
//     public String verifierEligibilite(Adherent adherent, Livre livre, String typePret) {
//         // 1. Vérifie la pénalité
//         if (adherent.isPenaliteActive()) {
//             return "L’adhérent a une pénalité active et ne peut pas emprunter.";
//         }

//         // 2. Vérifie le quota si prêt emporté
//         if ("emporte".equalsIgnoreCase(typePret)) {
//             Integer reste = adherent.getRestePret();
//             if (reste == null || reste <= 0) {
//                 return "Quota de prêt emporté épuisé pour cet adhérent.";
//             }
//         }

//         // 3. Vérifie s’il a déjà un emprunt non rendu pour ce livre
//         boolean dejaEmprunte = empruntRepository.existsByAdherentAndExemplaire_LivreAndRenduFalse(adherent, livre);
//         if (dejaEmprunte) {
//             return "Cet adhérent a déjà un exemplaire de ce livre non rendu.";
//         }

//         // ✅ Tout est bon
//         return null;
//     }
// }
