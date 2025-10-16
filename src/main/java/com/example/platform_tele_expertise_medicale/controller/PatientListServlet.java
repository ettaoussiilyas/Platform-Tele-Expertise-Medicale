package com.example.platform_tele_expertise_medicale.controller;

import com.example.platform_tele_expertise_medicale.dao.PatientDAO;
import jakarta.inject.Inject;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/patient/list")
public class PatientListServlet extends HttpServlet {
    
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
        
        try {
            var patients = patientDAO.findPatientsOfToday()
                .stream()
                .sorted((p1, p2) -> p1.getCreatedAt().compareTo(p2.getCreatedAt()))
                .toList();
            
            request.setAttribute("patients", patients);
            request.getRequestDispatcher("/WEB-INF/views/patient-list.jsp").forward(request, response);
            
        } catch (Exception e) {
            request.setAttribute("error", "Erreur lors du chargement: " + e.getMessage());
            request.getRequestDispatcher("/WEB-INF/views/patient-list.jsp").forward(request, response);
        }
    }
}