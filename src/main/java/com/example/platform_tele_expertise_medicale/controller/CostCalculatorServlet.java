package com.example.platform_tele_expertise_medicale.controller;

import com.example.platform_tele_expertise_medicale.dao.ActeTechniqueDAO;
import com.example.platform_tele_expertise_medicale.dao.ConsultationDAO;
import com.example.platform_tele_expertise_medicale.model.ActeTechnique;
import com.example.platform_tele_expertise_medicale.model.Consultation;
import com.example.platform_tele_expertise_medicale.model.Utilisateur;
import com.example.platform_tele_expertise_medicale.service.ConsultationService;
import jakarta.inject.Inject;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

@WebServlet("/cost/calculator")
public class CostCalculatorServlet extends HttpServlet {

    @Inject
    private ConsultationService consultationService;
    
    @Inject
    private ConsultationDAO consultationDAO;
    
    @Inject
    private ActeTechniqueDAO acteTechniqueDAO;

    @Override
    public void init() throws ServletException {
        if (consultationService == null) {
            consultationService = new ConsultationService();
        }
        if (consultationDAO == null) {
            consultationDAO = new ConsultationDAO();
        }
        if (acteTechniqueDAO == null) {
            acteTechniqueDAO = new ActeTechniqueDAO();
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

        Utilisateur user = (Utilisateur) session.getAttribute("user");
        List<Consultation> consultations = consultationService.getConsultationsByMedecin(user.getId());
        List<ActeTechnique> actesTechniques = acteTechniqueDAO.findAll();
        
        request.setAttribute("consultations", consultations);
        request.setAttribute("actesTechniques", actesTechniques);
        
        request.getRequestDispatcher("/WEB-INF/views/cost/calculator.jsp").forward(request, response);
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
            Long consultationId = Long.parseLong(request.getParameter("consultationId"));
            String[] acteTechniqueIds = request.getParameterValues("acteTechniqueIds");
            
            // Calculate total cost using Lambda expressions
            BigDecimal totalCost = consultationService.calculateTotalCost(consultationId);
            
            // Add technical acts cost using Stream API
            if (acteTechniqueIds != null) {
                BigDecimal actesCost = Arrays.stream(acteTechniqueIds)
                        .map(Long::parseLong)
                        .map(id -> acteTechniqueDAO.findById(id))
                        .filter(acte -> acte != null)
                        .map(ActeTechnique::getTarif)
                        .reduce(BigDecimal.ZERO, BigDecimal::add);
                
                totalCost = totalCost.add(actesCost);
            }
            
            Consultation consultation = consultationDAO.findByIdWithExpertises(consultationId);
            
            request.setAttribute("consultation", consultation);
            request.setAttribute("totalCost", totalCost);
            request.setAttribute("calculationDone", true);
            
            doGet(request, response);
        } catch (Exception e) {
            request.setAttribute("error", "Erreur lors du calcul: " + e.getMessage());
            doGet(request, response);
        }
    }
}