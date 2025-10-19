package com.example.platform_tele_expertise_medicale.controller;

import com.example.platform_tele_expertise_medicale.dao.SpecialiteDAO;
import com.example.platform_tele_expertise_medicale.dao.UtilisateurDAO;
import com.example.platform_tele_expertise_medicale.model.Specialite;
import com.example.platform_tele_expertise_medicale.model.Utilisateur;
import jakarta.inject.Inject;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;

@WebServlet("/specialist/profile")
public class SpecialistProfileServlet extends HttpServlet {

    @Inject
    private UtilisateurDAO utilisateurDAO;
    
    @Inject
    private SpecialiteDAO specialiteDAO;

    @Override
    public void init() throws ServletException {
        if (utilisateurDAO == null) {
            utilisateurDAO = new UtilisateurDAO();
        }
        if (specialiteDAO == null) {
            specialiteDAO = new SpecialiteDAO();
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("user") == null) {
            response.sendRedirect(request.getContextPath() + "/login");
            return;
        }

        String userRole = session.getAttribute("userRole").toString();
        if (!"SPECIALISTE".equals(userRole)) {
            response.sendRedirect(request.getContextPath() + "/dashboard");
            return;
        }

        List<Specialite> specialites = specialiteDAO.findAll();
        request.setAttribute("specialites", specialites);
        
        request.getRequestDispatcher("/WEB-INF/views/specialist/profile.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("user") == null) {
            response.sendRedirect(request.getContextPath() + "/login");
            return;
        }

        try {
            Utilisateur user = (Utilisateur) session.getAttribute("user");
            
            Long specialiteId = Long.parseLong(request.getParameter("specialiteId"));
            BigDecimal tarif = new BigDecimal(request.getParameter("tarif"));
            
            Specialite specialite = specialiteDAO.findById(specialiteId);
            if (specialite == null) {
                request.setAttribute("error", "Spécialité non trouvée");
                doGet(request, response);
                return;
            }
            
            user.setSpecialite(specialite);
            user.setTarif(tarif);
            
            utilisateurDAO.update(user);
            
            // Update session
            session.setAttribute("user", user);
            
            response.sendRedirect(request.getContextPath() + "/dashboard/specialiste?success=profile_updated");
        } catch (Exception e) {
            request.setAttribute("error", "Erreur lors de la mise à jour: " + e.getMessage());
            doGet(request, response);
        }
    }
}