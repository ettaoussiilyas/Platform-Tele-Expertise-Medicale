<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html lang="fr">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Dashboard Spécialiste - Télé-Expertise Médicale</title>
    <style>
        body { font-family: Arial, sans-serif; background-color: #f4f4f4; margin: 0; padding: 20px; }
        .container { max-width: 1000px; margin: 0 auto; background: white; padding: 30px; border-radius: 8px; box-shadow: 0 2px 10px rgba(0,0,0,0.1); }
        .header { display: flex; justify-content: space-between; align-items: center; margin-bottom: 30px; border-bottom: 1px solid #eee; padding-bottom: 20px; }
        h1 { color: #333; margin: 0; }
        .user-info { color: #666; }
        .logout-btn { background-color: #dc3545; color: white; padding: 8px 16px; text-decoration: none; border-radius: 4px; }
        .logout-btn:hover { background-color: #c82333; }
        .role-badge { background-color: #6f42c1; color: white; padding: 4px 12px; border-radius: 20px; font-size: 12px; }
        .actions { display: grid; grid-template-columns: repeat(auto-fit, minmax(300px, 1fr)); gap: 20px; margin-top: 30px; }
        .action-card { background: #f8f9fa; padding: 20px; border-radius: 8px; border-left: 4px solid #6f42c1; }
        .action-card h3 { color: #333; margin-top: 0; }
        .action-card p { color: #666; margin-bottom: 15px; }
        .btn { background-color: #6f42c1; color: white; padding: 10px 20px; text-decoration: none; border-radius: 4px; display: inline-block; }
        .btn:hover { background-color: #5a32a3; }
        .profile-info { background: #f0e6ff; padding: 15px; border-radius: 4px; margin-top: 20px; }
    </style>
</head>
<body>
    <div class="container">
        <div class="header">
            <div>
                <h1>Dashboard Spécialiste</h1>
                <span class="role-badge">SPECIALISTE</span>
            </div>
            <div class="user-info">
                Bienvenue, Dr. ${sessionScope.user.prenom} ${sessionScope.user.nom}
                <a href="${pageContext.request.contextPath}/logout" class="logout-btn">Déconnexion</a>
            </div>
        </div>
        
        <div class="actions">
            <div class="action-card">
                <h3>⚙️ Configurer Profil</h3>
                <p>Définir tarif, spécialité et durée moyenne consultation (30 min fixe).</p>
                <a href="${pageContext.request.contextPath}/specialist/profile" class="btn">Configurer Profil</a>
            </div>
            
            <div class="action-card">
                <h3>📅 Gérer Créneaux</h3>
                <p>Voir créneaux fixes de 30 min, gérer disponibilités et réservations automatiques.</p>
                <a href="${pageContext.request.contextPath}/specialist/slots" class="btn">Gérer Créneaux</a>
            </div>
            
            <div class="action-card">
                <h3>📨 Demandes d'Expertise</h3>
                <p>Consulter demandes reçues, filtrer par statut et priorité, voir détails patients.</p>
                <a href="${pageContext.request.contextPath}/expertise/list" class="btn">Voir Demandes</a>
            </div>
            
            <div class="action-card">
                <h3>✍️ Répondre Expertise</h3>
                <p>Saisir avis médical, recommandations et marquer demandes comme terminées.</p>
                <a href="${pageContext.request.contextPath}/expertise/list" class="btn">Répondre Expertise</a>
            </div>
        </div>
        
        <div class="profile-info">
            <h4>👨⚕️ Informations Profil</h4>
            <p><strong>Spécialité :</strong> ${sessionScope.user.specialite != null ? sessionScope.user.specialite.specialiteName : 'Non définie'}</p>
            <p><strong>Tarif :</strong> ${sessionScope.user.tarif != null ? sessionScope.user.tarif : 'Non défini'} DH</p>
            <p><strong>Durée consultation :</strong> 30 minutes (fixe)</p>
        </div>
    </div>
</body>
</html>