<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="fr">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Télé-Expertise Médicale</title>
    <style>
        body { font-family: Arial, sans-serif; background-color: #f4f4f4; margin: 0; padding: 20px; text-align: center; }
        .container { max-width: 600px; margin: 50px auto; background: white; padding: 40px; border-radius: 8px; box-shadow: 0 2px 10px rgba(0,0,0,0.1); }
        h1 { color: #333; margin-bottom: 30px; }
        .description { color: #666; margin-bottom: 30px; line-height: 1.6; }
        .buttons { display: flex; gap: 20px; justify-content: center; }
        .btn { padding: 12px 24px; text-decoration: none; border-radius: 4px; font-weight: bold; }
        .btn-primary { background-color: #007bff; color: white; }
        .btn-secondary { background-color: #28a745; color: white; }
        .btn:hover { opacity: 0.9; }
    </style>
</head>
<body>
    <div class="container">
        <h1>Système de Télé-Expertise Médicale</h1>
        <div class="description">
            <p>Plateforme de coordination entre médecins généralistes, spécialistes et infirmiers pour optimiser le parcours patient.</p>
        </div>
        <div class="buttons">
            <a href="login" class="btn btn-primary">Se connecter</a>
            <a href="register" class="btn btn-secondary">S'inscrire</a>
        </div>
    </div>
</body>
</html>