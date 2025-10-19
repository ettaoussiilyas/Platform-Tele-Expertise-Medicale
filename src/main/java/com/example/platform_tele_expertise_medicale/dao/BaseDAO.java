package com.example.platform_tele_expertise_medicale.dao;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.ejb.Stateless;
import java.util.List;

@Stateless
public abstract class BaseDAO<T> {
    
    @PersistenceContext(unitName = "medicalPU")
    protected EntityManager entityManager;
    
    private final Class<T> entityClass;
    
    public BaseDAO(Class<T> entityClass) {
        this.entityClass = entityClass;
    }
    
    public void save(T entity) {
        entityManager.persist(entity);
    }
    
    public T findById(Long id) {
        return entityManager.find(entityClass, id);
    }
    
    public List<T> findAll() {
        return entityManager.createQuery("SELECT e FROM " + entityClass.getSimpleName() + " e", entityClass).getResultList();
    }
    
    public void update(T entity) {
        entityManager.merge(entity);
    }
    
    public void delete(Long id) {
        T entity = findById(id);
        if (entity != null) {
            entityManager.remove(entity);
        }
    }
}