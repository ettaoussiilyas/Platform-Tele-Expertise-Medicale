
/*CREATE DATABASE IF NOT EXISTS medical_platform;*/
USE medical_platform;

CREATE TABLE roles (
    id INT PRIMARY KEY AUTO_INCREMENT,
    role_name ENUM('INFIRMIER', 'GENERALISTE', 'SPECIALISTE', 'ADMIN') NOT NULL UNIQUE
);



CREATE TABLE specialites (
    id INT PRIMARY KEY AUTO_INCREMENT,
    specialite_name VARCHAR(100) NOT NULL UNIQUE
);


CREATE TABLE utilisateurs (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    nom VARCHAR(100) NOT NULL,
    prenom VARCHAR(100) NOT NULL,
    email VARCHAR(150) NOT NULL UNIQUE,
    mot_de_passe VARCHAR(255) NOT NULL,
    telephone VARCHAR(20),
    role_id INT NOT NULL,
    
    -- Specialist-specific fields (nullable for others)
    specialite_id INT NULL,
    tarif DECIMAL(10,2) NULL,
    duree_consultation INT NULL,
    
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    
    FOREIGN KEY (role_id) REFERENCES roles(id),
    FOREIGN KEY (specialite_id) REFERENCES specialites(id),
    
    INDEX idx_email (email),
    INDEX idx_role (role_id)
);


CREATE TABLE patients (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    nom VARCHAR(100) NOT NULL,
    prenom VARCHAR(100) NOT NULL,
    email VARCHAR(150),
    telephone VARCHAR(20),
    date_naissance DATE,
    numero_securite_sociale VARCHAR(50),
    adresse TEXT,
    
    -- Signes vitaux
    tension VARCHAR(10),
    temperature DECIMAL(4,1),
    frequence_cardiaque INT,
    frequence_respiratoire INT,
    poids DECIMAL(5,2),
    taille DECIMAL(5,2),
    
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);


CREATE TABLE consultations (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    patient_id BIGINT NOT NULL,
    medecin_id BIGINT NOT NULL,
    date_consultation DATETIME NOT NULL,
    duree_minutes INT DEFAULT 30,
    tarif DECIMAL(10,2),
    statut ENUM('PROGRAMMEE', 'EN_COURS', 'TERMINEE', 'ANNULEE', 'EN_ATTENTE_AVIS_SPECIALISTE') DEFAULT 'PROGRAMMEE',
    notes TEXT,
    diagnostic TEXT,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    
    FOREIGN KEY (patient_id) REFERENCES patients(id),
    FOREIGN KEY (medecin_id) REFERENCES utilisateurs(id),
    
    INDEX idx_patient (patient_id),
    INDEX idx_medecin (medecin_id),
    INDEX idx_date (date_consultation),
    INDEX idx_statut (statut)
);


CREATE TABLE demandes_expertises (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    consultation_id BIGINT NOT NULL,
    generaliste_id BIGINT NOT NULL,
    specialiste_id BIGINT NOT NULL,
    specialite_id INT NOT NULL,
    question TEXT NOT NULL,
    reponse TEXT,
    priorite ENUM('URGENTE', 'NORMALE', 'NON_URGENTE') DEFAULT 'NORMALE',
    statut ENUM('EN_ATTENTE', 'TERMINEE') DEFAULT 'EN_ATTENTE',
    date_creation TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    date_reponse TIMESTAMP NULL,
    
    FOREIGN KEY (consultation_id) REFERENCES consultations(id),
    FOREIGN KEY (generaliste_id) REFERENCES utilisateurs(id),
    FOREIGN KEY (specialiste_id) REFERENCES utilisateurs(id),
    FOREIGN KEY (specialite_id) REFERENCES specialites(id),
    
    INDEX idx_statut (statut),
    INDEX idx_priorite (priorite)
);


CREATE TABLE creneaux_disponibles (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    medecin_id BIGINT NOT NULL,
    date_debut DATETIME NOT NULL,
    date_fin DATETIME NOT NULL,
    est_disponible BOOLEAN DEFAULT TRUE,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    
    FOREIGN KEY (medecin_id) REFERENCES utilisateurs(id),
    
    INDEX idx_medecin (medecin_id),
    INDEX idx_date_debut (date_debut),
    INDEX idx_disponible (est_disponible)
);


CREATE TABLE dossiers_medicaux (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    patient_id BIGINT NOT NULL,
    consultation_id BIGINT,
    titre VARCHAR(200) NOT NULL,
    contenu TEXT,
    type_document ENUM('ORDONNANCE', 'RAPPORT', 'ANALYSE', 'IMAGERIE') NOT NULL,
    date_creation TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    
    FOREIGN KEY (patient_id) REFERENCES patients(id),
    FOREIGN KEY (consultation_id) REFERENCES consultations(id),
    
    INDEX idx_patient (patient_id),
    INDEX idx_consultation (consultation_id),
    INDEX idx_type (type_document)
);


CREATE INDEX idx_utilisateurs_email_password ON utilisateurs(email, mot_de_passe);
CREATE INDEX idx_consultations_date_medecin ON consultations(date_consultation, medecin_id);
