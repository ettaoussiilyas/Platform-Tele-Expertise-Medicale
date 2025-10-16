<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html lang="fr">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Recherche Patient - Télé-Expertise Médicale</title>
    <style>
        body { font-family: Arial, sans-serif; background-color: #f4f4f4; margin: 0; padding: 20px; }
        .container { max-width: 800px; margin: 0 auto; background: white; padding: 30px; border-radius: 8px; box-shadow: 0 2px 10px rgba(0,0,0,0.1); }
        h2 { color: #333; margin-bottom: 30px; }
        .search-form { background: #f8f9fa; padding: 20px; border-radius: 8px; margin-bottom: 30px; }
        .form-group { margin-bottom: 15px; }
        label { display: block; margin-bottom: 5px; color: #555; font-weight: bold; }
        input { width: 100%; padding: 10px; border: 1px solid #ddd; border-radius: 4px; box-sizing: border-box; }
        .btn { background-color: #17a2b8; color: white; padding: 12px 24px; border: none; border-radius: 4px; cursor: pointer; }
        .btn:hover { background-color: #138496; }
        .btn-secondary { background-color: #6c757d; margin-left: 10px; }
        .patient-info { background: #e7f3ff; padding: 20px; border-radius: 8px; margin-top: 20px; }
        .patient-info h3 { color: #0056b3; margin-top: 0; }
        .info-row { display: flex; gap: 30px; margin-bottom: 10px; }
        .info-item { flex: 1; }
        .info-label { font-weight: bold; color: #555; }
        .error { color: #dc3545; background-color: #f8d7da; padding: 10px; border-radius: 4px; margin-bottom: 20px; }
        .no-result { color: #856404; background-color: #fff3cd; padding: 15px; border-radius: 4px; text-align: center; }
    </style>
</head>
<body>
    <div class="container">
        <h2>🔍 Recherche Patient</h2>
        
        <div class="search-form">
            <form method="post" action="${pageContext.request.contextPath}/patient/search">
                <div class="form-group">
                    <label for="searchTerm">Rechercher par N° Sécurité Sociale ou Email</label>
                    <input type="text" id="searchTerm" name="searchTerm" 
                           placeholder="Entrez le numéro de sécurité sociale ou l'email" required>
                </div>
                
                <button type="submit" class="btn">Rechercher</button>
                <a href="${pageContext.request.contextPath}/dashboard/infirmier" class="btn btn-secondary">Retour</a>
            </form>
        </div>
        
        <c:if test="${not empty error}">
            <div class="error">${error}</div>
        </c:if>
        
        <c:choose>
            <c:when test="${not empty patient}">
                <div class="patient-info">
                    <h3>👤 Informations Patient</h3>
                    
                    <div class="info-row">
                        <div class="info-item">
                            <div class="info-label">Nom Complet:</div>
                            <div>${patient.nom} ${patient.prenom}</div>
                        </div>
                        <div class="info-item">
                            <div class="info-label">N° Sécurité Sociale:</div>
                            <div>${patient.numeroSecuriteSociale}</div>
                        </div>
                    </div>
                    
                    <div class="info-row">
                        <div class="info-item">
                            <div class="info-label">Email:</div>
                            <div>${patient.email}</div>
                        </div>
                        <div class="info-item">
                            <div class="info-label">Téléphone:</div>
                            <div>${patient.telephone}</div>
                        </div>
                    </div>
                    
                    <div class="info-row">
                        <div class="info-item">
                            <div class="info-label">Date de Naissance:</div>
                            <div>${patient.dateNaissance}</div>
                        </div>
                        <div class="info-item">
                            <div class="info-label">Adresse:</div>
                            <div>${patient.adresse}</div>
                        </div>
                    </div>
                    
                    <h4>🩺 Derniers Signes Vitaux</h4>
                    <div class="info-row">
                        <div class="info-item">
                            <div class="info-label">Tension:</div>
                            <div>${patient.tension}</div>
                        </div>
                        <div class="info-item">
                            <div class="info-label">Température:</div>
                            <div>${patient.temperature}°C</div>
                        </div>
                    </div>
                    
                    <div class="info-row">
                        <div class="info-item">
                            <div class="info-label">Fréquence Cardiaque:</div>
                            <div>${patient.frequenceCardiaque} bpm</div>
                        </div>
                        <div class="info-item">
                            <div class="info-label">Fréquence Respiratoire:</div>
                            <div>${patient.frequenceRespiratoire} /min</div>
                        </div>
                    </div>
                    
                    <div class="info-row">
                        <div class="info-item">
                            <div class="info-label">Poids:</div>
                            <div>${patient.poids} kg</div>
                        </div>
                        <div class="info-item">
                            <div class="info-label">Taille:</div>
                            <div>${patient.taille} m</div>
                        </div>
                    </div>
                    
                    <div style="margin-top: 20px;">
                        <a href="${pageContext.request.contextPath}/patient/register" class="btn">Nouveaux Signes Vitaux</a>
                    </div>
                </div>
            </c:when>
            <c:when test="${param.searchTerm != null && empty patient}">
                <div class="no-result">
                    Aucun patient trouvé avec ces critères de recherche.
                </div>
            </c:when>
        </c:choose>
    </div>
</body>
</html>