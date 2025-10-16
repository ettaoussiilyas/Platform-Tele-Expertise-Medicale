package com.example.platform_tele_expertise_medicale.controller;

import com.example.platform_tele_expertise_medicale.dao.UtilisateurDAO;
import com.example.platform_tele_expertise_medicale.model.Utilisateur;
import jakarta.inject.Inject;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet("/login")
public class LoginServlet extends HttpServlet {
    
    @Inject
    private UtilisateurDAO utilisateurDAO;
    
    @Override
    public void init() throws ServletException {
        if (utilisateurDAO == null) {
            utilisateurDAO = new UtilisateurDAO();
        }
    }
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        request.getRequestDispatcher("/WEB-INF/views/login.jsp").forward(request, response);
    }
    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        String email = request.getParameter("email");
        String password = request.getParameter("password");
        
        if (email == null || password == null || email.trim().isEmpty() || password.trim().isEmpty()) {
            request.setAttribute("error", "Email et mot de passe requis");
            request.getRequestDispatcher("/WEB-INF/views/login.jsp").forward(request, response);
            return;
        }
        
        try {
            Utilisateur user = utilisateurDAO.findByEmail(email);
            
            if (user != null && org.mindrot.jbcrypt.BCrypt.checkpw(password, user.getMotDePasse())) {
                HttpSession session = request.getSession();
                session.setAttribute("user", user);
                session.setAttribute("userRole", user.getRole().getRoleName());

                String redirectUrl = getRedirectUrlByRole(user.getRole().getRoleName().toString());
                response.sendRedirect(request.getContextPath() + redirectUrl);
            } else {
                request.setAttribute("error", "Email ou mot de passe incorrect");
                request.getRequestDispatcher("/WEB-INF/views/login.jsp").forward(request, response);
            }
        } catch (Exception e) {
            request.setAttribute("error", "Erreur de connexion: " + e.getMessage());
            request.getRequestDispatcher("/WEB-INF/views/login.jsp").forward(request, response);
        }
    }
    
    private String getRedirectUrlByRole(String role) {
        switch (role) {
            case "INFIRMIER":
                return "/dashboard/infirmier";
            case "GENERALISTE":
                return "/dashboard/generaliste";
            case "SPECIALISTE":
                return "/dashboard/specialiste";
            default:
                return "/dashboard";
        }
    }
}