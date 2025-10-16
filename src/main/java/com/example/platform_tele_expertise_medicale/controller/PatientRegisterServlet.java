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
import java.time.LocalDate;

@WebServlet("/patient/register")
public class PatientRegisterServlet extends HttpServlet {
    
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
        request.getRequestDispatcher("/WEB-INF/views/patient-register.jsp").forward(request, response);
    }
    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        try {
            Patient patient = new Patient();
            patient.setNom(request.getParameter("nom"));
            patient.setPrenom(request.getParameter("prenom"));
            patient.setEmail(request.getParameter("email"));
            patient.setTelephone(request.getParameter("telephone"));
            patient.setDateNaissance(LocalDate.parse(request.getParameter("dateNaissance")));
            patient.setNumeroSecuriteSociale(request.getParameter("numeroSecuriteSociale"));
            patient.setAdresse(request.getParameter("adresse"));
            
            patient.setTension(request.getParameter("tension"));
            patient.setTemperature(java.math.BigDecimal.valueOf(Double.parseDouble(request.getParameter("temperature"))));
            patient.setFrequenceCardiaque(Integer.parseInt(request.getParameter("frequenceCardiaque")));
            patient.setFrequenceRespiratoire(Integer.parseInt(request.getParameter("frequenceRespiratoire")));
            patient.setPoids(java.math.BigDecimal.valueOf(Double.parseDouble(request.getParameter("poids"))));
            patient.setTaille(java.math.BigDecimal.valueOf(Double.parseDouble(request.getParameter("taille"))));
            
            patientDAO.save(patient);
            
            request.setAttribute("success", "Patient enregistré avec succès");
            request.getRequestDispatcher("/WEB-INF/views/patient-register.jsp").forward(request, response);
            
        } catch (Exception e) {
            request.setAttribute("error", "Erreur lors de l'enregistrement: " + e.getMessage());
            request.getRequestDispatcher("/WEB-INF/views/patient-register.jsp").forward(request, response);
        }
    }
}