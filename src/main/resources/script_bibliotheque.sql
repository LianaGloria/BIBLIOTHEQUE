DROP DATABASE IF EXISTS biblio;

CREATE DATABASE IF NOT EXISTS biblio;
USE biblio;

-- profils des utilisateurs
CREATE TABLE profils (
    profil_profil_id INT AUTO_INCREMENT PRIMARY KEY,
    nom VARCHAR(50) UNIQUE NOT NULL,
    duree_sur_place INT NOT NULL,
    duree_emporte INT NOT NULL,
    duree_prolongation INT NOT NULL,
    duree_adhesion INT NOT NULL,
    cotisation DECIMAL(10,2),
    penalites INT
);

-- quotas associés à chaque profil
CREATE TABLE quotas (
    quota_profil_id INT AUTO_INCREMENT PRIMARY KEY,
    nb_prets INT NOT NULL,
    nb_prolongations INT NOT NULL,
    nb_reservations INT NOT NULL,
    profil_profil_id INT NOT NULL,
    FOREIGN KEY (profil_profil_id) REFERENCES profils(profil_profil_id)
);

-- Jours de fermeture
CREATE TABLE jours_fermess (
    jour_profil_id INT AUTO_INCREMENT PRIMARY KEY,
    date_fermetureture DATE UNIQUE
);

-- Adhérents de la bibliothèque
CREATE TABLE adherents (
    adherent_profil_id BIGINT AUTO_INCREMENT PRIMARY KEY,
    nom VARCHAR(100) NOT NULL,
    prenom VARCHAR(100) NOT NULL,
    date_naissance DATE NOT NULL,
    age INT,
    email VARCHAR(100) UNIQUE NOT NULL,
    adresse TEXT,
    mot_de_passe VARCHAR(255) NOT NULL,
    date_adhesion DATE NOT NULL DEFAULT CURRENT_DATE,
    date_expiration DATE,
    nb_emprunts_restants INT,
    nb_prolongations_restantes INT,
    nb_reservations_restantes INT,
    penalites_active BOOLEAN DEFAULT FALSE,
    statut ENUM('actif', 'bloque', 'expire') DEFAULT 'actif',
    profil_profil_id INT NOT NULL,
    FOREIGN KEY (profil_profil_id) REFERENCES profils(profil_profil_id)
) ENGINE=InnoDB;

-- livres disponibles
CREATE TABLE livres (
    livre_profil_id INT AUTO_INCREMENT PRIMARY KEY,
    titre VARCHAR(200) NOT NULL,
    auteur VARCHAR(200) NOT NULL,
    description TEXT,
    image_url VARCHAR(255),
    age_minimum INT DEFAULT 0
);

-- exemplaires de livres
CREATE TABLE exemplaires (
    exemplaire_profil_id INT AUTO_INCREMENT PRIMARY KEY,
    livre_profil_id INT NOT NULL,
    statut ENUM('disponible', 'emprunte', 'reserve') DEFAULT 'disponible',
    FOREIGN KEY (livre_profil_id) REFERENCES livres(livre_profil_id)
) ENGINE=InnoDB;

-- emprunts de livres
CREATE TABLE emprunts (
    emprunt_profil_id INT AUTO_INCREMENT PRIMARY KEY,
    adherent_profil_id BIGINT NOT NULL,
    exemplaire_profil_id INT NOT NULL,
    type_pret ENUM('sur_place', 'emporte') NOT NULL,
    date_emprunt DATE NOT NULL,
    date_retour_prevue DATE NOT NULL,
    date_retour_reelle DATE,
    is_prolonge BOOLEAN DEFAULT FALSE,
    nb_prolongements INT DEFAULT 0,
    is_rendu BOOLEAN DEFAULT FALSE,
    penalites_active BOOLEAN DEFAULT FALSE,
    statut ENUM('en_cours', 'termine') DEFAULT 'en_cours',
    FOREIGN KEY (adherent_profil_id) REFERENCES adherents(adherent_profil_id),
    FOREIGN KEY (exemplaire_profil_id) REFERENCES exemplaires(exemplaire_profil_id)
) ENGINE=InnoDB;

-- retours des emprunts
CREATE TABLE retours (
    retour_profil_id INT AUTO_INCREMENT PRIMARY KEY,
    emprunt_profil_id INT NOT NULL,
    date_retour DATE NOT NULL,
    FOREIGN KEY (emprunt_profil_id) REFERENCES emprunts(emprunt_profil_id)
);

-- Pénalités liées aux emprunts
CREATE TABLE penalites (
    penalites_profil_id INT AUTO_INCREMENT PRIMARY KEY,
    emprunt_profil_id INT NOT NULL,
    nb_jours_retard INT NOT NULL,
    nb_jours_penalites INT NOT NULL,
    date_creation DATE DEFAULT CURRENT_DATE,
    date_autorisation_emprunt DATE,
    statut ENUM('en_cours', 'termine') DEFAULT 'en_cours',
    FOREIGN KEY (emprunt_profil_id) REFERENCES emprunts(emprunt_profil_id)
);

-- Réservations d’exemplaires
CREATE TABLE reservations (
    reservation_profil_id INT AUTO_INCREMENT PRIMARY KEY,
    adherent_profil_id BIGINT NOT NULL,
    exemplaire_profil_id INT NOT NULL,
    date_reservation DATE NOT NULL DEFAULT CURRENT_DATE,
    statut_demande ENUM('en_attente', 'acceptee', 'refusee', 'expiree') DEFAULT 'en_attente',
    statut_reservation ENUM('en_attente', 'en_cours', 'termine') DEFAULT 'en_attente',
    FOREIGN KEY (adherent_profil_id) REFERENCES adherents(adherent_profil_id),
    FOREIGN KEY (exemplaire_profil_id) REFERENCES exemplaires(exemplaire_profil_id)
);

-- prolongations d’emprunts
CREATE TABLE prolongations (
    prolongation_profil_id INT AUTO_INCREMENT PRIMARY KEY,
    emprunt_profil_id INT NOT NULL,
    date_demande DATE NOT NULL DEFAULT CURRENT_DATE,
    statut_demande ENUM('en_attente', 'acceptee', 'refusee') DEFAULT 'en_attente',
    statut_prolongation ENUM('en_attente', 'en_cours', 'termine') DEFAULT 'en_attente',
    nouvelle_date_retour DATE,
    FOREIGN KEY (emprunt_profil_id) REFERENCES emprunts(emprunt_profil_id)
);