<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html lang="fr">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Demander Expertise - Télé-Expertise Médicale</title>
    <style>
        body { font-family: Arial, sans-serif; background-color: #f4f4f4; margin: 0; padding: 20px; }
        .container { max-width: 800px; margin: 0 auto; background: white; padding: 30px; border-radius: 8px; box-shadow: 0 2px 10px rgba(0,0,0,0.1); }
        .header { margin-bottom: 30px; }
        h1 { color: #333; margin: 0; }
        .patient-info { background: #f8f9fa; padding: 15px; border-radius: 4px; margin-bottom: 20px; }
        .form-group { margin-bottom: 20px; }
        label { display: block; margin-bottom: 5px; font-weight: bold; color: #333; }
        select, textarea { width: 100%; padding: 10px; border: 1px solid #ddd; border-radius: 4px; font-size: 14px; }
        textarea { height: 120px; resize: vertical; }
        .btn { background-color: #28a745; color: white; padding: 12px 24px; border: none; border-radius: 4px; cursor: pointer; font-size: 16px; }
        .btn:hover { background-color: #218838; }
        .btn-secondary { background-color: #6c757d; margin-right: 10px; }
        .btn-secondary:hover { background-color: #5a6268; }
        .error { background-color: #f8d7da; color: #721c24; padding: 10px; border-radius: 4px; margin-bottom: 20px; }
        .priority-info { font-size: 12px; color: #666; margin-top: 5px; }
    </style>
</head>
<body>
    <div class="container">
        <div class="header">
            <h1>Demander une Expertise</h1>
        </div>

        <c:if test="${not empty error}">
            <div class="error">${error}</div>
        </c:if>

        <div class="patient-info">
            <h3>👤 Informations Patient</h3>
            <p><strong>Nom :</strong> ${consultation.patient.nom} ${consultation.patient.prenom}</p>
            <p><strong>N° Sécurité Sociale :</strong> ${consultation.patient.numeroSecuriteSociale}</p>
            <p><strong>Notes de consultation :</strong> ${consultation.notes}</p>
        </div>

        <form method="post" action="${pageContext.request.contextPath}/expertise/request">
            <input type="hidden" name="consultationId" value="${consultation.id}">
            
            <div class="form-group">
                <label for="specialiteId">Spécialité requise :</label>
                <select id="specialiteId" name="specialiteId" required>
                    <option value="">-- Choisir une spécialité --</option>
                    <c:forEach var="specialite" items="${specialites}">
                        <option value="${specialite.id}">${specialite.nom}</option>
                    </c:forEach>
                </select>
            </div>

            <div class="form-group">
                <label for="priorite">Priorité :</label>
                <select id="priorite" name="priorite" required>
                    <c:forEach var="priorite" items="${priorites}">
                        <option value="${priorite}">${priorite}</option>
                    </c:forEach>
                </select>
                <div class="priority-info">
                    URGENTE: Réponse sous 2h | NORMALE: Réponse sous 24h | NON_URGENTE: Réponse sous 48h
                </div>
            </div>

            <div class="form-group">
                <label for="question">Question au spécialiste :</label>
                <textarea id="question" name="question" placeholder="Décrivez votre question, les examens réalisés, et ce que vous souhaitez savoir du spécialiste..." required></textarea>
            </div>

            <div>
                <a href="${pageContext.request.contextPath}/consultation/list" class="btn btn-secondary">Annuler</a>
                <button type="submit" class="btn">Envoyer la Demande</button>
            </div>
        </form>
    </div>
</body>
</html>