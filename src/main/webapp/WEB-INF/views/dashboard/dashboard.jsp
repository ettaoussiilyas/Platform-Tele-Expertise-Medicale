<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html lang="fr">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Dashboard - Télé-Expertise Médicale</title>
    <style>
        body { font-family: Arial, sans-serif; background-color: #f4f4f4; margin: 0; padding: 20px; }
        .container { max-width: 800px; margin: 0 auto; background: white; padding: 30px; border-radius: 8px; box-shadow: 0 2px 10px rgba(0,0,0,0.1); }
        .header { display: flex; justify-content: space-between; align-items: center; margin-bottom: 30px; border-bottom: 1px solid #eee; padding-bottom: 20px; }
        h1 { color: #333; margin: 0; }
        .user-info { color: #666; }
        .logout-btn { background-color: #dc3545; color: white; padding: 8px 16px; text-decoration: none; border-radius: 4px; }
        .logout-btn:hover { background-color: #c82333; }
        .welcome { background-color: #d4edda; color: #155724; padding: 20px; border-radius: 4px; margin-bottom: 20px; }
    </style>
</head>
<body>
    <div class="container">
        <div class="header">
            <h1>Dashboard</h1>
            <div class="user-info">
                Bienvenue, ${sessionScope.user.prenom} ${sessionScope.user.nom}
                <a href="${pageContext.request.contextPath}/logout" class="logout-btn">Déconnexion</a>
            </div>
        </div>
        
        <div class="welcome">
            <h3>Connexion réussie!</h3>
            <p>Vous êtes connecté en tant que: <strong>${sessionScope.userRole}</strong></p>
            <p>Email: ${sessionScope.user.email}</p>
            <c:if test="${not empty sessionScope.user.telephone}">
                <p>Téléphone: ${sessionScope.user.telephone}</p>
            </c:if>
        </div>
        
        <p>Votre application fonctionne correctement! Les servlets et JSP sont opérationnels.</p>
    </div>
</body>
</html>