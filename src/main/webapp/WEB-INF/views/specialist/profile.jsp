<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html lang="fr">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Configuration Profil - Télé-Expertise Médicale</title>
    <style>
        body { font-family: Arial, sans-serif; background-color: #f4f4f4; margin: 0; padding: 20px; }
        .container { max-width: 600px; margin: 0 auto; background: white; padding: 30px; border-radius: 8px; box-shadow: 0 2px 10px rgba(0,0,0,0.1); }
        .header { margin-bottom: 30px; }
        h1 { color: #333; margin: 0; }
        .current-info { background: #f8f9fa; padding: 15px; border-radius: 4px; margin-bottom: 20px; }
        .form-group { margin-bottom: 20px; }
        label { display: block; margin-bottom: 5px; font-weight: bold; color: #333; }
        select, input { width: 100%; padding: 10px; border: 1px solid #ddd; border-radius: 4px; font-size: 14px; }
        .btn { background-color: #28a745; color: white; padding: 12px 24px; border: none; border-radius: 4px; cursor: pointer; font-size: 16px; }
        .btn:hover { background-color: #218838; }
        .btn-secondary { background-color: #6c757d; margin-right: 10px; }
        .btn-secondary:hover { background-color: #5a6268; }
        .error { background-color: #f8d7da; color: #721c24; padding: 10px; border-radius: 4px; margin-bottom: 20px; }
        .info { background: #e7f3ff; padding: 15px; border-radius: 4px; margin-bottom: 20px; }
    </style>
</head>
<body>
    <div class="container">
        <div class="header">
            <h1>⚙️ Configuration du Profil</h1>
        </div>

        <c:if test="${not empty error}">
            <div class="error">${error}</div>
        </c:if>

        <div class="current-info">
            <h3>Informations actuelles</h3>
            <p><strong>Nom :</strong> Dr. ${sessionScope.user.prenom} ${sessionScope.user.nom}</p>
            <p><strong>Spécialité :</strong> 
                <c:choose>
                    <c:when test="${sessionScope.user.specialite != null}">
                        ${sessionScope.user.specialite.nom}
                    </c:when>
                    <c:otherwise>
                        <em>Non définie</em>
                    </c:otherwise>
                </c:choose>
            </p>
            <p><strong>Tarif :</strong> 
                <c:choose>
                    <c:when test="${sessionScope.user.tarif != null}">
                        ${sessionScope.user.tarif} DH
                    </c:when>
                    <c:otherwise>
                        <em>Non défini</em>
                    </c:otherwise>
                </c:choose>
            </p>
        </div>

        <div class="info">
            <strong>📋 Durée de consultation :</strong> 30 minutes (fixe)<br>
            <strong>💡 Conseil :</strong> Définissez votre spécialité et tarif pour recevoir des demandes d'expertise.
        </div>

        <form method="post" action="${pageContext.request.contextPath}/specialist/profile">
            <div class="form-group">
                <label for="specialiteId">Spécialité :</label>
                <select id="specialiteId" name="specialiteId" required>
                    <option value="">-- Choisir votre spécialité --</option>
                    <c:forEach var="specialite" items="${specialites}">
                        <option value="${specialite.id}" 
                                <c:if test="${sessionScope.user.specialite != null && sessionScope.user.specialite.id == specialite.id}">selected</c:if>>
                            ${specialite.nom}
                        </option>
                    </c:forEach>
                </select>
            </div>

            <div class="form-group">
                <label for="tarif">Tarif de consultation (DH) :</label>
                <input type="number" id="tarif" name="tarif" step="0.01" min="0" 
                       value="${sessionScope.user.tarif}" 
                       placeholder="Ex: 300.00" required>
            </div>

            <div>
                <a href="${pageContext.request.contextPath}/dashboard/specialiste" class="btn btn-secondary">Annuler</a>
                <button type="submit" class="btn">Sauvegarder</button>
            </div>
        </form>
    </div>
</body>
</html>