package com.example.platform_tele_expertise_medicale.dao;

import com.example.platform_tele_expertise_medicale.model.Consultation;
import com.example.platform_tele_expertise_medicale.model.enums.StatutConsultation;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import jakarta.ejb.Stateless;
import java.util.List;

@Stateless
public class ConsultationDAO implements BaseDAO<Consultation, Long> {
    
    @PersistenceContext(unitName = "medicalPU")
    private EntityManager em;
    
    @Override
    public void save(Consultation consultation) {
        em.persist(consultation);
    }
    
    @Override
    public Consultation findById(Long id) {
        return em.find(Consultation.class, id);
    }
    
    @Override
    public List<Consultation> findAll() {
        return em.createQuery("SELECT c FROM Consultation c", Consultation.class).getResultList();
    }
    
    @Override
    public void update(Consultation consultation) {
        em.merge(consultation);
    }
    
    @Override
    public void delete(Long id) {
        Consultation consultation = findById(id);
        if (consultation != null) {
            em.remove(consultation);
        }
    }
    
    public List<Consultation> findByPatientId(Long patientId) {
        TypedQuery<Consultation> query = em.createQuery(
            "SELECT c FROM Consultation c WHERE c.patient.id = :patientId ORDER BY c.dateConsultation DESC", 
            Consultation.class);
        query.setParameter("patientId", patientId);
        return query.getResultList();
    }
    
    public List<Consultation> findByMedecinId(Long medecinId) {
        TypedQuery<Consultation> query = em.createQuery(
            "SELECT c FROM Consultation c WHERE c.medecin.id = :medecinId ORDER BY c.dateConsultation DESC", 
            Consultation.class);
        query.setParameter("medecinId", medecinId);
        return query.getResultList();
    }
    
    public List<Consultation> findByStatut(StatutConsultation statut) {
        TypedQuery<Consultation> query = em.createQuery(
            "SELECT c FROM Consultation c WHERE c.statut = :statut", Consultation.class);
        query.setParameter("statut", statut);
        return query.getResultList();
    }
}