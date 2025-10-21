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
            String nom = request.getParameter("nom");
            String prenom = request.getParameter("prenom");
            String numeroSS = request.getParameter("numeroSecuriteSociale");
            
            if (nom == null || prenom == null || numeroSS == null || 
                nom.trim().isEmpty() || prenom.trim().isEmpty() || numeroSS.trim().isEmpty()) {
                request.setAttribute("error", "Les champs nom, prénom et numéro de sécurité sociale sont obligatoires");
                request.getRequestDispatcher("/WEB-INF/views/patient-register.jsp").forward(request, response);
                return;
            }
            
            Patient patient = new Patient();
            patient.setNom(nom.trim());
            patient.setPrenom(prenom.trim());
            patient.setEmail(request.getParameter("email"));
            patient.setTelephone(request.getParameter("telephone"));
            
            String dateNaissanceStr = request.getParameter("dateNaissance");
            if (dateNaissanceStr != null && !dateNaissanceStr.trim().isEmpty()) {
                patient.setDateNaissance(LocalDate.parse(dateNaissanceStr));
            }
            
            patient.setNumeroSecuriteSociale(numeroSS.trim());
            patient.setAdresse(request.getParameter("adresse"));
            
            String tension = request.getParameter("tension");
            if (tension != null && tension.length() > 20) {
                tension = tension.substring(0, 20);
            }
            patient.setTension(tension);
            
            String tempStr = request.getParameter("temperature");
            if (tempStr != null && !tempStr.trim().isEmpty()) {
                patient.setTemperature(java.math.BigDecimal.valueOf(Double.parseDouble(tempStr)));
            }
            
            String fcStr = request.getParameter("frequenceCardiaque");
            if (fcStr != null && !fcStr.trim().isEmpty()) {
                patient.setFrequenceCardiaque(Integer.parseInt(fcStr));
            }
            
            String frStr = request.getParameter("frequenceRespiratoire");
            if (frStr != null && !frStr.trim().isEmpty()) {
                patient.setFrequenceRespiratoire(Integer.parseInt(frStr));
            }
            
            String poidsStr = request.getParameter("poids");
            if (poidsStr != null && !poidsStr.trim().isEmpty()) {
                patient.setPoids(java.math.BigDecimal.valueOf(Double.parseDouble(poidsStr)));
            }
            
            String tailleStr = request.getParameter("taille");
            if (tailleStr != null && !tailleStr.trim().isEmpty()) {
                patient.setTaille(java.math.BigDecimal.valueOf(Double.parseDouble(tailleStr)));
            }
            
            patientDAO.save(patient);
            
            request.setAttribute("success", "Patient enregistré avec succès");
            request.getRequestDispatcher("/WEB-INF/views/patient-register.jsp").forward(request, response);
            
        } catch (Exception e) {
            request.setAttribute("error", "Erreur lors de l'enregistrement: " + e.getMessage());
            request.getRequestDispatcher("/WEB-INF/views/patient-register.jsp").forward(request, response);
        }
    }
}