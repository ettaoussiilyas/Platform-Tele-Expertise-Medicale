<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html lang="fr">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Demandes d'Expertise - Télé-Expertise Médicale</title>
    <style>
        body { font-family: Arial, sans-serif; background-color: #f4f4f4; margin: 0; padding: 20px; }
        .container { max-width: 1200px; margin: 0 auto; background: white; padding: 30px; border-radius: 8px; box-shadow: 0 2px 10px rgba(0,0,0,0.1); }
        .header { display: flex; justify-content: space-between; align-items: center; margin-bottom: 30px; }
        h1 { color: #333; margin: 0; }
        .filters { display: flex; gap: 15px; margin-bottom: 20px; flex-wrap: wrap; }
        .filter-group { display: flex; flex-direction: column; }
        .filter-group label { font-size: 12px; color: #666; margin-bottom: 5px; }
        .filter-group select { padding: 8px; border: 1px solid #ddd; border-radius: 4px; }
        .success { background-color: #d4edda; color: #155724; padding: 10px; border-radius: 4px; margin-bottom: 20px; }
        .error { background-color: #f8d7da; color: #721c24; padding: 10px; border-radius: 4px; margin-bottom: 20px; }
        .demande-card { background: #f8f9fa; border-radius: 8px; padding: 20px; margin-bottom: 20px; border-left: 4px solid #6f42c1; }
        .demande-header { display: flex; justify-content: space-between; align-items: flex-start; margin-bottom: 15px; }
        .patient-info { font-weight: bold; color: #333; }
        .priority { padding: 4px 8px; border-radius: 4px; font-size: 12px; font-weight: bold; }
        .priority-urgente { background-color: #f8d7da; color: #721c24; }
        .priority-normale { background-color: #fff3cd; color: #856404; }
        .priority-non-urgente { background-color: #d4edda; color: #155724; }
        .status { padding: 4px 8px; border-radius: 4px; font-size: 12px; font-weight: bold; margin-left: 10px; }
        .status-en-attente { background-color: #fff3cd; color: #856404; }
        .status-terminee { background-color: #d4edda; color: #155724; }
        .question { background: white; padding: 15px; border-radius: 4px; margin: 15px 0; }
        .response-form { margin-top: 15px; }
        .response-form textarea { width: 100%; height: 100px; padding: 10px; border: 1px solid #ddd; border-radius: 4px; resize: vertical; }
        .btn { background-color: #6f42c1; color: white; padding: 8px 16px; border: none; border-radius: 4px; cursor: pointer; font-size: 14px; }
        .btn:hover { background-color: #5a32a3; }
        .btn-small { padding: 6px 12px; font-size: 12px; }
        .no-data { text-align: center; color: #666; padding: 40px; }
        .consultation-info { font-size: 14px; color: #666; margin-bottom: 10px; }
    </style>
</head>
<body>
    <div class="container">
        <div class="header">
            <h1>📨 Demandes d'Expertise Reçues</h1>
        </div>

        <c:if test="${param.success == 'responded'}">
            <div class="success">Réponse envoyée avec succès!</div>
        </c:if>

        <c:if test="${not empty param.error}">
            <div class="error">Erreur: ${param.error}</div>
        </c:if>

        <div class="filters">
            <div class="filter-group">
                <label>Filtrer par statut:</label>
                <select onchange="filterByStatus(this.value)">
                    <option value="">Tous</option>
                    <option value="EN_ATTENTE">En attente</option>
                    <option value="TERMINEE">Terminées</option>
                </select>
            </div>
            <div class="filter-group">
                <label>Filtrer par priorité:</label>
                <select onchange="filterByPriority(this.value)">
                    <option value="">Toutes</option>
                    <option value="URGENTE">Urgente</option>
                    <option value="NORMALE">Normale</option>
                    <option value="NON_URGENTE">Non urgente</option>
                </select>
            </div>
        </div>

        <c:choose>
            <c:when test="${empty demandes}">
                <div class="no-data">
                    <p>Aucune demande d'expertise trouvée.</p>
                </div>
            </c:when>
            <c:otherwise>
                <c:forEach var="demande" items="${demandes}">
                    <div class="demande-card" data-status="${demande.statut}" data-priority="${demande.priorite}">
                        <div class="demande-header">
                            <div>
                                <div class="patient-info">
                                    👤 ${demande.consultation.patient.nom} ${demande.consultation.patient.prenom}
                                </div>
                                <div class="consultation-info">
                                    📅 Consultation: ${demande.consultation.dateConsultation}
                                    | 👨‍⚕️ Dr. ${demande.consultation.medecin.nom}
                                </div>
                            </div>
                            <div>
                                <span class="priority priority-${demande.priorite.toString().toLowerCase().replace('_', '-')}">
                                    ${demande.priorite}
                                </span>
                                <span class="status status-${demande.statut.toString().toLowerCase().replace('_', '-')}">
                                    ${demande.statut}
                                </span>
                            </div>
                        </div>

                        <div class="question">
                            <strong>❓ Question du généraliste:</strong>
                            <p>${demande.question}</p>
                        </div>

                        <c:if test="${not empty demande.consultation.notes}">
                            <div class="question">
                                <strong>📝 Notes de consultation:</strong>
                                <p>${demande.consultation.notes}</p>
                            </div>
                        </c:if>

                        <c:choose>
                            <c:when test="${demande.statut == 'EN_ATTENTE'}">
                                <form method="post" action="${pageContext.request.contextPath}/expertise/respond" class="response-form">
                                    <input type="hidden" name="demandeId" value="${demande.id}">
                                    <label for="reponse_${demande.id}"><strong>✍️ Votre avis médical:</strong></label>
                                    <textarea id="reponse_${demande.id}" name="reponse" 
                                              placeholder="Saisir votre avis médical, recommandations et conseils pour la prise en charge..." required></textarea>
                                    <button type="submit" class="btn">Envoyer la Réponse</button>
                                </form>
                            </c:when>
                            <c:otherwise>
                                <div class="question">
                                    <strong>✅ Votre réponse:</strong>
                                    <p>${demande.reponse}</p>
                                    <small style="color: #666;">Répondu le: ${demande.dateReponse}</small>
                                </div>
                            </c:otherwise>
                        </c:choose>
                    </div>
                </c:forEach>
            </c:otherwise>
        </c:choose>

        <div style="margin-top: 30px;">
            <a href="${pageContext.request.contextPath}/dashboard/specialiste" class="btn" style="background-color: #6c757d;">Retour au Dashboard</a>
        </div>
    </div>

    <script>
        function filterByStatus(status) {
            const cards = document.querySelectorAll('.demande-card');
            cards.forEach(card => {
                if (status === '' || card.dataset.status === status) {
                    card.style.display = 'block';
                } else {
                    card.style.display = 'none';
                }
            });
        }

        function filterByPriority(priority) {
            const cards = document.querySelectorAll('.demande-card');
            cards.forEach(card => {
                if (priority === '' || card.dataset.priority === priority) {
                    card.style.display = 'block';
                } else {
                    card.style.display = 'none';
                }
            });
        }
    </script>
</body>
</html>