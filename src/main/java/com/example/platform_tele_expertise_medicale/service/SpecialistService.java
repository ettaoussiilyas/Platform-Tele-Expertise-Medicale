package com.example.platform_tele_expertise_medicale.service;

import com.example.platform_tele_expertise_medicale.dao.UtilisateurDAO;
import com.example.platform_tele_expertise_medicale.dao.SpecialiteDAO;
import com.example.platform_tele_expertise_medicale.dao.CreneauDisponibleDAO;
import com.example.platform_tele_expertise_medicale.model.Utilisateur;
import com.example.platform_tele_expertise_medicale.model.Specialite;
import com.example.platform_tele_expertise_medicale.model.CreneauDisponible;
import jakarta.ejb.Stateless;
import jakarta.inject.Inject;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

@Stateless
public class SpecialistService {
    
    @Inject
    private UtilisateurDAO utilisateurDAO;
    
    @Inject
    private SpecialiteDAO specialiteDAO;
    
    @Inject
    private CreneauDisponibleDAO creneauDAO;
    
    public Utilisateur configureProfile(Long specialistId, Integer specialiteId,
                                       BigDecimal tarif, Integer dureeConsultation) {
        Utilisateur specialist = utilisateurDAO.findById(specialistId);
        Specialite specialite = specialiteDAO.findById(specialiteId);
        
        if (specialist != null && specialite != null) {
            specialist.setSpecialite(specialite);
            specialist.setTarif(tarif);
            specialist.setDureeConsultation(dureeConsultation != null ? dureeConsultation : 30);
            utilisateurDAO.update(specialist);
        }
        return specialist;
    }
    
    public List<CreneauDisponible> generateTimeSlots(Long specialistId, LocalDateTime startDate,
                                                    LocalDateTime endDate) {
        Utilisateur specialist = utilisateurDAO.findById(specialistId);
        if (specialist == null) {
            throw new RuntimeException("Spécialiste introuvable");
        }
        
        List<CreneauDisponible> slots = new ArrayList<>();
        LocalDateTime current = startDate;
        
        while (current.isBefore(endDate)) {
            CreneauDisponible slot = new CreneauDisponible();
            slot.setMedecin(specialist);
            slot.setDateDebut(current);
            slot.setDateFin(current.plusMinutes(30)); // Fixed 30 min duration
            slot.setEstDisponible(true);
            
            creneauDAO.save(slot);
            slots.add(slot);
            
            current = current.plusMinutes(30);
        }
        
        return slots;
    }
    
    public List<CreneauDisponible> getSpecialistSlots(Long specialistId) {
        List<CreneauDisponible> slots = creneauDAO.findByMedecinId(specialistId);
        
        // Update slot status automatically
        LocalDateTime now = LocalDateTime.now();
        for (CreneauDisponible slot : slots) {
            if (slot.getDateDebut().isBefore(now) && slot.getEstDisponible()) {
                // Mark past slots as unavailable
                slot.setEstDisponible(false);
                creneauDAO.update(slot);
            }
        }
        
        return slots;
    }
    
    public CreneauDisponible reserveSlot(Long slotId) {
        CreneauDisponible slot = creneauDAO.findById(slotId);
        if (slot != null && slot.getEstDisponible()) {
            slot.setEstDisponible(false);
            creneauDAO.update(slot);
        }
        return slot;
    }
    
    public CreneauDisponible cancelReservation(Long slotId) {
        CreneauDisponible slot = creneauDAO.findById(slotId);
        if (slot != null && !slot.getEstDisponible() && slot.getDateDebut().isAfter(LocalDateTime.now())) {
            slot.setEstDisponible(true);
            creneauDAO.update(slot);
        }
        return slot;
    }
    
    public List<Specialite> getAllSpecialties() {
        return specialiteDAO.findAll();
    }
}