<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="fr">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Télé-Expertise Médicale - Plateforme Médicale</title>
    <style>
        * { margin: 0; padding: 0; box-sizing: border-box; }
        body { font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif; background: linear-gradient(135deg, #667eea 0%, #764ba2 100%); min-height: 100vh; }
        .header { background: rgba(255,255,255,0.1); backdrop-filter: blur(10px); padding: 20px 0; }
        .nav { max-width: 1200px; margin: 0 auto; display: flex; justify-content: space-between; align-items: center; padding: 0 20px; }
        .logo { color: white; font-size: 24px; font-weight: bold; }
        .nav-links { display: flex; gap: 20px; }
        .nav-links a { color: white; text-decoration: none; padding: 8px 16px; border-radius: 20px; transition: all 0.3s; }
        .nav-links a:hover { background: rgba(255,255,255,0.2); }
        .hero { max-width: 1200px; margin: 0 auto; padding: 80px 20px; text-align: center; color: white; }
        .hero h1 { font-size: 3.5rem; margin-bottom: 20px; text-shadow: 2px 2px 4px rgba(0,0,0,0.3); }
        .hero .subtitle { font-size: 1.3rem; margin-bottom: 40px; opacity: 0.9; }
        .hero .description { font-size: 1.1rem; margin-bottom: 50px; max-width: 800px; margin-left: auto; margin-right: auto; line-height: 1.6; }
        .buttons { display: flex; gap: 20px; justify-content: center; flex-wrap: wrap; }
        .btn { padding: 15px 30px; text-decoration: none; border-radius: 50px; font-weight: 600; font-size: 16px; transition: all 0.3s; box-shadow: 0 4px 15px rgba(0,0,0,0.2); }
        .btn-primary { background: #fff; color: #667eea; }
        .btn-primary:hover { transform: translateY(-2px); box-shadow: 0 6px 20px rgba(0,0,0,0.3); }
        .btn-secondary { background: transparent; color: white; border: 2px solid white; }
        .btn-secondary:hover { background: white; color: #667eea; transform: translateY(-2px); }
        .features { background: white; padding: 80px 20px; }
        .features-container { max-width: 1200px; margin: 0 auto; }
        .features h2 { text-align: center; font-size: 2.5rem; margin-bottom: 60px; color: #333; }
        .features-grid { display: grid; grid-template-columns: repeat(auto-fit, minmax(300px, 1fr)); gap: 40px; }
        .feature-card { text-align: center; padding: 30px; border-radius: 15px; box-shadow: 0 5px 15px rgba(0,0,0,0.1); transition: transform 0.3s; }
        .feature-card:hover { transform: translateY(-5px); }
        .feature-icon { font-size: 3rem; margin-bottom: 20px; }
        .feature-card h3 { font-size: 1.5rem; margin-bottom: 15px; color: #333; }
        .feature-card p { color: #666; line-height: 1.6; }
        .roles { background: #f8f9fa; padding: 80px 20px; }
        .roles-container { max-width: 1200px; margin: 0 auto; }
        .roles h2 { text-align: center; font-size: 2.5rem; margin-bottom: 60px; color: #333; }
        .roles-grid { display: grid; grid-template-columns: repeat(auto-fit, minmax(250px, 1fr)); gap: 30px; }
        .role-card { background: white; padding: 30px; border-radius: 15px; text-align: center; box-shadow: 0 5px 15px rgba(0,0,0,0.1); }
        .role-card .role-icon { font-size: 4rem; margin-bottom: 20px; }
        .role-card h3 { font-size: 1.3rem; margin-bottom: 15px; color: #333; }
        .role-card ul { list-style: none; text-align: left; }
        .role-card li { padding: 5px 0; color: #666; }
        .role-card li:before { content: '✓'; color: #28a745; font-weight: bold; margin-right: 10px; }
        .footer { background: #333; color: white; text-align: center; padding: 40px 20px; }
        @media (max-width: 768px) {
            .hero h1 { font-size: 2.5rem; }
            .buttons { flex-direction: column; align-items: center; }
            .nav { flex-direction: column; gap: 20px; }
        }
    </style>
</head>
<body>
    <header class="header">
        <nav class="nav">
            <div class="logo">🏥 Télé-Expertise</div>
            <div class="nav-links">
                <a href="#features">Fonctionnalités</a>
                <a href="#roles">Rôles</a>
                <a href="login">Connexion</a>
            </div>
        </nav>
    </header>

    <section class="hero">
        <h1>Télé-Expertise Médicale</h1>
        <p class="subtitle">Optimisez le parcours patient avec notre plateforme innovante</p>
        <p class="description">
            Facilitez la coordination entre médecins généralistes, spécialistes et infirmiers. 
            Notre système permet une prise en charge efficace et rapide grâce à la télé-expertise 
            en mode synchrone et asynchrone.
        </p>
        <div class="buttons">
            <a href="login" class="btn btn-primary">🔐 Se connecter</a>
            <a href="register" class="btn btn-secondary">📝 S'inscrire</a>
        </div>
    </section>

    <section class="features" id="features">
        <div class="features-container">
            <h2>Fonctionnalités Principales</h2>
            <div class="features-grid">
                <div class="feature-card">
                    <div class="feature-icon">👨‍⚕️</div>
                    <h3>Consultation Médicale</h3>
                    <p>Création et gestion des consultations avec suivi complet du dossier patient et calcul automatique des coûts.</p>
                </div>
                <div class="feature-card">
                    <div class="feature-icon">🔍</div>
                    <h3>Demande d'Expertise</h3>
                    <p>Sollicitez l'avis de spécialistes avec filtrage par spécialité, tarif et disponibilité en temps réel.</p>
                </div>
                <div class="feature-card">
                    <div class="feature-icon">📅</div>
                    <h3>Gestion des Créneaux</h3>
                    <p>Système automatisé de créneaux de 30 minutes avec mise à jour en temps réel des disponibilités.</p>
                </div>
                <div class="feature-card">
                    <div class="feature-icon">💬</div>
                    <h3>Communication Synchrone/Asynchrone</h3>
                    <p>Échanges immédiats par visioconférence ou réponses écrites sous 24-48h selon la priorité.</p>
                </div>
                <div class="feature-card">
                    <div class="feature-icon">💰</div>
                    <h3>Calcul des Coûts</h3>
                    <p>Calcul automatique : consultation (150 DH) + tarif spécialiste + actes techniques.</p>
                </div>
                <div class="feature-card">
                    <div class="feature-icon">📊</div>
                    <h3>Suivi et Reporting</h3>
                    <p>Tableaux de bord personnalisés avec suivi des consultations, demandes et statistiques.</p>
                </div>
            </div>
        </div>
    </section>

    <section class="roles" id="roles">
        <div class="roles-container">
            <h2>Rôles et Responsabilités</h2>
            <div class="roles-grid">
                <div class="role-card">
                    <div class="role-icon">👩‍⚕️</div>
                    <h3>Infirmier</h3>
                    <ul>
                        <li>Accueil et enregistrement patients</li>
                        <li>Saisie des signes vitaux</li>
                        <li>Gestion de la file d'attente</li>
                        <li>Suivi des patients du jour</li>
                    </ul>
                </div>
                <div class="role-card">
                    <div class="role-icon">👨‍⚕️</div>
                    <h3>Médecin Généraliste</h3>
                    <ul>
                        <li>Création de consultations</li>
                        <li>Demandes d'expertise</li>
                        <li>Diagnostic et prescriptions</li>
                        <li>Calcul des coûts totaux</li>
                    </ul>
                </div>
                <div class="role-card">
                    <div class="role-icon">🔬</div>
                    <h3>Médecin Spécialiste</h3>
                    <ul>
                        <li>Configuration du profil</li>
                        <li>Gestion des créneaux</li>
                        <li>Réponse aux expertises</li>
                        <li>Avis médicaux spécialisés</li>
                    </ul>
                </div>
            </div>
        </div>
    </section>

    <footer class="footer">
        <p>&copy; 2024 Système de Télé-Expertise Médicale. Tous droits réservés.</p>
        <p>Plateforme développée avec Jakarta EE, JPA/Hibernate, et les meilleures pratiques de sécurité.</p>
    </footer>
</body>
</html>