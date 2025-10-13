package com.example.platform_tele_expertise_medicale.service;

import com.example.platform_tele_expertise_medicale.dao.PatientDAO;
import com.example.platform_tele_expertise_medicale.model.Patient;
import jakarta.ejb.Stateless;
import jakarta.inject.Inject;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Stateless
public class PatientService {
    
    @Inject
    private PatientDAO patientDAO;
    
    public Patient registerPatient(String nom, String prenom, String email, String telephone,
                                 LocalDate dateNaissance, String numeroSS, String adresse,
                                 String tension, BigDecimal temperature, Integer frequenceCardiaque,
                                 Integer frequenceRespiratoire, BigDecimal poids, BigDecimal taille) {
        
        Patient patient = new Patient();
        patient.setNom(nom);
        patient.setPrenom(prenom);
        patient.setEmail(email);
        patient.setTelephone(telephone);
        patient.setDateNaissance(dateNaissance);
        patient.setNumeroSecuriteSociale(numeroSS);
        patient.setAdresse(adresse);
        
        // Vital signs
        patient.setTension(tension);
        patient.setTemperature(temperature);
        patient.setFrequenceCardiaque(frequenceCardiaque);
        patient.setFrequenceRespiratoire(frequenceRespiratoire);
        patient.setPoids(poids);
        patient.setTaille(taille);
        
        patientDAO.save(patient);
        return patient;
    }
    
    public Patient findPatientByNumeroSS(String numeroSS) {
        return patientDAO.findByNumeroSecuriteSociale(numeroSS);
    }
    
    public List<Patient> searchPatientsByName(String nom, String prenom) {
        return patientDAO.searchByName(nom, prenom);
    }
    
    public List<Patient> getTodayPatients() {
        return patientDAO.findPatientsOfDay(LocalDate.now())
                .stream()
                .sorted((p1, p2) -> p1.getCreatedAt().compareTo(p2.getCreatedAt()))
                .collect(Collectors.toList());
    }
    
    public Patient updateVitalSigns(Long patientId, String tension, BigDecimal temperature,
                                  Integer frequenceCardiaque, Integer frequenceRespiratoire,
                                  BigDecimal poids, BigDecimal taille) {
        Patient patient = patientDAO.findById(patientId);
        if (patient != null) {
            patient.setTension(tension);
            patient.setTemperature(temperature);
            patient.setFrequenceCardiaque(frequenceCardiaque);
            patient.setFrequenceRespiratoire(frequenceRespiratoire);
            patient.setPoids(poids);
            patient.setTaille(taille);
            patientDAO.update(patient);
        }
        return patient;
    }
}