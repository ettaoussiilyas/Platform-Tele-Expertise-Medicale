INSERT INTO roles (role_name) VALUES
('INFIRMIER'),
('GENERALISTE'),
('SPECIALISTE'),
('ADMIN');

INSERT INTO specialites (nom) VALUES
('CARDIOLOGIE'),
('PNEUMOLOGIE'),
('NEUROLOGIE'),
('GASTRO-ENTEROLOGIE'),
('ENDOCRINOLOGIE'),
('DERMATOLOGIE'),
('RHUMATOLOGIE'),
('PSYCHIATRIE'),
('NEPHROLOGIE'),
('ORTHOPEDIE');

INSERT INTO actes_techniques (nom, description, tarif, duree_minutes) VALUES
('Radiographie', 'Radiographie thoracique standard', 80.00, 15),
('Échographie', 'Échographie abdominale complète', 120.00, 30),
('IRM', 'Imagerie par résonance magnétique', 800.00, 45),
('Électrocardiogramme', 'ECG 12 dérivations', 50.00, 10),
('Analyses de sang', 'Bilan sanguin complet', 150.00, 5),
('Analyse d\'urine', 'ECBU complet', 60.00, 5),
('Fond d\'œil', 'Examen ophtalmologique', 100.00, 20),
('Laser dermatologique', 'Traitement laser cutané', 200.00, 30);

INSERT INTO utilisateurs (nom, prenom, email, mot_de_passe, telephone, role_id)
VALUES
('Dupont', 'Marie', 'marie.dupont@hospital.com', '$2a$10$N9qo8uLOickgx2ZMRZoMye', '0611223344', 1),
('Martin', 'Pierre', 'pierre.martin@hospital.com', '$2a$10$N9qo8uLOickgx2ZMRZoMye', '0611223355', 2),
('Dubois', 'Sophie', 'sophie.dubois@hospital.com', '$2a$10$N9qo8uLOickgx2ZMRZoMye', '0611223366', 3);

UPDATE utilisateurs
SET specialite_id = 1, tarif = 80.00, duree_consultation = 45
WHERE email = 'sophie.dubois@hospital.com';

INSERT INTO patients (
    nom, prenom, email, telephone, date_naissance, numero_securite_sociale, adresse,
    tension, temperature, frequence_cardiaque, frequence_respiratoire, poids, taille
)
VALUES
('Benali', 'Ahmed', 'ahmed.benali@gmail.com', '0611221100', '1987-05-12', 'MA123456789', 'Nador, Maroc',
 '12/8', 36.8, 78, 18, 70.5, 1.78),
('Zahraoui', 'Khadija', 'khadija.zahraoui@gmail.com', '0611332211', '1992-09-23', 'MA987654321', 'Oujda, Maroc',
 '13/9', 37.2, 80, 19, 68.0, 1.65);

INSERT INTO consultations (
    patient_id, medecin_id, date_consultation, duree_minutes, tarif, statut, notes, diagnostic
)
VALUES
(1, 2, '2025-10-08 10:30:00', 30, 150.00, 'TERMINEE', 'Toux et fièvre légère', 'Infection virale bénigne'),
(2, 2, '2025-10-08 11:00:00', 30, 150.00, 'EN_ATTENTE_AVIS_SPECIALISTE', 'Douleur thoracique', 'Possible problème cardiaque');

INSERT INTO demandes_expertises (
    consultation_id, generaliste_id, specialiste_id, specialite_id, question, priorite, statut
)
VALUES
(2, 2, 3, 1, 'Avis sur ECG suspect : patient avec douleurs thoraciques.', 'URGENTE', 'EN_ATTENTE');

INSERT INTO creneaux_disponibles (
    medecin_id, date_debut, date_fin, est_disponible
)
VALUES
(3, '2025-10-09 09:00:00', '2025-10-09 09:30:00', TRUE),
(3, '2025-10-09 09:30:00', '2025-10-09 10:00:00', TRUE),
(3, '2025-10-09 10:00:00', '2025-10-09 10:30:00', FALSE),
(3, '2025-10-09 10:30:00', '2025-10-09 11:00:00', TRUE),
(3, '2025-10-09 11:00:00', '2025-10-09 11:30:00', TRUE);

INSERT INTO dossiers_medicaux (
    patient_id, consultation_id, titre, contenu, type_document
)
VALUES
(1, 1, 'Ordonnance du 08/10/2025', 'Paracétamol 1g x3/jour pendant 5 jours', 'ORDONNANCE'),
(2, 2, 'Rapport de consultation', 'Patient sous observation en attente de télé-expertise.', 'RAPPORT');