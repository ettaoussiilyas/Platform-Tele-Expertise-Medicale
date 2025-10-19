<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html lang="fr">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Calculateur de Coût - Télé-Expertise Médicale</title>
    <style>
        body { font-family: Arial, sans-serif; background-color: #f4f4f4; margin: 0; padding: 20px; }
        .container { max-width: 900px; margin: 0 auto; background: white; padding: 30px; border-radius: 8px; box-shadow: 0 2px 10px rgba(0,0,0,0.1); }
        .header { margin-bottom: 30px; }
        h1 { color: #333; margin: 0; }
        .form-group { margin-bottom: 20px; }
        label { display: block; margin-bottom: 5px; font-weight: bold; color: #333; }
        select { width: 100%; padding: 10px; border: 1px solid #ddd; border-radius: 4px; font-size: 14px; }
        .checkbox-group { display: grid; grid-template-columns: repeat(auto-fit, minmax(250px, 1fr)); gap: 10px; margin-top: 10px; }
        .checkbox-item { display: flex; align-items: center; padding: 10px; background: #f8f9fa; border-radius: 4px; }
        .checkbox-item input { margin-right: 10px; }
        .btn { background-color: #28a745; color: white; padding: 12px 24px; border: none; border-radius: 4px; cursor: pointer; font-size: 16px; }
        .btn:hover { background-color: #218838; }
        .btn-secondary { background-color: #6c757d; margin-right: 10px; }
        .btn-secondary:hover { background-color: #5a6268; }
        .error { background-color: #f8d7da; color: #721c24; padding: 10px; border-radius: 4px; margin-bottom: 20px; }
        .result { background: #d4edda; color: #155724; padding: 20px; border-radius: 4px; margin-top: 20px; }
        .cost-breakdown { background: #f8f9fa; padding: 20px; border-radius: 4px; margin-top: 20px; }
        .cost-item { display: flex; justify-content: space-between; padding: 8px 0; border-bottom: 1px solid #eee; }
        .cost-total { font-weight: bold; font-size: 18px; color: #28a745; border-top: 2px solid #28a745; padding-top: 10px; margin-top: 10px; }
        .info { background: #e7f3ff; padding: 15px; border-radius: 4px; margin-bottom: 20px; }
    </style>
</head>
<body>
    <div class="container">
        <div class="header">
            <h1>💰 Calculateur de Coût Total</h1>
        </div>

        <c:if test="${not empty error}">
            <div class="error">${error}</div>
        </c:if>

        <div class="info">
            <strong>📋 Formule de calcul :</strong> Consultation (150 DH) + Tarif spécialiste + Actes techniques
        </div>

        <form method="post" action="${pageContext.request.contextPath}/cost/calculator">
            <div class="form-group">
                <label for="consultationId">Sélectionner la Consultation :</label>
                <select id="consultationId" name="consultationId" required>
                    <option value="">-- Choisir une consultation --</option>
                    <c:forEach var="consultation" items="${consultations}">
                        <option value="${consultation.id}" 
                                <c:if test="${consultation.id == param.consultationId}">selected</c:if>>
                            ${consultation.patient.nom} ${consultation.patient.prenom} - ${consultation.dateConsultation} (${consultation.statut})
                        </option>
                    </c:forEach>
                </select>
            </div>

            <div class="form-group">
                <label>Actes Techniques (optionnel) :</label>
                <div class="checkbox-group">
                    <c:forEach var="acte" items="${actesTechniques}">
                        <div class="checkbox-item">
                            <input type="checkbox" id="acte_${acte.id}" name="acteTechniqueIds" value="${acte.id}">
                            <label for="acte_${acte.id}">
                                ${acte.nom} - ${acte.tarif} DH
                                <c:if test="${not empty acte.description}">
                                    <br><small style="color: #666;">${acte.description}</small>
                                </c:if>
                            </label>
                        </div>
                    </c:forEach>
                </div>
            </div>

            <div>
                <a href="${pageContext.request.contextPath}/dashboard/generaliste" class="btn btn-secondary">Retour</a>
                <button type="submit" class="btn">Calculer le Coût</button>
            </div>
        </form>

        <c:if test="${calculationDone}">
            <div class="result">
                <h3>✅ Calcul terminé pour la consultation</h3>
                <p><strong>Patient :</strong> ${consultation.patient.nom} ${consultation.patient.prenom}</p>
                <p><strong>Date :</strong> ${consultation.dateConsultation}</p>
            </div>

            <div class="cost-breakdown">
                <h4>Détail des Coûts</h4>
                <div class="cost-item">
                    <span>Consultation généraliste</span>
                    <span>150.00 DH</span>
                </div>
                <c:if test="${not empty consultation.demandesExpertises}">
                    <c:forEach var="demande" items="${consultation.demandesExpertises}">
                        <div class="cost-item">
                            <span>Expertise ${demande.specialiste.specialite.nom}</span>
                            <span>${demande.specialiste.tarif} DH</span>
                        </div>
                    </c:forEach>
                </c:if>
                <div class="cost-item cost-total">
                    <span>TOTAL</span>
                    <span>${totalCost} DH</span>
                </div>
            </div>
        </c:if>
    </div>
</body>
</html>