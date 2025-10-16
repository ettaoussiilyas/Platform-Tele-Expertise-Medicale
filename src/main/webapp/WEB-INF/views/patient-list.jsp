<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html lang="fr">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Liste Patients - Télé-Expertise Médicale</title>
    <style>
        body { font-family: Arial, sans-serif; background-color: #f4f4f4; margin: 0; padding: 20px; }
        .container { max-width: 1200px; margin: 0 auto; background: white; padding: 30px; border-radius: 8px; box-shadow: 0 2px 10px rgba(0,0,0,0.1); }
        h2 { color: #333; margin-bottom: 30px; }
        .header-info { background: #e7f3ff; padding: 15px; border-radius: 8px; margin-bottom: 20px; }
        table { width: 100%; border-collapse: collapse; margin-top: 20px; }
        th, td { padding: 12px; text-align: left; border-bottom: 1px solid #ddd; }
        th { background-color: #17a2b8; color: white; }
        tr:hover { background-color: #f5f5f5; }
        .btn { background-color: #17a2b8; color: white; padding: 8px 16px; text-decoration: none; border-radius: 4px; font-size: 12px; }
        .btn:hover { background-color: #138496; }
        .btn-secondary { background-color: #6c757d; }
        .vital-signs { font-size: 11px; color: #666; }
        .time-badge { background-color: #28a745; color: white; padding: 2px 8px; border-radius: 12px; font-size: 11px; }
        .no-patients { text-align: center; color: #666; padding: 40px; }
    </style>
</head>
<body>
    <div class="container">
        <h2>📋 Liste des Patients du Jour</h2>
        
        <div class="header-info">
            <strong>📅 Date:</strong> ${java.time.LocalDate.now()} | 
            <strong>👥 Total patients:</strong> ${patients.size()} | 
            <strong>📊 Triés par heure d'arrivée (ancien → récent)</strong>
        </div>
        
        <div style="margin-bottom: 20px;">
            <a href="${pageContext.request.contextPath}/patient/register" class="btn">+ Nouveau Patient</a>
            <a href="${pageContext.request.contextPath}/patient/search" class="btn btn-secondary">🔍 Rechercher</a>
            <a href="${pageContext.request.contextPath}/dashboard/infirmier" class="btn btn-secondary">Retour Dashboard</a>
        </div>
        
        <c:choose>
            <c:when test="${not empty patients}">
                <table>
                    <thead>
                        <tr>
                            <th>Heure Arrivée</th>
                            <th>Nom Prénom</th>
                            <th>N° Sécurité Sociale</th>
                            <th>Téléphone</th>
                            <th>Signes Vitaux</th>
                            <th>Actions</th>
                        </tr>
                    </thead>
                    <tbody>
                        <c:forEach var="patient" items="${patients}">
                            <tr>
                                <td>
                                    <span class="time-badge">
                                        ${patient.createdAt.toLocalTime().toString().substring(0,5)}
                                    </span>
                                </td>
                                <td>
                                    <strong>${patient.nom} ${patient.prenom}</strong><br>
                                    <small style="color: #666;">${patient.email}</small>
                                </td>
                                <td>${patient.numeroSecuriteSociale}</td>
                                <td>${patient.telephone}</td>
                                <td class="vital-signs">
                                    <div><strong>Tension:</strong> ${patient.tension}</div>
                                    <div><strong>Temp:</strong> ${patient.temperature}°C</div>
                                    <div><strong>FC:</strong> ${patient.frequenceCardiaque} bpm</div>
                                    <div><strong>Poids:</strong> ${patient.poids} kg</div>
                                </td>
                                <td>
                                    <a href="#" class="btn" style="font-size: 11px;">Voir Détails</a>
                                </td>
                            </tr>
                        </c:forEach>
                    </tbody>
                </table>
            </c:when>
            <c:otherwise>
                <div class="no-patients">
                    <h3>Aucun patient enregistré aujourd'hui</h3>
                    <p>Commencez par enregistrer un nouveau patient.</p>
                    <a href="${pageContext.request.contextPath}/patient/register" class="btn">+ Enregistrer Premier Patient</a>
                </div>
            </c:otherwise>
        </c:choose>
    </div>
</body>
</html>