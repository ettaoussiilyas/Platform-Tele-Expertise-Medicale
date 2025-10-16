<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html lang="fr">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Dashboard Infirmier - Télé-Expertise Médicale</title>
    <style>
        body { font-family: Arial, sans-serif; background-color: #f4f4f4; margin: 0; padding: 20px; }
        .container { max-width: 1000px; margin: 0 auto; background: white; padding: 30px; border-radius: 8px; box-shadow: 0 2px 10px rgba(0,0,0,0.1); }
        .header { display: flex; justify-content: space-between; align-items: center; margin-bottom: 30px; border-bottom: 1px solid #eee; padding-bottom: 20px; }
        h1 { color: #333; margin: 0; }
        .user-info { color: #666; }
        .logout-btn { background-color: #dc3545; color: white; padding: 8px 16px; text-decoration: none; border-radius: 4px; }
        .logout-btn:hover { background-color: #c82333; }
        .role-badge { background-color: #17a2b8; color: white; padding: 4px 12px; border-radius: 20px; font-size: 12px; }
        .actions { display: grid; grid-template-columns: repeat(auto-fit, minmax(300px, 1fr)); gap: 20px; margin-top: 30px; }
        .action-card { background: #f8f9fa; padding: 20px; border-radius: 8px; border-left: 4px solid #17a2b8; }
        .action-card h3 { color: #333; margin-top: 0; }
        .action-card p { color: #666; margin-bottom: 15px; }
        .btn { background-color: #17a2b8; color: white; padding: 10px 20px; text-decoration: none; border-radius: 4px; display: inline-block; }
        .btn:hover { background-color: #138496; }
    </style>
</head>
<body>
    <div class="container">
        <div class="header">
            <div>
                <h1>Dashboard Infirmier</h1>
                <span class="role-badge">INFIRMIER</span>
            </div>
            <div class="user-info">
                Bienvenue, ${sessionScope.user.prenom} ${sessionScope.user.nom}
                <a href="${pageContext.request.contextPath}/logout" class="logout-btn">Déconnexion</a>
            </div>
        </div>
        
        <div class="actions">
            <div class="action-card">
                <h3>📋 Accueil Patient</h3>
                <p>Enregistrer un nouveau patient ou rechercher un patient existant. Saisir les données administratives et médicales.</p>
                <a href="${pageContext.request.contextPath}/patient/register" class="btn">Nouveau Patient</a>
                <a href="${pageContext.request.contextPath}/patient/search" class="btn" style="margin-left: 10px;">Rechercher</a>
            </div>
            
            <div class="action-card">
                <h3>🩺 Signes Vitaux</h3>
                <p>Saisir les signes vitaux : tension artérielle, fréquence cardiaque, température, fréquence respiratoire, poids/taille.</p>
                <a href="${pageContext.request.contextPath}/patient/register" class="btn">Saisir Signes Vitaux</a>
            </div>
            
            <div class="action-card">
                <h3>📝 Liste des Patients</h3>
                <p>Voir la liste des patients enregistrés du jour, triés par heure d'arrivée avec leurs informations.</p>
                <a href="${pageContext.request.contextPath}/patient/list" class="btn">Voir Liste Patients</a>
            </div>
            
            <div class="action-card">
                <h3>⏰ File d'Attente</h3>
                <p>Gérer la file d'attente des patients pour les consultations avec les médecins généralistes.</p>
                <a href="${pageContext.request.contextPath}/patient/list" class="btn">Gérer File d'Attente</a>
            </div>
        </div>
    </div>
</body>
</html>