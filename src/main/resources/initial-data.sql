-- Insert Roles
INSERT INTO roles (role_name) VALUES
('INFIRMIER'),
('GENERALISTE'),
('SPECIALISTE'),
('ADMIN');

-- Insert Specialties
INSERT INTO specialites (specialite_name) VALUES
('CARDIOLOGIE'),
('DERMATOLOGIE'),
('NEUROLOGIE'),
('ORTHOPEDIE'),
('PEDIATRIE'),
('GYNECOLOGIE'),
('OPHTALMOLOGIE'),
('RADIOLOGIE');

-- Insert Users with BCrypt hashed passwords
-- Password for all users: 123456 (hashed with BCrypt)
INSERT INTO utilisateurs (nom, prenom, email, mot_de_passe, telephone, role_id, created_at, updated_at)
VALUES
-- Infirmiers
('Alami', 'Fatima', 'infirmier@app.com', '$2a$10$92IXUNpkjO0rOQ5byMi.Ye4oKoEa3Ro9llC/.og/at2.uheWG/igi', '0661234567', 1, NOW(), NOW()),
('Bennani', 'Youssef', 'youssef.bennani@app.com', '$2a$10$92IXUNpkjO0rOQ5byMi.Ye4oKoEa3Ro9llC/.og/at2.uheWG/igi', '0662345678', 1, NOW(), NOW()),

-- Generalistes
('Tazi', 'Ahmed', 'generaliste@app.com', '$2a$10$92IXUNpkjO0rOQ5byMi.Ye4oKoEa3Ro9llC/.og/at2.uheWG/igi', '0663456789', 2, NOW(), NOW()),
('Idrissi', 'Khadija', 'khadija.idrissi@app.com', '$2a$10$92IXUNpkjO0rOQ5byMi.Ye4oKoEa3Ro9llC/.og/at2.uheWG/igi', '0664567890', 2, NOW(), NOW()),

-- Specialistes
('Fassi', 'Omar', 'specialiste@app.com', '$2a$10$92IXUNpkjO0rOQ5byMi.Ye4oKoEa3Ro9llC/.og/at2.uheWG/igi', '0665678901', 3, NOW(), NOW()),
('Berrada', 'Aicha', 'aicha.berrada@app.com', '$2a$10$92IXUNpkjO0rOQ5byMi.Ye4oKoEa3Ro9llC/.og/at2.uheWG/igi', '0666789012', 3, NOW(), NOW()),
('Lahlou', 'Rachid', 'rachid.lahlou@app.com', '$2a$10$92IXUNpkjO0rOQ5byMi.Ye4oKoEa3Ro9llC/.og/at2.uheWG/igi', '0667890123', 3, NOW(), NOW()),

-- Admin
('Admin', 'System', 'admin@app.com', '$2a$10$92IXUNpkjO0rOQ5byMi.Ye4oKoEa3Ro9llC/.og/at2.uheWG/igi', '0668901234', 4, NOW(), NOW());

-- Update Specialists with their specialties and rates
UPDATE utilisateurs SET specialite_id = 1, tarif = 200.00, duree_consultation = 30 WHERE email = 'specialiste@app.com';
UPDATE utilisateurs SET specialite_id = 2, tarif = 150.00, duree_consultation = 30 WHERE email = 'aicha.berrada@app.com';
UPDATE utilisateurs SET specialite_id = 3, tarif = 250.00, duree_consultation = 45 WHERE email = 'rachid.lahlou@app.com';

-- Insert Patients
INSERT INTO patients (
    nom, prenom, email, telephone, date_naissance, numero_securite_sociale, adresse,
    tension, temperature, frequence_cardiaque, frequence_respiratoire, poids, taille, created_at
)
VALUES
('Benali', 'Ahmed', 'ahmed.benali@gmail.com', '0611221100', '1987-05-12', 'MA123456789', 'Rue Hassan II, Rabat',
 '12/8', 36.5, 75, 16, 75.0, 1.80, NOW()),
('Zahraoui', 'Khadija', 'khadija.zahraoui@gmail.com', '0611332211', '1992-09-23', 'MA987654321', 'Avenue Mohammed V, Casablanca',
 '11/7', 36.8, 68, 18, 62.0, 1.65, NOW()),
('Amrani', 'Said', 'said.amrani@gmail.com', '0612443322', '1985-03-15', 'MA456789123', 'Boulevard Zerktouni, Marrakech',
 '13/9', 37.1, 82, 20, 80.5, 1.75, NOW()),
('Ouali', 'Zineb', 'zineb.ouali@gmail.com', '0613554433', '1990-11-08', 'MA789123456', 'Rue Allal Ben Abdellah, Fès',
 '10/6', 36.2, 70, 15, 58.0, 1.60, NOW());

-- Insert Consultations
INSERT INTO consultations (
    patient_id, medecin_id, date_consultation, duree_minutes, tarif, statut, notes, diagnostic, created_at
)
VALUES
(1, 3, '2025-01-15 09:30:00', 30, 150.00, 'TERMINEE', 'Consultation de routine, patient en bonne santé', 'Bilan de santé normal', NOW()),
(2, 3, '2025-01-15 10:00:00', 30, 150.00, 'EN_ATTENTE_AVIS_SPECIALISTE', 'Douleurs thoraciques récurrentes', 'Suspicion de problème cardiaque', NOW()),
(3, 4, '2025-01-15 14:30:00', 30, 150.00, 'TERMINEE', 'Maux de tête fréquents', 'Migraine, traitement prescrit', NOW()),
(4, 4, '2025-01-15 15:00:00', 30, 150.00, 'EN_ATTENTE_AVIS_SPECIALISTE', 'Éruption cutanée persistante', 'Nécessite avis dermatologique', NOW());

-- Insert Expertise Requests
INSERT INTO demandes_expertises (
    consultation_id, generaliste_id, specialiste_id, specialite_id, question, priorite, statut, date_creation
)
VALUES
(2, 3, 5, 1, 'Patient de 37 ans avec douleurs thoraciques récurrentes. ECG à interpréter. Antécédents familiaux de maladie cardiaque.', 'URGENTE', 'EN_ATTENTE', NOW()),
(4, 4, 6, 2, 'Patiente de 34 ans avec éruption cutanée depuis 3 semaines. Résistante aux traitements locaux habituels.', 'NORMALE', 'EN_ATTENTE', NOW());

-- Insert Available Time Slots for Specialists
INSERT INTO creneaux_disponibles (
    medecin_id, date_debut, date_fin, est_disponible, created_at
)
VALUES
-- Dr. Fassi (Cardiologue) - Semaine prochaine
(5, '2025-01-22 09:00:00', '2025-01-22 09:30:00', TRUE, NOW()),
(5, '2025-01-22 09:30:00', '2025-01-22 10:00:00', TRUE, NOW()),
(5, '2025-01-22 10:00:00', '2025-01-22 10:30:00', FALSE, NOW()),
(5, '2025-01-22 14:00:00', '2025-01-22 14:30:00', TRUE, NOW()),
(5, '2025-01-22 14:30:00', '2025-01-22 15:00:00', TRUE, NOW()),

-- Dr. Berrada (Dermatologue)
(6, '2025-01-23 08:30:00', '2025-01-23 09:00:00', TRUE, NOW()),
(6, '2025-01-23 09:00:00', '2025-01-23 09:30:00', TRUE, NOW()),
(6, '2025-01-23 09:30:00', '2025-01-23 10:00:00', TRUE, NOW()),
(6, '2025-01-23 15:30:00', '2025-01-23 16:00:00', TRUE, NOW()),

-- Dr. Lahlou (Neurologue)
(7, '2025-01-24 10:00:00', '2025-01-24 10:45:00', TRUE, NOW()),
(7, '2025-01-24 10:45:00', '2025-01-24 11:30:00', TRUE, NOW()),
(7, '2025-01-24 14:00:00', '2025-01-24 14:45:00', TRUE, NOW());

-- Insert Medical Records
INSERT INTO dossiers_medicaux (
    patient_id, consultation_id, titre, contenu, type_document, date_creation
)
VALUES
(1, 1, 'Bilan de santé - Janvier 2025', 'Examen clinique complet. Tension artérielle normale. Poids stable. Recommandations: maintenir activité physique régulière.', 'RAPPORT', NOW()),
(2, 2, 'Rapport consultation cardiologique', 'Patient en attente d''expertise cardiologique. ECG réalisé, résultats à interpréter par spécialiste.', 'RAPPORT', NOW()),
(3, 3, 'Ordonnance - Traitement migraine', 'Paracétamol 1g x3/jour si douleur. Sumatriptan 50mg en cas de crise. Revoir dans 15 jours.', 'ORDONNANCE', NOW()),
(4, 4, 'Demande avis dermatologique', 'Éruption cutanée résistante aux traitements locaux. Photos prises. Demande avis spécialisé urgent.', 'RAPPORT', NOW());