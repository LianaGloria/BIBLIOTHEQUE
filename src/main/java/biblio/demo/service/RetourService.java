package biblio.demo.service;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import biblio.demo.model.Adherent;
import biblio.demo.model.Emprunt;
import biblio.demo.model.Exemplaire;
import biblio.demo.model.JourFerme;
import biblio.demo.model.Penalite;
import biblio.demo.model.Retour;
import biblio.demo.repository.AdherentRepository;
import biblio.demo.repository.EmpruntRepository;
import biblio.demo.repository.ExemplaireRepository;
import biblio.demo.repository.JourFermeRepository;
import biblio.demo.repository.PenaliteRepository;
import biblio.demo.repository.RetourRepository;

@Service
public class RetourService {

    @Autowired
    private AdherentRepository adherentRepository;

    @Autowired
    private EmpruntRepository empruntRepository;

    @Autowired
    private RetourRepository retourRepository;

    @Autowired
    private ExemplaireRepository exemplaireRepository;

    @Autowired
    private PenaliteRepository penaliteRepository;

    @Autowired
    private JourFermeRepository jourFermeRepository;

    // public String gererRetour(Long adherentId, Long livreId, LocalDate dateRetour) {
    //     // Vérification de l'existence de l'adhérent
    //     Adherent adherent = adherentRepository.findById(adherentId).orElse(null);
    //     if (adherent == null) return "Adhérent introuvable.";

    //     // Recherche de l'emprunt actif correspondant à ce livre
    //     List<Emprunt> emprunts = empruntRepository.findempruntsActifsParLivreEtAdherent(adherentId, livreId);
    //     if (emprunts.isEmpty()) return "Aucun emprunt actif trouvé pour ce livre.";

    //     Emprunt emprunt = emprunts.get(0);

    //     // Mise à jour de l'emprunt
    //     emprunt.setDateRetourReel(dateRetour);
    //     emprunt.setRendu(true);
    //     emprunt.setStatut(Emprunt.StatutEmprunt.termine);
    //     empruntRepository.save(emprunt);

    //     // Mise à jour de l'exemplaire
    //     Exemplaire exemplaire = emprunt.getExemplaire();
    //     exemplaire.setStatut(Exemplaire.StatutExemplaire.disponible);
    //     exemplaireRepository.save(exemplaire);

    //     // Enregistrement du retour
    //     Retour retour = new Retour();
    //     retour.setEmprunt(emprunt);
    //     retour.setDateRetour(dateRetour);
    //     retourRepository.save(retour);

    //     // Vérification de retard
    //     LocalDate datePrevue = emprunt.getDateRetourPrevu();
    //     if (dateRetour.isAfter(datePrevue)) {
    //         // Calcul du retard
    //         // long joursRetard = ChronoUnit.DAYS.between(datePrevue, dateRetour);
    //         // int penaliteParJour = adherent.getProfil().getPenalite();
    //         // int dureePenalite = (int) joursRetard * penaliteParJour;
    //         // dureePenalite = Math.min(dureePenalite, 30); // Plafond de 30 jours

    //         int joursRetard =(int) ChronoUnit.DAYS.between(datePrevue, dateRetour);
    //         int dureePenalite = adherent.getProfil().getPenalite();
            
    //         // Chercher les pénalités déjà en cours pour cet adhérent
    //         LocalDate baseDate = dateRetour;
    //         List<Penalite> penalitesActives = penaliteRepository
    //             .findByEmprunt_AdherentAndDateEmpruntAutoriseAfter(adherent, LocalDate.now());

    //         for (Penalite p : penalitesActives) {
    //             if (p.getDateEmpruntAutorise().isAfter(baseDate)) {
    //                 baseDate = p.getDateEmpruntAutorise();
    //             }
    //         }

    //         // Déterminer la vraie date autorisée d'emprunt après pénalité
    //         LocalDate dateAutorise = determinerDateAutorise(baseDate, dureePenalite);
            

    //         // Enregistrer la pénalité
    //         Penalite penalite = new Penalite();
    //         penalite.setEmprunt(emprunt);
    //         penalite.setNbJoursRetard((int) joursRetard);
    //         penalite.setNbJoursPenalite(dureePenalite);
    //         penalite.setDateCreation(dateRetour);
    //         penalite.setDateEmpruntAutorise(dateAutorise);
    //         penaliteRepository.save(penalite);

    //         // Mise à jour de l'adhérent (restePret + 1)
    //         adherent.setPenaliteActive(true);

    //         // int nouveauRestePret = adherent.getRestePret() + 1;
    //         // adherentRepository.updateRestePret(adherent.getId(), nouveauRestePret);

    //         // (adherentRepository.findById(adherentId).get()).setRestePret((adherentRepository.findById(adherentId).get()).getRestePret() + 1);

            
    //         System.out.println("Avant update custom : " + adherent.getRestePret());
    //         adherentRepository.incrementRestePret(adherent.getId());
            
    //     }

    // // Vérifie si c'était une prolongation
    // if (emprunt.isProlongation()) {
    //     // L'emprunt avait été prolongé → on rend le quota de prolongation
    //     adherent.setResteProlongation(adherent.getResteProlongation() + 1);
    //     System.out.println("Quota de prolongation restitué.");
    // } else {
    //     // Emprunt simple → on rend le quota d’emprunt
    //     adherent.setRestePret(adherent.getRestePret() + 1);
    //     System.out.println("Quota d’emprunt restitué.");
    // }

    // adherentRepository.save(adherent);


    //     return null; // succès
    // }




    public String gererRetour(Long adherentId, Long exemplaireId, LocalDate dateRetour) {
        // Vérification de l'existence de l'adhérent
        Adherent adherent = adherentRepository.findById(adherentId).orElse(null);
        if (adherent == null) return "Adhérent introuvable.";

        // Vérification de l'exemplaire
        Exemplaire exemplaire = exemplaireRepository.findById(exemplaireId).orElse(null);
        if (exemplaire == null) return "Exemplaire introuvable.";

        // Recherche de l'emprunt actif correspondant à cet exemplaire
        Emprunt emprunt = empruntRepository.findByAdherentAndExemplaireAndStatut(
                adherent, exemplaire, Emprunt.StatutEmprunt.en_cours);

        if (emprunt == null) return "Aucun emprunt actif trouvé pour cet exemplaire.";

        // Mise à jour de l'emprunt
        emprunt.setDateRetourReel(dateRetour);
        emprunt.setRendu(true);
        emprunt.setStatut(Emprunt.StatutEmprunt.termine);
        empruntRepository.save(emprunt);

        // Mise à jour de l'exemplaire
        exemplaire.setStatut(Exemplaire.StatutExemplaire.disponible);
        exemplaireRepository.save(exemplaire);

        // Enregistrement du retour
        Retour retour = new Retour();
        retour.setEmprunt(emprunt);
        retour.setDateRetour(dateRetour);
        retourRepository.save(retour);

        // Vérification de retard
        LocalDate datePrevue = emprunt.getDateRetourPrevu();
        if (dateRetour.isAfter(datePrevue)) {
            int joursRetard = (int) ChronoUnit.DAYS.between(datePrevue, dateRetour);
            int dureePenalite = adherent.getProfil().getPenalite();

            LocalDate baseDate = dateRetour;
            List<Penalite> penalitesActives = penaliteRepository
                .findByEmprunt_AdherentAndDateEmpruntAutoriseAfter(adherent, LocalDate.now());

            for (Penalite p : penalitesActives) {
                if (p.getDateEmpruntAutorise().isAfter(baseDate)) {
                    baseDate = p.getDateEmpruntAutorise();
                }
            }

            LocalDate dateAutorise = determinerDateAutorise(baseDate, dureePenalite);

            // Enregistrer la pénalité
            Penalite penalite = new Penalite();
            penalite.setEmprunt(emprunt);
            penalite.setNbJoursRetard(joursRetard);
            penalite.setNbJoursPenalite(dureePenalite);
            penalite.setDateCreation(dateRetour);
            penalite.setDateEmpruntAutorise(dateAutorise);
            penaliteRepository.save(penalite);

            adherent.setPenaliteActive(true);
            adherentRepository.incrementRestePret(adherent.getId()); // méthode custom
        }

        // Restitution de quota selon type d'emprunt
        if (emprunt.isProlongation()) {
            adherent.setResteProlongation(adherent.getResteProlongation() + emprunt.getNbprolongations());
        } 
            
        adherent.setRestePret(adherent.getRestePret() + 1);
    
        adherentRepository.save(adherent);

        return null;
    }








    /**
     * Ajoute un nombre de jours ouvrables à une date donnée
     * en sautant les jours fériés définis dans la base.
     */
    private LocalDate determinerDateAutorise(LocalDate dateDepart, int jours) {
        LocalDate date = dateDepart.plusDays(jours);
        List<LocalDate> joursFermes = jourFermeRepository.findAll()
            .stream()
            .map(JourFerme::getDate)
            .collect(Collectors.toList());

        // Décaler jusqu'à trouver un jour non fermé
        while (joursFermes.contains(date)) {
            date = date.plusDays(1);
        }

        return date;
    }
}
