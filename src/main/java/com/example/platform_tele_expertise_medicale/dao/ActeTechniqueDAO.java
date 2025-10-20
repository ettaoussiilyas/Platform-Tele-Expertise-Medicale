package com.example.platform_tele_expertise_medicale.dao;

import com.example.platform_tele_expertise_medicale.model.ActeTechnique;
import com.example.platform_tele_expertise_medicale.util.EntityManagerUtil;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.ejb.Stateless;
import java.util.List;

@Stateless
public class ActeTechniqueDAO implements BaseDAO<ActeTechnique, Long> {

    @PersistenceContext(unitName = "medicalPU")
    private EntityManager em;
    
    private EntityManager getEntityManager() {
        if (em == null) {
            return EntityManagerUtil.getEntityManager();
        }
        return em;
    }
    
    @Override
    public void save(ActeTechnique acte) {
        EntityManager entityManager = getEntityManager();
        try {
            entityManager.getTransaction().begin();
            entityManager.persist(acte);
            entityManager.getTransaction().commit();
        } catch (Exception e) {
            entityManager.getTransaction().rollback();
            throw e;
        } finally {
            if (em == null) entityManager.close();
        }
    }
    
    @Override
    public ActeTechnique findById(Long id) {
        return getEntityManager().find(ActeTechnique.class, id);
    }
    
    @Override
    public List<ActeTechnique> findAll() {
        EntityManager entityManager = getEntityManager();
        try {
            return entityManager.createQuery("SELECT a FROM ActeTechnique a ORDER BY a.nom", ActeTechnique.class)
                    .getResultList();
        } finally {
            if (em == null) entityManager.close();
        }
    }
    
    @Override
    public void update(ActeTechnique acte) {
        EntityManager entityManager = getEntityManager();
        try {
            entityManager.getTransaction().begin();
            entityManager.merge(acte);
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
            ActeTechnique acte = entityManager.find(ActeTechnique.class, id);
            if (acte != null) {
                entityManager.remove(acte);
            }
            entityManager.getTransaction().commit();
        } catch (Exception e) {
            entityManager.getTransaction().rollback();
            throw e;
        } finally {
            if (em == null) entityManager.close();
        }
    }

    public List<ActeTechnique> findByNomContaining(String nom) {
        EntityManager entityManager = getEntityManager();
        try {
            return entityManager.createQuery("SELECT a FROM ActeTechnique a WHERE LOWER(a.nom) LIKE LOWER(:nom) ORDER BY a.nom", ActeTechnique.class)
                    .setParameter("nom", "%" + nom + "%")
                    .getResultList();
        } finally {
            if (em == null) entityManager.close();
        }
    }
}