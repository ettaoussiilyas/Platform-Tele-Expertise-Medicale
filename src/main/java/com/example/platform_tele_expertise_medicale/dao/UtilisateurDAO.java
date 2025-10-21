package com.example.platform_tele_expertise_medicale.dao;

import com.example.platform_tele_expertise_medicale.model.Utilisateur;
import com.example.platform_tele_expertise_medicale.model.enums.RoleName;
import com.example.platform_tele_expertise_medicale.util.EntityManagerUtil;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import jakarta.ejb.Stateless;
import java.util.List;

@Stateless
public class UtilisateurDAO implements BaseDAO<Utilisateur, Long> {
    
    @PersistenceContext(unitName = "medicalPU")
    private EntityManager em;
    
    private EntityManager getEntityManager() {
        if (em == null) {
            try {
                return EntityManagerUtil.getEntityManager();
            } catch (Exception e) {
                throw new RuntimeException("Cannot create EntityManager. Check database connection and persistence.xml: " + e.getMessage(), e);
            }
        }
        return em;
    }
    
    @Override
    public void save(Utilisateur utilisateur) {
        EntityManager entityManager = getEntityManager();
        try {
            entityManager.getTransaction().begin();
            entityManager.persist(utilisateur);
            entityManager.getTransaction().commit();
        } catch (Exception e) {
            entityManager.getTransaction().rollback();
            throw e;
        } finally {
            if (em == null) entityManager.close();
        }
    }
    
    @Override
    public Utilisateur findById(Long id) {
        EntityManager entityManager = getEntityManager();
        try {
            return entityManager.find(Utilisateur.class, id);
        } finally {
            if (em == null) entityManager.close();
        }
    }
    
    @Override
    public List<Utilisateur> findAll() {
        EntityManager entityManager = getEntityManager();
        try {
            return entityManager.createQuery("SELECT u FROM Utilisateur u", Utilisateur.class).getResultList();
        } finally {
            if (em == null) entityManager.close();
        }
    }
    
    @Override
    public void update(Utilisateur utilisateur) {
        EntityManager entityManager = getEntityManager();
        try {
            entityManager.getTransaction().begin();
            entityManager.merge(utilisateur);
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
            Utilisateur utilisateur = entityManager.find(Utilisateur.class, id);
            if (utilisateur != null) {
                entityManager.remove(utilisateur);
            }
            entityManager.getTransaction().commit();
        } catch (Exception e) {
            entityManager.getTransaction().rollback();
            throw e;
        } finally {
            if (em == null) entityManager.close();
        }
    }
    
    public Utilisateur findByEmail(String email) {
        EntityManager entityManager = getEntityManager();
        try {
            TypedQuery<Utilisateur> query = entityManager.createQuery(
                "SELECT u FROM Utilisateur u WHERE u.email = :email", Utilisateur.class);
            query.setParameter("email", email);
            return query.getResultStream().findFirst().orElse(null);
        } finally {
            if (em == null) entityManager.close();
        }
    }
    
    public List<Utilisateur> findByRole(RoleName roleName) {
        EntityManager entityManager = getEntityManager();
        try {
            TypedQuery<Utilisateur> query = entityManager.createQuery(
                "SELECT u FROM Utilisateur u WHERE u.role.roleName = :roleName", Utilisateur.class);
            query.setParameter("roleName", roleName);
            return query.getResultList();
        } finally {
            if (em == null) entityManager.close();
        }
    }
    
    public List<Utilisateur> findSpecialistesBySpecialite(Integer specialiteId) {
        EntityManager entityManager = getEntityManager();
        try {
            TypedQuery<Utilisateur> query = entityManager.createQuery(
                "SELECT u FROM Utilisateur u WHERE u.specialite.id = :specialiteId ORDER BY u.tarif", Utilisateur.class);
            query.setParameter("specialiteId", specialiteId);
            return query.getResultList();
        } finally {
            if (em == null) entityManager.close();
        }
    }
}
