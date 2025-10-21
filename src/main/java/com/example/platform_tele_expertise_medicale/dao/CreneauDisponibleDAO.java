package com.example.platform_tele_expertise_medicale.dao;

import com.example.platform_tele_expertise_medicale.model.CreneauDisponible;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import jakarta.ejb.Stateless;
import java.time.LocalDateTime;
import java.util.List;

@Stateless
public class CreneauDisponibleDAO implements BaseDAO<CreneauDisponible, Long> {
    
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
    public void save(CreneauDisponible creneau) {
        EntityManager entityManager = getEntityManager();
        try {
            entityManager.getTransaction().begin();
            entityManager.persist(creneau);
            entityManager.getTransaction().commit();
        } catch (Exception e) {
            entityManager.getTransaction().rollback();
            throw e;
        } finally {
            if (em == null) entityManager.close();
        }
    }
    
    @Override
    public CreneauDisponible findById(Long id) {
        EntityManager entityManager = getEntityManager();
        try {
            return entityManager.find(CreneauDisponible.class, id);
        } finally {
            if (em == null) entityManager.close();
        }
    }
    
    @Override
    public List<CreneauDisponible> findAll() {
        EntityManager entityManager = getEntityManager();
        try {
            return entityManager.createQuery("SELECT c FROM CreneauDisponible c", CreneauDisponible.class).getResultList();
        } finally {
            if (em == null) entityManager.close();
        }
    }
    
    @Override
    public void update(CreneauDisponible creneau) {
        EntityManager entityManager = getEntityManager();
        try {
            entityManager.getTransaction().begin();
            entityManager.merge(creneau);
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
            CreneauDisponible creneau = entityManager.find(CreneauDisponible.class, id);
            if (creneau != null) {
                entityManager.remove(creneau);
            }
            entityManager.getTransaction().commit();
        } catch (Exception e) {
            entityManager.getTransaction().rollback();
            throw e;
        } finally {
            if (em == null) entityManager.close();
        }
    }
    
    public List<CreneauDisponible> findAvailableByMedecinId(Long medecinId) {
        EntityManager entityManager = getEntityManager();
        try {
            TypedQuery<CreneauDisponible> query = entityManager.createQuery(
                "SELECT c FROM CreneauDisponible c WHERE c.medecin.id = :medecinId AND c.estDisponible = true AND c.dateDebut > :now ORDER BY c.dateDebut", 
                CreneauDisponible.class);
            query.setParameter("medecinId", medecinId);
            query.setParameter("now", LocalDateTime.now());
            return query.getResultList();
        } finally {
            if (em == null) entityManager.close();
        }
    }
    
    public List<CreneauDisponible> findByMedecinId(Long medecinId) {
        EntityManager entityManager = getEntityManager();
        try {
            TypedQuery<CreneauDisponible> query = entityManager.createQuery(
                "SELECT c FROM CreneauDisponible c WHERE c.medecin.id = :medecinId ORDER BY c.dateDebut", 
                CreneauDisponible.class);
            query.setParameter("medecinId", medecinId);
            return query.getResultList();
        } finally {
            if (em == null) entityManager.close();
        }
    }
}