package com.example.platform_tele_expertise_medicale.dao;

import com.example.platform_tele_expertise_medicale.model.Role;
import com.example.platform_tele_expertise_medicale.model.enums.RoleName;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import jakarta.ejb.Stateless;
import java.util.List;

@Stateless
public class RoleDAO implements BaseDAO<Role, Integer> {
    
    @PersistenceContext(unitName = "medicalPU")
    private EntityManager em;
    
    public RoleDAO() {
    }
    
    private EntityManager getEntityManager() {
        if (em == null) {
            return com.example.platform_tele_expertise_medicale.util.EntityManagerUtil.getEntityManager();
        }
        return em;
    }
    
    @Override
    public void save(Role role) {
        EntityManager entityManager = getEntityManager();
        try {
            entityManager.getTransaction().begin();
            entityManager.persist(role);
            entityManager.getTransaction().commit();
        } catch (Exception e) {
            entityManager.getTransaction().rollback();
            throw e;
        } finally {
            if (em == null) entityManager.close();
        }
    }
    
    @Override
    public Role findById(Integer id) {
        return em.find(Role.class, id);
    }
    
    @Override
    public List<Role> findAll() {
        return em.createQuery("SELECT r FROM Role r", Role.class).getResultList();
    }
    
    @Override
    public void update(Role role) {
        em.merge(role);
    }
    
    @Override
    public void delete(Integer id) {
        Role role = findById(id);
        if (role != null) {
            em.remove(role);
        }
    }
    
    public Role findByRoleName(RoleName roleName) {
        EntityManager entityManager = getEntityManager();
        try {
            TypedQuery<Role> query = entityManager.createQuery(
                "SELECT r FROM Role r WHERE r.roleName = :roleName", Role.class);
            query.setParameter("roleName", roleName);
            return query.getResultStream().findFirst().orElse(null);
        } finally {
            if (em == null) entityManager.close();
        }
    }
}