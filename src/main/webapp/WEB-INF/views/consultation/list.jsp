<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html lang="fr">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Mes Consultations - Télé-Expertise Médicale</title>
    <style>
        body { font-family: Arial, sans-serif; background-color: #f4f4f4; margin: 0; padding: 20px; }
        .container { max-width: 1200px; margin: 0 auto; background: white; padding: 30px; border-radius: 8px; box-shadow: 0 2px 10px rgba(0,0,0,0.1); }
        .header { display: flex; justify-content: space-between; align-items: center; margin-bottom: 30px; }
        h1 { color: #333; margin: 0; }
        .btn { background-color: #28a745; color: white; padding: 10px 20px; text-decoration: none; border-radius: 4px; }
        .btn:hover { background-color: #218838; }
        .success { background-color: #d4edda; color: #155724; padding: 10px; border-radius: 4px; margin-bottom: 20px; }
        table { width: 100%; border-collapse: collapse; margin-top: 20px; }
        th, td { padding: 12px; text-align: left; border-bottom: 1px solid #ddd; }
        th { background-color: #f8f9fa; font-weight: bold; }
        .status { padding: 4px 8px; border-radius: 4px; font-size: 12px; font-weight: bold; }
        .status-en-cours { background-color: #fff3cd; color: #856404; }
        .status-terminee { background-color: #d4edda; color: #155724; }
        .status-attente { background-color: #f8d7da; color: #721c24; }
        .no-data { text-align: center; color: #666; padding: 40px; }
    </style>
</head>
<body>
    <div class="container">
        <div class="header">
            <h1>Mes Consultations</h1>
            <a href="${pageContext.request.contextPath}/consultation/create" class="btn">Nouvelle Consultation</a>
        </div>

        <c:if test="${param.success == 'created'}">
            <div class="success">Consultation créée avec succès!</div>
        </c:if>

        <c:choose>
            <c:when test="${empty consultations}">
                <div class="no-data">
                    <p>Aucune consultation trouvée.</p>
                    <a href="${pageContext.request.contextPath}/consultation/create" class="btn">Créer votre première consultation</a>
                </div>
            </c:when>
            <c:otherwise>
                <table>
                    <thead>
                        <tr>
                            <th>Patient</th>
                            <th>Date</th>
                            <th>Statut</th>
                            <th>Tarif</th>
                            <th>Notes</th>
                            <th>Actions</th>
                        </tr>
                    </thead>
                    <tbody>
                        <c:forEach var="consultation" items="${consultations}">
                            <tr>
                                <td>${consultation.patient.nom} ${consultation.patient.prenom}</td>
                                <td>${consultation.dateConsultation}</td>
                                <td>
                                    <span class="status status-${consultation.statut.toString().toLowerCase().replace('_', '-')}">
                                        ${consultation.statut}
                                    </span>
                                </td>
                                <td>${consultation.tarif} DH</td>
                                <td>${consultation.notes}</td>
                                <td>
                                    <c:if test="${consultation.statut == 'EN_COURS'}">
                                        <a href="${pageContext.request.contextPath}/expertise/request?consultationId=${consultation.id}" class="btn">Demander Expertise</a>
                                    </c:if>
                                </td>
                            </tr>
                        </c:forEach>
                    </tbody>
                </table>
            </c:otherwise>
        </c:choose>

        <div style="margin-top: 30px;">
            <a href="${pageContext.request.contextPath}/dashboard/generaliste" class="btn" style="background-color: #6c757d;">Retour au Dashboard</a>
        </div>
    </div>
</body>
</html>