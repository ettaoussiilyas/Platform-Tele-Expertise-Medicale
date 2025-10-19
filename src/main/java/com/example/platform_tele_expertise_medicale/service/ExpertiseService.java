package com.example.platform_tele_expertise_medicale.service;

import com.example.platform_tele_expertise_medicale.dao.*;
import com.example.platform_tele_expertise_medicale.model.*;
import com.example.platform_tele_expertise_medicale.model.enums.Priorite;
import com.example.platform_tele_expertise_medicale.model.enums.StatutDemande;
import jakarta.ejb.Stateless;
import jakarta.inject.Inject;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Stateless
public class ExpertiseService {
    
    @Inject
    private DemandeExpertiseDAO demandeExpertiseDAO;
    
    @Inject
    private UtilisateurDAO utilisateurDAO;
    
    @Inject
    private SpecialiteDAO specialiteDAO;
    
    @Inject
    private ConsultationDAO consultationDAO;
    
    @Inject
    private CreneauDisponibleDAO creneauDAO;
    
    public DemandeExpertise createDemandeExpertise(Long consultationId, Long specialisteId, String question, Priorite priorite) {
        
        Consultation consultation = consultationDAO.findById(consultationId);
        Utilisateur specialiste = utilisateurDAO.findById(specialisteId);
        
        if (consultation == null || specialiste == null) {
            throw new RuntimeException("Consultation ou spécialiste introuvable");
        }
        
        DemandeExpertise demande = new DemandeExpertise();
        demande.setConsultation(consultation);
        demande.setGeneraliste(consultation.getMedecin());
        demande.setSpecialiste(specialiste);
        demande.setSpecialite(specialiste.getSpecialite());
        demande.setQuestion(question);
        demande.setPriorite(priorite);
        demande.setStatut(StatutDemande.EN_ATTENTE);
        
        demandeExpertiseDAO.save(demande);
        return demande;
    }
    
    public List<DemandeExpertise> getDemandesForSpecialiste(Long specialisteId) {
        return demandeExpertiseDAO.findBySpecialisteId(specialisteId);
    }
    
    public DemandeExpertise respondToExpertise(Long demandeId, String reponse) {
        DemandeExpertise demande = demandeExpertiseDAO.findById(demandeId);
        if (demande != null) {
            demande.setReponse(reponse);
            demande.setStatut(StatutDemande.TERMINEE);
            demande.setDateReponse(LocalDateTime.now());
            demandeExpertiseDAO.update(demande);
        }
        return demande;
    }
    
    public List<CreneauDisponible> getAvailableSlots(Long specialisteId) {
        return creneauDAO.findAvailableByMedecinId(specialisteId);
    }
    
    public List<DemandeExpertise> getDemandesByGeneraliste(Long generalisteId) {
        return demandeExpertiseDAO.findByGeneralisteId(generalisteId);
    }
}