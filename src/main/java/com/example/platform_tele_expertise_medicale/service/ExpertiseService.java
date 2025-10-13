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
    
    public DemandeExpertise requestExpertise(Long consultationId, Long generalisteId,
                                           Integer specialiteId, String question, Priorite priorite) {
        
        Consultation consultation = consultationDAO.findById(consultationId);
        Utilisateur generaliste = utilisateurDAO.findById(generalisteId);
        Specialite specialite = specialiteDAO.findById(specialiteId);
        
        List<Utilisateur> specialists = utilisateurDAO.findSpecialistesBySpecialite(specialiteId)
                .stream()
                .filter(spec -> spec.getTarif() != null)
                .sorted((s1, s2) -> s1.getTarif().compareTo(s2.getTarif()))
                .collect(Collectors.toList());
        
        if (specialists.isEmpty()) {
            throw new RuntimeException("Aucun spécialiste disponible pour cette spécialité");
        }
        
        Utilisateur specialiste = specialists.get(0);
        
        DemandeExpertise demande = new DemandeExpertise();
        demande.setConsultation(consultation);
        demande.setGeneraliste(generaliste);
        demande.setSpecialiste(specialiste);
        demande.setSpecialite(specialite);
        demande.setQuestion(question);
        demande.setPriorite(priorite);
        demande.setStatut(StatutDemande.EN_ATTENTE);
        
        demandeExpertiseDAO.save(demande);
        return demande;
    }
    
    public List<DemandeExpertise> getExpertiseRequestsForSpecialist(Long specialisteId,
                                                                   StatutDemande statut, Priorite priorite) {
        List<DemandeExpertise> requests = demandeExpertiseDAO.findBySpecialisteId(specialisteId);
        
        return requests.stream()
                .filter(req -> statut == null || req.getStatut().equals(statut))
                .filter(req -> priorite == null || req.getPriorite().equals(priorite))
                .sorted((r1, r2) -> {
                    int priorityCompare = r1.getPriorite().compareTo(r2.getPriorite());
                    if (priorityCompare != 0) return priorityCompare;
                    return r1.getDateCreation().compareTo(r2.getDateCreation());
                })
                .collect(Collectors.toList());
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
    
    public List<Utilisateur> getSpecialistsBySpecialty(Integer specialiteId) {
        return utilisateurDAO.findSpecialistesBySpecialite(specialiteId)
                .stream()
                .sorted((s1, s2) -> s1.getTarif().compareTo(s2.getTarif()))
                .collect(Collectors.toList());
    }
}