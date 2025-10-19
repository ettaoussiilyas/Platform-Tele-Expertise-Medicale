package com.example.platform_tele_expertise_medicale.controller;

import com.example.platform_tele_expertise_medicale.dao.ConsultationDAO;
import com.example.platform_tele_expertise_medicale.dao.SpecialiteDAO;
import com.example.platform_tele_expertise_medicale.dao.UtilisateurDAO;
import com.example.platform_tele_expertise_medicale.model.Consultation;
import com.example.platform_tele_expertise_medicale.model.Specialite;
import com.example.platform_tele_expertise_medicale.model.Utilisateur;
import com.example.platform_tele_expertise_medicale.model.enums.Priorite;
import com.example.platform_tele_expertise_medicale.model.enums.RoleName;
import com.example.platform_tele_expertise_medicale.service.ExpertiseService;
import jakarta.inject.Inject;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@WebServlet("/expertise/*")
public class ExpertiseServlet extends HttpServlet {

    @Inject
    private ExpertiseService expertiseService;
    
    @Inject
    private ConsultationDAO consultationDAO;
    
    @Inject
    private SpecialiteDAO specialiteDAO;
    
    @Inject
    private UtilisateurDAO utilisateurDAO;

    @Override
    public void init() throws ServletException {
        if (expertiseService == null) {
            expertiseService = new ExpertiseService();
        }
        if (consultationDAO == null) {
            consultationDAO = new ConsultationDAO();
        }
        if (specialiteDAO == null) {
            specialiteDAO = new SpecialiteDAO();
        }
        if (utilisateurDAO == null) {
            utilisateurDAO = new UtilisateurDAO();
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

        String pathInfo = request.getPathInfo();
        if (pathInfo == null) pathInfo = "/";

        switch (pathInfo) {
            case "/request":
                showRequestForm(request, response);
                break;
            case "/list":
                showExpertiseRequests(request, response, session);
                break;
            default:
                response.sendRedirect(request.getContextPath() + "/dashboard");
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("user") == null) {
            response.sendRedirect(request.getContextPath() + "/login");
            return;
        }

        String pathInfo = request.getPathInfo();
        if ("/request".equals(pathInfo)) {
            createExpertiseRequest(request, response, session);
        } else if ("/respond".equals(pathInfo)) {
            respondToExpertise(request, response, session);
        }
    }

    private void showRequestForm(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        String consultationIdParam = request.getParameter("consultationId");
        if (consultationIdParam == null) {
            response.sendRedirect(request.getContextPath() + "/consultation/list");
            return;
        }

        Long consultationId = Long.parseLong(consultationIdParam);
        Consultation consultation = consultationDAO.findById(consultationId);
        
        if (consultation == null) {
            response.sendRedirect(request.getContextPath() + "/consultation/list?error=consultation_not_found");
            return;
        }

        List<Specialite> specialites = specialiteDAO.findAll();
        
        request.setAttribute("consultation", consultation);
        request.setAttribute("specialites", specialites);
        request.setAttribute("priorites", Priorite.values());
        
        request.getRequestDispatcher("/WEB-INF/views/expertise/request.jsp").forward(request, response);
    }

    private void showExpertiseRequests(HttpServletRequest request, HttpServletResponse response, HttpSession session) 
            throws ServletException, IOException {
        
        Utilisateur user = (Utilisateur) session.getAttribute("user");
        String userRole = user.getRole().getRoleName().toString();
        
        if ("SPECIALISTE".equals(userRole)) {
            var demandes = expertiseService.getDemandesForSpecialiste(user.getId());
            request.setAttribute("demandes", demandes);
            request.getRequestDispatcher("/WEB-INF/views/expertise/specialist-list.jsp").forward(request, response);
        } else {
            var demandes = expertiseService.getDemandesByGeneraliste(user.getId());
            request.setAttribute("demandes", demandes);
            request.getRequestDispatcher("/WEB-INF/views/expertise/generaliste-list.jsp").forward(request, response);
        }
    }

    private void createExpertiseRequest(HttpServletRequest request, HttpServletResponse response, HttpSession session) 
            throws ServletException, IOException {
        try {
            Long consultationId = Long.parseLong(request.getParameter("consultationId"));
            Long specialiteId = Long.parseLong(request.getParameter("specialiteId"));
            String question = request.getParameter("question");
            Priorite priorite = Priorite.valueOf(request.getParameter("priorite"));
            
            // Get specialists by specialty and sort by tariff using Stream API
            List<Utilisateur> specialistes = utilisateurDAO.findByRole(RoleName.SPECIALISTE)
                    .stream()
                    .filter(s -> s.getSpecialite() != null && s.getSpecialite().getId().equals(specialiteId))
                    .sorted((s1, s2) -> s1.getTarif().compareTo(s2.getTarif()))
                    .collect(Collectors.toList());
            
            if (specialistes.isEmpty()) {
                request.setAttribute("error", "Aucun spécialiste disponible pour cette spécialité");
                showRequestForm(request, response);
                return;
            }
            
            // For now, assign to the first (cheapest) specialist
            Utilisateur specialiste = specialistes.get(0);
            
            expertiseService.createDemandeExpertise(consultationId, specialiste.getId(), question, priorite);
            
            response.sendRedirect(request.getContextPath() + "/consultation/list?success=expertise_requested");
        } catch (Exception e) {
            request.setAttribute("error", "Erreur lors de la demande: " + e.getMessage());
            showRequestForm(request, response);
        }
    }

    private void respondToExpertise(HttpServletRequest request, HttpServletResponse response, HttpSession session) 
            throws ServletException, IOException {
        try {
            Long demandeId = Long.parseLong(request.getParameter("demandeId"));
            String reponse = request.getParameter("reponse");
            
            expertiseService.respondToExpertise(demandeId, reponse);
            
            response.sendRedirect(request.getContextPath() + "/expertise/list?success=responded");
        } catch (Exception e) {
            response.sendRedirect(request.getContextPath() + "/expertise/list?error=" + e.getMessage());
        }
    }
}