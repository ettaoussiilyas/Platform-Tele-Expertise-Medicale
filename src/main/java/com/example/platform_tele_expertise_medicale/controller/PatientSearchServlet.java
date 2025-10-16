package com.example.platform_tele_expertise_medicale.controller;

import com.example.platform_tele_expertise_medicale.dao.PatientDAO;
import com.example.platform_tele_expertise_medicale.model.Patient;
import jakarta.inject.Inject;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/patient/search")
public class PatientSearchServlet extends HttpServlet {
    
    @Inject
    private PatientDAO patientDAO;
    
    @Override
    public void init() throws ServletException {
        if (patientDAO == null) {
            patientDAO = new PatientDAO();
        }
    }
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        request.getRequestDispatcher("/WEB-INF/views/patient-search.jsp").forward(request, response);
    }
    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        String searchTerm = request.getParameter("searchTerm");
        try {
            Patient patient = patientDAO.findByNumeroSecuriteSociale(searchTerm);
            if (patient == null) {
                patient = patientDAO.findByEmail(searchTerm);
            }
            
            request.setAttribute("patient", patient);
            request.getRequestDispatcher("/WEB-INF/views/patient-search.jsp").forward(request, response);
            
        } catch (Exception e) {
            request.setAttribute("error", "Erreur lors de la recherche: " + e.getMessage());
            request.getRequestDispatcher("/WEB-INF/views/patient-search.jsp").forward(request, response);
        }
    }
}