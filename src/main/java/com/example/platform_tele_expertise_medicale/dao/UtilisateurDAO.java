package com.example.platform_tele_expertise_medicale.dao;

import com.example.platform_tele_expertise_medicale.model.Utilisateur;
import com.example.platform_tele_expertise_medicale.model.enums.RoleName;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import jakarta.ejb.Stateless;
import java.util.List;

@Stateless
public class UtilisateurDAO implements BaseDAO<Utilisateur, Long> {
    
    @PersistenceContext(unitName = "medicalPU")
    private EntityManager em;
    
    public UtilisateurDAO() {
        // Default constructor for manual instantiation
    }
    
    private EntityManager getEntityManager() {
        if (em == null) {
            return com.example.platform_tele_expertise_medicale.util.EntityManagerUtil.getEntityManager();
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
        return em.find(Utilisateur.class, id);
    }
    
    @Override
    public List<Utilisateur> findAll() {
        return em.createQuery("SELECT u FROM Utilisateur u", Utilisateur.class).getResultList();
    }
    
    @Override
    public void update(Utilisateur utilisateur) {
        em.merge(utilisateur);
    }
    
    @Override
    public void delete(Long id) {
        Utilisateur utilisateur = findById(id);
        if (utilisateur != null) {
            em.remove(utilisateur);
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
        TypedQuery<Utilisateur> query = em.createQuery(
            "SELECT u FROM Utilisateur u WHERE u.role.roleName = :roleName", Utilisateur.class);
        query.setParameter("roleName", roleName);
        return query.getResultList();
    }
    
    public List<Utilisateur> findSpecialistesBySpecialite(Integer specialiteId) {
        TypedQuery<Utilisateur> query = em.createQuery(
            "SELECT u FROM Utilisateur u WHERE u.specialite.id = :specialiteId ORDER BY u.tarif", Utilisateur.class);
        query.setParameter("specialiteId", specialiteId);
        return query.getResultList();
    }
}
