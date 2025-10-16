<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html lang="fr">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Dashboard Généraliste - Télé-Expertise Médicale</title>
    <style>
        body { font-family: Arial, sans-serif; background-color: #f4f4f4; margin: 0; padding: 20px; }
        .container { max-width: 1000px; margin: 0 auto; background: white; padding: 30px; border-radius: 8px; box-shadow: 0 2px 10px rgba(0,0,0,0.1); }
        .header { display: flex; justify-content: space-between; align-items: center; margin-bottom: 30px; border-bottom: 1px solid #eee; padding-bottom: 20px; }
        h1 { color: #333; margin: 0; }
        .user-info { color: #666; }
        .logout-btn { background-color: #dc3545; color: white; padding: 8px 16px; text-decoration: none; border-radius: 4px; }
        .logout-btn:hover { background-color: #c82333; }
        .role-badge { background-color: #28a745; color: white; padding: 4px 12px; border-radius: 20px; font-size: 12px; }
        .actions { display: grid; grid-template-columns: repeat(auto-fit, minmax(300px, 1fr)); gap: 20px; margin-top: 30px; }
        .action-card { background: #f8f9fa; padding: 20px; border-radius: 8px; border-left: 4px solid #28a745; }
        .action-card h3 { color: #333; margin-top: 0; }
        .action-card p { color: #666; margin-bottom: 15px; }
        .btn { background-color: #28a745; color: white; padding: 10px 20px; text-decoration: none; border-radius: 4px; display: inline-block; }
        .btn:hover { background-color: #218838; }
        .cost-info { background: #e7f3ff; padding: 15px; border-radius: 4px; margin-top: 20px; }
    </style>
</head>
<body>
    <div class="container">
        <div class="header">
            <div>
                <h1>Dashboard Généraliste</h1>
                <span class="role-badge">GENERALISTE</span>
            </div>
            <div class="user-info">
                Bienvenue, Dr. ${sessionScope.user.prenom} ${sessionScope.user.nom}
                <a href="${pageContext.request.contextPath}/logout" class="logout-btn">Déconnexion</a>
            </div>
        </div>
        
        <div class="actions">
            <div class="action-card">
                <h3>👨‍⚕️ Créer Consultation</h3>
                <p>Sélectionner un patient, saisir motif et observations. Coût consultation : 150 DH (fixe).</p>
                <a href="#" class="btn">Nouvelle Consultation</a>
            </div>
            
            <div class="action-card">
                <h3>🔍 Demander Expertise</h3>
                <p>Choisir spécialité, filtrer spécialistes par tarif, sélectionner créneaux et poser questions aux spécialistes.</p>
                <a href="#" class="btn">Demander Expertise</a>
            </div>
            
            <div class="action-card">
                <h3>💰 Calculer Coût Total</h3>
                <p>Voir le coût total = consultation (150 DH) + tarif spécialiste + actes techniques.</p>
                <a href="#" class="btn">Calculer Coût</a>
            </div>
            
            <div class="action-card">
                <h3>📋 Mes Consultations</h3>
                <p>Voir toutes mes consultations en cours et terminées avec les patients.</p>
                <a href="#" class="btn">Voir Consultations</a>
            </div>
        </div>
        
        <div class="cost-info">
            <h4>💡 Information Tarification</h4>
            <p><strong>Consultation généraliste :</strong> 150 DH (fixe)</p>
            <p><strong>Calcul total :</strong> Consultation + Tarif spécialiste + Actes techniques</p>
        </div>
    </div>
</body>
</html>