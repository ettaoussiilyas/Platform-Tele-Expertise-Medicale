package com.example.platform_tele_expertise_medicale.service;

import com.example.platform_tele_expertise_medicale.dao.ConsultationDAO;
import com.example.platform_tele_expertise_medicale.dao.PatientDAO;
import com.example.platform_tele_expertise_medicale.dao.UtilisateurDAO;
import com.example.platform_tele_expertise_medicale.model.Consultation;
import com.example.platform_tele_expertise_medicale.model.Patient;
import com.example.platform_tele_expertise_medicale.model.Utilisateur;
import com.example.platform_tele_expertise_medicale.model.enums.StatutConsultation;
import jakarta.ejb.Stateless;
import jakarta.inject.Inject;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Stateless
public class ConsultationService {
    
    @Inject
    private ConsultationDAO consultationDAO;
    
    @Inject
    private PatientDAO patientDAO;
    
    @Inject
    private UtilisateurDAO utilisateurDAO;
    
    private static final BigDecimal CONSULTATION_COST = new BigDecimal("150.00");
    
    public Consultation createConsultation(Long patientId, Long medecinId, String notes) {
        Patient patient = patientDAO.findById(patientId);
        Utilisateur medecin = utilisateurDAO.findById(medecinId);
        
        if (patient == null || medecin == null) {
            throw new RuntimeException("Patient ou médecin introuvable");
        }
        
        Consultation consultation = new Consultation();
        consultation.setPatient(patient);
        consultation.setMedecin(medecin);
        consultation.setDateConsultation(LocalDateTime.now());
        consultation.setDureeMinutes(30);
        consultation.setTarif(CONSULTATION_COST);
        consultation.setStatut(StatutConsultation.EN_COURS);
        consultation.setNotes(notes);
        
        consultationDAO.save(consultation);
        return consultation;
    }
    
    public Consultation completeConsultation(Long consultationId, String diagnostic) {
        Consultation consultation = consultationDAO.findById(consultationId);
        if (consultation != null) {
            consultation.setDiagnostic(diagnostic);
            consultation.setStatut(StatutConsultation.TERMINEE);
            consultationDAO.update(consultation);
        }
        return consultation;
    }
    
    public Consultation markWaitingForExpertise(Long consultationId) {
        Consultation consultation = consultationDAO.findById(consultationId);
        if (consultation != null) {
            consultation.setStatut(StatutConsultation.EN_ATTENTE_AVIS_SPECIALISTE);
            consultationDAO.update(consultation);
        }
        return consultation;
    }
    
    public List<Consultation> getConsultationsByPatient(Long patientId) {
        return consultationDAO.findByPatientId(patientId);
    }
    
    public List<Consultation> getConsultationsByMedecin(Long medecinId) {
        return consultationDAO.findByMedecinId(medecinId);
    }
    
    public BigDecimal calculateTotalCost(Long consultationId) {
        Consultation consultation = consultationDAO.findById(consultationId);
        if (consultation == null) {
            return BigDecimal.ZERO;
        }
        
        BigDecimal total = consultation.getTarif();
        
        if (consultation.getDemandesExpertises() != null) {
            BigDecimal specialistCost = consultation.getDemandesExpertises()
                    .stream()
                    .map(demande -> demande.getSpecialiste().getTarif())
                    .reduce(BigDecimal.ZERO, BigDecimal::add);
            total = total.add(specialistCost);
        }
        
        return total;
    }
}