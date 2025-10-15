package com.example.platform_tele_expertise_medicale.controller;

import com.example.platform_tele_expertise_medicale.dao.UtilisateurDAO;
import com.example.platform_tele_expertise_medicale.dao.RoleDAO;
import com.example.platform_tele_expertise_medicale.model.Utilisateur;
import com.example.platform_tele_expertise_medicale.model.Role;
import com.example.platform_tele_expertise_medicale.model.enums.RoleName;
import jakarta.inject.Inject;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/register")
public class RegisterServlet extends HttpServlet {
    
    @Inject
    private UtilisateurDAO utilisateurDAO;
    
    @Inject
    private RoleDAO roleDAO;
    
    @Override
    public void init() throws ServletException {
        if (utilisateurDAO == null) {
            utilisateurDAO = new UtilisateurDAO();
        }
        if (roleDAO == null) {
            roleDAO = new RoleDAO();
        }
    }
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        request.getRequestDispatcher("/WEB-INF/views/register.jsp").forward(request, response);
    }
    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        String nom = request.getParameter("nom");
        String prenom = request.getParameter("prenom");
        String email = request.getParameter("email");
        String password = request.getParameter("password");
        String telephone = request.getParameter("telephone");
        String roleParam = request.getParameter("role");
        
        if (nom == null || prenom == null || email == null || password == null || roleParam == null ||
            nom.trim().isEmpty() || prenom.trim().isEmpty() || email.trim().isEmpty() || password.trim().isEmpty()) {
            request.setAttribute("error", "Tous les champs obligatoires doivent être remplis");
            request.getRequestDispatcher("/WEB-INF/views/register.jsp").forward(request, response);
            return;
        }
        
        try {
            if (utilisateurDAO.findByEmail(email) != null) {
                request.setAttribute("error", "Cet email est déjà utilisé");
                request.getRequestDispatcher("/WEB-INF/views/register.jsp").forward(request, response);
                return;
            }
            
            Utilisateur newUser = new Utilisateur();
            newUser.setNom(nom);
            newUser.setPrenom(prenom);
            newUser.setEmail(email);
            newUser.setMotDePasse(org.mindrot.jbcrypt.BCrypt.hashpw(password, org.mindrot.jbcrypt.BCrypt.gensalt()));
            newUser.setTelephone(telephone);
            RoleName roleName = RoleName.valueOf(roleParam);
            Role role = roleDAO.findByRoleName(roleName);
            newUser.setRole(role);
            
            utilisateurDAO.save(newUser);
            
            request.setAttribute("success", "Inscription réussie! Vous pouvez maintenant vous connecter.");
            request.getRequestDispatcher("/WEB-INF/views/login.jsp").forward(request, response);
            
        } catch (Exception e) {
            request.setAttribute("error", "Erreur lors de l'inscription: " + e.getMessage());
            request.getRequestDispatcher("/WEB-INF/views/register.jsp").forward(request, response);
        }
    }
}