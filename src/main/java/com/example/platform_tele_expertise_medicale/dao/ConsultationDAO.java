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
    
    private EntityManager getEntityManager() {
        if (em == null) {
            try {
                return com.example.platform_tele_expertise_medicale.util.EntityManagerUtil.getEntityManager();
            } catch (Exception e) {
                throw new RuntimeException("Cannot create EntityManager. Check database connection and persistence.xml: " + e.getMessage(), e);
            }
        }
        return em;
    }
    
    @Override
    public void save(Consultation consultation) {
        EntityManager entityManager = getEntityManager();
        try {
            entityManager.getTransaction().begin();
            entityManager.persist(consultation);
            entityManager.getTransaction().commit();
        } catch (Exception e) {
            entityManager.getTransaction().rollback();
            throw e;
        } finally {
            if (em == null) entityManager.close();
        }
    }
    
    @Override
    public Consultation findById(Long id) {
        EntityManager entityManager = getEntityManager();
        try {
            return entityManager.find(Consultation.class, id);
        } finally {
            if (em == null) entityManager.close();
        }
    }
    
    @Override
    public List<Consultation> findAll() {
        EntityManager entityManager = getEntityManager();
        try {
            return entityManager.createQuery("SELECT c FROM Consultation c", Consultation.class).getResultList();
        } finally {
            if (em == null) entityManager.close();
        }
    }
    
    @Override
    public void update(Consultation consultation) {
        EntityManager entityManager = getEntityManager();
        try {
            entityManager.getTransaction().begin();
            entityManager.merge(consultation);
            entityManager.getTransaction().commit();
        } catch (Exception e) {
            entityManager.getTransaction().rollback();
            throw e;
        } finally {
            if (em == null) entityManager.close();
        }
    }
    
    @Override
    public void delete(Long id) {
        EntityManager entityManager = getEntityManager();
        try {
            entityManager.getTransaction().begin();
            Consultation consultation = entityManager.find(Consultation.class, id);
            if (consultation != null) {
                entityManager.remove(consultation);
            }
            entityManager.getTransaction().commit();
        } catch (Exception e) {
            entityManager.getTransaction().rollback();
            throw e;
        } finally {
            if (em == null) entityManager.close();
        }
    }
    
    public List<Consultation> findByPatientId(Long patientId) {
        EntityManager entityManager = getEntityManager();
        try {
            TypedQuery<Consultation> query = entityManager.createQuery(
                "SELECT c FROM Consultation c WHERE c.patient.id = :patientId ORDER BY c.dateConsultation DESC", 
                Consultation.class);
            query.setParameter("patientId", patientId);
            return query.getResultList();
        } finally {
            if (em == null) entityManager.close();
        }
    }
    
    public List<Consultation> findByMedecinId(Long medecinId) {
        EntityManager entityManager = getEntityManager();
        try {
            TypedQuery<Consultation> query = entityManager.createQuery(
                "SELECT c FROM Consultation c WHERE c.medecin.id = :medecinId ORDER BY c.dateConsultation DESC", 
                Consultation.class);
            query.setParameter("medecinId", medecinId);
            return query.getResultList();
        } finally {
            if (em == null) entityManager.close();
        }
    }
    
    public List<Consultation> findByStatut(StatutConsultation statut) {
        EntityManager entityManager = getEntityManager();
        try {
            TypedQuery<Consultation> query = entityManager.createQuery(
                "SELECT c FROM Consultation c WHERE c.statut = :statut", Consultation.class);
            query.setParameter("statut", statut);
            return query.getResultList();
        } finally {
            if (em == null) entityManager.close();
        }
    }
    
    public Consultation findByIdWithExpertises(Long id) {
        EntityManager entityManager = getEntityManager();
        try {
            TypedQuery<Consultation> query = entityManager.createQuery(
                "SELECT c FROM Consultation c LEFT JOIN FETCH c.demandesExpertises d LEFT JOIN FETCH d.specialiste WHERE c.id = :id", 
                Consultation.class);
            query.setParameter("id", id);
            return query.getResultStream().findFirst().orElse(null);
        } finally {
            if (em == null) entityManager.close();
        }
    }
}