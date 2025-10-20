package com.example.platform_tele_expertise_medicale.dao;

import com.example.platform_tele_expertise_medicale.model.DemandeExpertise;
import com.example.platform_tele_expertise_medicale.model.enums.StatutDemande;
import com.example.platform_tele_expertise_medicale.model.enums.Priorite;
import com.example.platform_tele_expertise_medicale.util.EntityManagerUtil;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import jakarta.ejb.Stateless;
import java.util.List;

@Stateless
public class DemandeExpertiseDAO implements BaseDAO<DemandeExpertise, Long> {
    
    @PersistenceContext(unitName = "medicalPU")
    private EntityManager em;
    
    private EntityManager getEntityManager() {
        if (em == null) {
            return EntityManagerUtil.getEntityManager();
        }
        return em;
    }
    
    @Override
    public void save(DemandeExpertise demande) {
        EntityManager entityManager = getEntityManager();
        try {
            entityManager.getTransaction().begin();
            entityManager.persist(demande);
            entityManager.getTransaction().commit();
        } catch (Exception e) {
            entityManager.getTransaction().rollback();
            throw e;
        } finally {
            if (em == null) entityManager.close();
        }
    }
    
    @Override
    public DemandeExpertise findById(Long id) {
        return getEntityManager().find(DemandeExpertise.class, id);
    }
    
    @Override
    public List<DemandeExpertise> findAll() {
        return getEntityManager().createQuery("SELECT d FROM DemandeExpertise d", DemandeExpertise.class).getResultList();
    }
    
    @Override
    public void update(DemandeExpertise demande) {
        EntityManager entityManager = getEntityManager();
        try {
            entityManager.getTransaction().begin();
            entityManager.merge(demande);
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
            DemandeExpertise demande = entityManager.find(DemandeExpertise.class, id);
            if (demande != null) {
                entityManager.remove(demande);
            }
            entityManager.getTransaction().commit();
        } catch (Exception e) {
            entityManager.getTransaction().rollback();
            throw e;
        } finally {
            if (em == null) entityManager.close();
        }
    }
    
    public List<DemandeExpertise> findBySpecialisteId(Long specialisteId) {
        EntityManager entityManager = getEntityManager();
        try {
            TypedQuery<DemandeExpertise> query = entityManager.createQuery(
                "SELECT d FROM DemandeExpertise d WHERE d.specialiste.id = :specialisteId ORDER BY d.priorite, d.dateCreation", 
                DemandeExpertise.class);
            query.setParameter("specialisteId", specialisteId);
            return query.getResultList();
        } finally {
            if (em == null) entityManager.close();
        }
    }
    
    public List<DemandeExpertise> findByStatutAndPriorite(StatutDemande statut, Priorite priorite) {
        EntityManager entityManager = getEntityManager();
        try {
            TypedQuery<DemandeExpertise> query = entityManager.createQuery(
                "SELECT d FROM DemandeExpertise d WHERE d.statut = :statut AND d.priorite = :priorite ORDER BY d.dateCreation", 
                DemandeExpertise.class);
            query.setParameter("statut", statut);
            query.setParameter("priorite", priorite);
            return query.getResultList();
        } finally {
            if (em == null) entityManager.close();
        }
    }
    
    public List<DemandeExpertise> findByGeneralisteId(Long generalisteId) {
        EntityManager entityManager = getEntityManager();
        try {
            TypedQuery<DemandeExpertise> query = entityManager.createQuery(
                "SELECT d FROM DemandeExpertise d WHERE d.generaliste.id = :generalisteId ORDER BY d.dateCreation DESC", 
                DemandeExpertise.class);
            query.setParameter("generalisteId", generalisteId);
            return query.getResultList();
        } finally {
            if (em == null) entityManager.close();
        }
    }
}