<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html lang="fr">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Enregistrement Patient - Télé-Expertise Médicale</title>
    <style>
        body { font-family: Arial, sans-serif; background-color: #f4f4f4; margin: 0; padding: 20px; }
        .container { max-width: 800px; margin: 0 auto; background: white; padding: 30px; border-radius: 8px; box-shadow: 0 2px 10px rgba(0,0,0,0.1); }
        h2 { color: #333; margin-bottom: 30px; }
        .form-section { margin-bottom: 30px; padding: 20px; background: #f8f9fa; border-radius: 8px; }
        .form-section h3 { color: #17a2b8; margin-top: 0; }
        .form-row { display: flex; gap: 15px; margin-bottom: 15px; }
        .form-group { flex: 1; }
        label { display: block; margin-bottom: 5px; color: #555; font-weight: bold; }
        input, textarea { width: 100%; padding: 10px; border: 1px solid #ddd; border-radius: 4px; box-sizing: border-box; }
        .btn { background-color: #17a2b8; color: white; padding: 12px 24px; border: none; border-radius: 4px; cursor: pointer; }
        .btn:hover { background-color: #138496; }
        .btn-secondary { background-color: #6c757d; margin-left: 10px; }
        .error { color: #dc3545; background-color: #f8d7da; padding: 10px; border-radius: 4px; margin-bottom: 20px; }
        .success { color: #155724; background-color: #d4edda; padding: 10px; border-radius: 4px; margin-bottom: 20px; }
    </style>
</head>
<body>
    <div class="container">
        <h2>📋 Enregistrement Patient</h2>
        
        <c:if test="${not empty error}">
            <div class="error">${error}</div>
        </c:if>
        
        <c:if test="${not empty success}">
            <div class="success">${success}</div>
        </c:if>
        
        <form method="post" action="${pageContext.request.contextPath}/patient/register">
            <!-- Données administratives -->
            <div class="form-section">
                <h3>👤 Données Administratives</h3>
                <div class="form-row">
                    <div class="form-group">
                        <label for="nom">Nom *</label>
                        <input type="text" id="nom" name="nom" required>
                    </div>
                    <div class="form-group">
                        <label for="prenom">Prénom *</label>
                        <input type="text" id="prenom" name="prenom" required>
                    </div>
                </div>
                
                <div class="form-row">
                    <div class="form-group">
                        <label for="email">Email</label>
                        <input type="email" id="email" name="email">
                    </div>
                    <div class="form-group">
                        <label for="telephone">Téléphone *</label>
                        <input type="tel" id="telephone" name="telephone" required>
                    </div>
                </div>
                
                <div class="form-row">
                    <div class="form-group">
                        <label for="dateNaissance">Date de Naissance *</label>
                        <input type="date" id="dateNaissance" name="dateNaissance" required>
                    </div>
                    <div class="form-group">
                        <label for="numeroSecuriteSociale">N° Sécurité Sociale *</label>
                        <input type="text" id="numeroSecuriteSociale" name="numeroSecuriteSociale" required>
                    </div>
                </div>
                
                <div class="form-group">
                    <label for="adresse">Adresse</label>
                    <textarea id="adresse" name="adresse" rows="2"></textarea>
                </div>
            </div>
            
            <!-- Signes vitaux -->
            <div class="form-section">
                <h3>🩺 Signes Vitaux</h3>
                <div class="form-row">
                    <div class="form-group">
                        <label for="tension">Tension Artérielle *</label>
                        <input type="text" id="tension" name="tension" placeholder="12/8" required>
                    </div>
                    <div class="form-group">
                        <label for="temperature">Température (°C) *</label>
                        <input type="number" id="temperature" name="temperature" step="0.1" min="35" max="42" required>
                    </div>
                </div>
                
                <div class="form-row">
                    <div class="form-group">
                        <label for="frequenceCardiaque">Fréquence Cardiaque (bpm) *</label>
                        <input type="number" id="frequenceCardiaque" name="frequenceCardiaque" min="40" max="200" required>
                    </div>
                    <div class="form-group">
                        <label for="frequenceRespiratoire">Fréquence Respiratoire (/min) *</label>
                        <input type="number" id="frequenceRespiratoire" name="frequenceRespiratoire" min="10" max="40" required>
                    </div>
                </div>
                
                <div class="form-row">
                    <div class="form-group">
                        <label for="poids">Poids (kg) *</label>
                        <input type="number" id="poids" name="poids" step="0.1" min="1" max="300" required>
                    </div>
                    <div class="form-group">
                        <label for="taille">Taille (m) *</label>
                        <input type="number" id="taille" name="taille" step="0.01" min="0.5" max="2.5" required>
                    </div>
                </div>
            </div>
            
            <div>
                <button type="submit" class="btn">Enregistrer Patient</button>
                <a href="${pageContext.request.contextPath}/dashboard/infirmier" class="btn btn-secondary">Retour</a>
            </div>
        </form>
    </div>
</body>
</html>