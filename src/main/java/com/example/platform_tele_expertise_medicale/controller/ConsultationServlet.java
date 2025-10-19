package com.example.platform_tele_expertise_medicale.controller;

import com.example.platform_tele_expertise_medicale.dao.PatientDAO;
import com.example.platform_tele_expertise_medicale.model.Patient;
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
import java.util.List;

@WebServlet("/consultation/*")
public class ConsultationServlet extends HttpServlet {

    @Inject
    private ConsultationService consultationService;
    
    @Inject
    private PatientDAO patientDAO;

    @Override
    public void init() throws ServletException {
        if (consultationService == null) {
            consultationService = new ConsultationService();
        }
        if (patientDAO == null) {
            patientDAO = new PatientDAO();
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
            case "/create":
                showCreateForm(request, response);
                break;
            case "/list":
                showConsultations(request, response, session);
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
        if ("/create".equals(pathInfo)) {
            createConsultation(request, response, session);
        }
    }

    private void showCreateForm(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        List<Patient> patients = patientDAO.findAll();
        request.setAttribute("patients", patients);
        request.getRequestDispatcher("/WEB-INF/views/consultation/create.jsp").forward(request, response);
    }

    private void showConsultations(HttpServletRequest request, HttpServletResponse response, HttpSession session) 
            throws ServletException, IOException {
        Utilisateur user = (Utilisateur) session.getAttribute("user");
        var consultations = consultationService.getConsultationsByMedecin(user.getId());
        request.setAttribute("consultations", consultations);
        request.getRequestDispatcher("/WEB-INF/views/consultation/list.jsp").forward(request, response);
    }

    private void createConsultation(HttpServletRequest request, HttpServletResponse response, HttpSession session) 
            throws ServletException, IOException {
        try {
            Long patientId = Long.parseLong(request.getParameter("patientId"));
            String notes = request.getParameter("notes");
            
            Utilisateur medecin = (Utilisateur) session.getAttribute("user");
            consultationService.createConsultation(patientId, medecin.getId(), notes);
            
            response.sendRedirect(request.getContextPath() + "/consultation/list?success=created");
        } catch (Exception e) {
            request.setAttribute("error", "Erreur lors de la création: " + e.getMessage());
            showCreateForm(request, response);
        }
    }
}