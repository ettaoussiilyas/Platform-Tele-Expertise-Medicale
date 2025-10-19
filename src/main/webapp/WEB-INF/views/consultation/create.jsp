<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html lang="fr">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Créer Consultation - Télé-Expertise Médicale</title>
    <style>
        body { font-family: Arial, sans-serif; background-color: #f4f4f4; margin: 0; padding: 20px; }
        .container { max-width: 800px; margin: 0 auto; background: white; padding: 30px; border-radius: 8px; box-shadow: 0 2px 10px rgba(0,0,0,0.1); }
        .header { margin-bottom: 30px; }
        h1 { color: #333; margin: 0; }
        .form-group { margin-bottom: 20px; }
        label { display: block; margin-bottom: 5px; font-weight: bold; color: #333; }
        select, textarea { width: 100%; padding: 10px; border: 1px solid #ddd; border-radius: 4px; font-size: 14px; }
        textarea { height: 120px; resize: vertical; }
        .btn { background-color: #28a745; color: white; padding: 12px 24px; border: none; border-radius: 4px; cursor: pointer; font-size: 16px; }
        .btn:hover { background-color: #218838; }
        .btn-secondary { background-color: #6c757d; margin-right: 10px; }
        .btn-secondary:hover { background-color: #5a6268; }
        .error { background-color: #f8d7da; color: #721c24; padding: 10px; border-radius: 4px; margin-bottom: 20px; }
        .cost-info { background: #e7f3ff; padding: 15px; border-radius: 4px; margin-bottom: 20px; }
    </style>
</head>
<body>
    <div class="container">
        <div class="header">
            <h1>Créer une Consultation</h1>
        </div>

        <c:if test="${not empty error}">
            <div class="error">${error}</div>
        </c:if>

        <div class="cost-info">
            <strong>💰 Coût de consultation :</strong> 150 DH (fixe)
        </div>

        <form method="post" action="${pageContext.request.contextPath}/consultation/create">
            <div class="form-group">
                <label for="patientId">Sélectionner le Patient :</label>
                <select id="patientId" name="patientId" required>
                    <option value="">-- Choisir un patient --</option>
                    <c:forEach var="patient" items="${patients}">
                        <option value="${patient.id}">
                            ${patient.nom} ${patient.prenom} - ${patient.numeroSecuriteSociale}
                        </option>
                    </c:forEach>
                </select>
            </div>

            <div class="form-group">
                <label for="notes">Motif et Observations :</label>
                <textarea id="notes" name="notes" placeholder="Saisir le motif de consultation et vos observations..." required></textarea>
            </div>

            <div>
                <a href="${pageContext.request.contextPath}/dashboard/generaliste" class="btn btn-secondary">Annuler</a>
                <button type="submit" class="btn">Créer Consultation</button>
            </div>
        </form>
    </div>
</body>
</html>