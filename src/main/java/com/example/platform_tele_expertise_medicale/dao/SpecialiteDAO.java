package com.example.platform_tele_expertise_medicale.dao;

import com.example.platform_tele_expertise_medicale.model.Specialite;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import jakarta.ejb.Stateless;
import java.util.List;

@Stateless
public class SpecialiteDAO implements BaseDAO<Specialite, Integer> {
    
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
    public void save(Specialite specialite) {
        EntityManager entityManager = getEntityManager();
        try {
            entityManager.getTransaction().begin();
            entityManager.persist(specialite);
            entityManager.getTransaction().commit();
        } catch (Exception e) {
            entityManager.getTransaction().rollback();
            throw e;
        } finally {
            if (em == null) entityManager.close();
        }
    }
    
    @Override
    public Specialite findById(Integer id) {
        EntityManager entityManager = getEntityManager();
        try {
            return entityManager.find(Specialite.class, id);
        } finally {
            if (em == null) entityManager.close();
        }
    }
    
    @Override
    public List<Specialite> findAll() {
        EntityManager entityManager = getEntityManager();
        try {
            return entityManager.createQuery("SELECT s FROM Specialite s ORDER BY s.specialiteName", Specialite.class).getResultList();
        } finally {
            if (em == null) entityManager.close();
        }
    }
    
    @Override
    public void update(Specialite specialite) {
        EntityManager entityManager = getEntityManager();
        try {
            entityManager.getTransaction().begin();
            entityManager.merge(specialite);
            entityManager.getTransaction().commit();
        } catch (Exception e) {
            entityManager.getTransaction().rollback();
            throw e;
        } finally {
            if (em == null) entityManager.close();
        }
    }
    
    @Override
    public void delete(Integer id) {
        EntityManager entityManager = getEntityManager();
        try {
            entityManager.getTransaction().begin();
            Specialite specialite = entityManager.find(Specialite.class, id);
            if (specialite != null) {
                entityManager.remove(specialite);
            }
            entityManager.getTransaction().commit();
        } catch (Exception e) {
            entityManager.getTransaction().rollback();
            throw e;
        } finally {
            if (em == null) entityManager.close();
        }
    }
    
    public Specialite findByName(String specialiteName) {
        EntityManager entityManager = getEntityManager();
        try {
            TypedQuery<Specialite> query = entityManager.createQuery(
                "SELECT s FROM Specialite s WHERE s.specialiteName = :name", Specialite.class);
            query.setParameter("name", specialiteName);
            return query.getResultStream().findFirst().orElse(null);
        } finally {
            if (em == null) entityManager.close();
        }
    }
}