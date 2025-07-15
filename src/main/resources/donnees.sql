-- Insertion des profils
INSERT INTO profils (nom, duree_sur_place, duree_emporte, duree_prolongation, duree_adhesion, cotisation, penalites)
VALUES
  ('etudiant', 3, 5, 2, 6, 8081.00, 2),
  ('universitaire', 5, 10, 3, 12, 8000.00, 4),
  ('professionnel', 4, 7, 2, 12, 12000.00, 6);

-- Insertion des quotas
INSERT INTO quotas (nb_prets, nb_prolongations, nb_reservations, profil_profil_id) VALUES
(3, 1, 1, 1),  -- etudiant
(8, 2, 3, 2),  -- universitaire
(10, 3, 5, 3); -- professionnel

-- Insertion des livres
-- INSERT INTO livres (titre, auteur, description, image_url, age_minimum)
-- VALUES
--   ('LÉnigme du Temps', 'Claire Dumas', 'Un roman captivant mêlant science-fiction et philosophie.', NULL, 14),
--   ('Les Secrets du Baobab', 'Rija Randrianarisoa', 'Une aventure au cœur de la forêt malgache, pleine de sagesse.', NULL, 10),
--   ('Voyage vers Mars', 'Julien Lefèvre', 'Un récit futuriste sur la première colonie humaine sur Mars.', NULL, 12);


INSERT INTO livres (titre, auteur, description, image_url, age_minimum) VALUES ('L’Énigme du Temps', 'Claire Dumas', 'Un roman captivant mêlant science-fiction et philosophie.', NULL, 14);
INSERT INTO livres (titre, auteur, description, image_url, age_minimum) VALUES ('Les Secrets du Baobab', 'Rija Randrianarisoa', 'Une aventure au cœur de la forêt malgache, pleine de sagesse.', NULL, 10);
INSERT INTO livres (titre, auteur, description, image_url, age_minimum) VALUES ('Voyage vers Mars', 'Julien Lefèvre', 'Un récit futuriste sur la première colonie humaine sur Mars.', NULL, 12);
-- Insertion des exemplaires
INSERT INTO exemplaires (livre_id, statut)
VALUES
  -- exemplaires pour "LÉnigme du Temps"
  (1, 'disponible'),
  (1, 'emprunte'),

  -- exemplaires pour "Les Secrets du Baobab"
  (2, 'disponible'),
  (2, 'reserve'),

  -- exemplaires pour "Voyage vers Mars"
  (3, 'disponible'),
  (3, 'emprunte');

-- Insertion des jours de fermeture
INSERT INTO jours_fermess (date_fermetureture)
VALUES
  ('2025-01-01'), -- Nouvel an
  ('2025-05-01'), -- Fête du travail
  ('2025-06-26'), -- Fête de l'indépendance à Madagascar
  ('2025-12-25'); -- Noël



