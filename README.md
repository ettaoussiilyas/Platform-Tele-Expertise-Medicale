# Système de Télé‑Expertise Médicale

## Contexte

En tant que développeur Java web, vous devez concevoir et développer un système de télé‑expertise médicale. L'objectif est d'optimiser le parcours patient en facilitant la coordination entre médecins généralistes, spécialistes et infirmiers, et d'assurer une prise en charge efficace et rapide. Le système permet à un généraliste de solliciter l'avis d'un spécialiste pour un patient donné, en mode synchrone (visioconférence/téléphone) ou asynchrone (dossier + réponse écrite).

---

## Table des matières

1. Contexte
2. Processus détaillé
3. Modes de communication
4. Exigences fonctionnelles (par module)
5. Actes techniques médicaux
6. Bonus — gestion du staff
7. Exigences techniques
8. Installation & exécution (rapide)

---

## Processus détaillé

### 1. Arrivée et enregistrement du patient

* L’infirmier accueille le patient et saisit :

    * Données administratives : nom, prénom, coordonnées, N° sécurité sociale, mutuelle
    * Données médicales : antécédents, allergies, traitements en cours
    * Signes vitaux : tension artérielle, fréquence cardiaque, température, fréquence respiratoire, poids/taille (si nécessaire)

### 2. File d'attente

* Après enregistrement, le patient est ajouté automatiquement à la file d’attente pour voir un médecin généraliste.

### 3. Consultation avec le médecin généraliste

* Le généraliste consulte le dossier patient (données saisies par l’infirmier) et effectue :

    * Examen clinique (inspection, palpation, auscultation...)
    * Analyse des symptômes
    * Création d’une consultation dans le système avec observations

### 4. Décision de prise en charge

Deux scénarios possibles :

**Scénario A — Prise en charge directe**

* Diagnostic
* Prescription (ex. Paracétamol 1g, 3x/j)
* Clôture de la consultation (statut `TERMINEE`)

**Scénario B — Télé‑expertise nécessaire**

* Étape 1 : Demande d'avis spécialiste → consultation sauvegardée avec statut `EN_ATTENTE_AVIS_SPECIALISTE` (consultation non clôturée)
* Étape 2 : Identification de la spécialité (ex. cardiologie, dermatologie...)
* Étape 3 : Recherche des spécialistes (liste triée/filtrée — nom, tarif, disponibilité)

    * Utilisation de Stream API pour filtrer par spécialité et trier par tarif
* Étape 4 : Vérification des créneaux horaires du spécialiste (créneaux futurs disponibles, réservés, passés)
* Étape 5 : Création et liaison de la demande

    * Contenu : question au spécialiste, lien à la consultation, statut `EN_ATTENTE_AVIS_SPECIALISTE`, priorité (URGENTE/NORMALE/NON_URGENTE)
    * Notification du spécialiste

---

## Communication entre professionnels

* Objectifs : confirmer ou orienter un diagnostic, interpréter des examens (imagerie, biologiques), proposer une stratégie thérapeutique, conseiller la prise en charge.

### Modalités d'échange

* **Synchrone** : visioconférence ou appel téléphonique — réponse immédiate.
* **Asynchrone** : envoi du dossier via la plateforme — réponse écrite sous 24–48h.

---

## Exigences fonctionnelles

### Module Authentification

* Login / Logout
* Rôles : `GENERALISTE`, `SPECIALISTE`, `INFIRMIER`

### Module Infirmier

**US1 — Accueil du patient**

* Recherche patient

    * Si patient existant : afficher infos, saisir nouveaux signes vitaux, ajouter à la file d’attente
    * Si nouveau patient : créer dossier (nom, prénom, date de naissance, N° sécurité sociale, téléphone, adresse optionnelle, signes vitaux) puis l’ajouter à la file d’attente

**US2 — Voir la liste des patients enregistrés**

* Liste simple des patients du jour (Nom, prénom, heure d'arrivée, signes vitaux, N° sécurité sociale)
* Tri par heure d'arrivée (ancien → récent)
* Utilisation Stream API : filtrer par date d'enregistrement

### Module Médecin Généraliste

**US1 — Créer une consultation**

* Sélectionner un patient
* Saisir motif et observations
* Coût consultation : 150 DH (fixe)

**US3 — Demander une expertise**

* Choix de la spécialité
* Filtrer spécialistes par spécialité et tarif (Stream API)
* Voir créneaux prédéfinis et sélectionner un créneau
* Poser une question et joindre données/examens

**US4 — Voir le coût total**

* Calcul du total = consultation (150 DH) + tarif spécialiste + actes techniques
* Utilisation de Lambda (map().sum()) pour le calcul

### Module Médecin Spécialiste

**US5 — Configurer son profil**

* Définir tarif
* Définir spécialité
* Durée moyenne consultation : 30 min (fixe)

**US6 — Voir ses créneaux**

* Créneaux fixes de 30 min (ex. 09:00–09:30, 09:30–10:00, ...)
* Mises à jour automatiques : réservation → indisponible, créneau passé → archivé, annulation → redevenir disponible

**US7 — Consulter les demandes d'expertise**

* Liste des demandes reçues
* Filtrer par statut (`EN_ATTENTE`, `TERMINEE`) et priorité (Stream API)
* Voir détails patient + question

**US8 — Répondre à une expertise**

* Saisir l’avis médical, recommandations
* Marquer la demande comme terminée

---

## Actes techniques médicaux (exemples)

* Radiographie
* Échographie
* IRM
* Électrocardiogramme
* Actes dermatologiques (laser)
* Fond d’œil
* Analyses de sang
* Analyse d’urine

---

## Bonus — gestion du staff

* **Option 1** : Ajouter le staff directement via scripts SQL (rapide pour tests).
* **Option 2** : Créer un rôle `ADMIN` et une interface pour gérer le staff (plus complet pour un projet réel).

---

## Exigences techniques

* Gestion de projet : Maven
* Serveur HTTP : Tomcat / Jetty / GlassFish / Netty
* Plateforme : Jakarta EE
* Technologies : Servlet, JSP, JSTL, JPA / Hibernate
* Authentification stateful (sessions)
* Sécurité : bcrypt pour hash des mots de passe, protection CSRF
* Tests : JUnit, Mockito
* Patterns / API Java : Streams API, Lambdas

---

## Installation (rapide)

1. Prérequis : Java 11+ installé, Maven, base de données MySQL/MariaDB
2. Cloner le projet
3. Configurer `application.properties` / `persistence.xml` pour la connexion DB
4. Exécuter : `mvn clean package`
5. Déployer le WAR sur Tomcat ou `mvn tomcat7:run` (selon plugin)

---

## Notes & bonnes pratiques

* Utiliser des DTOs pour éviter d’exposer les entités JPA directement.
* Gérer les transactions côté service et centraliser la logique métier.
* Valider toutes les saisies (formulaires) côté serveur et client.
* Journaliser les actions sensibles (création demande, réponse, annulation)

---

## Contact

Pour modifications ou questions : ajouter ici le nom du responsable projet / repo GitHub / contact.

---

*README généré automatiquement — adapte-le selon ton architecture et les conventions de ton équipe.*
