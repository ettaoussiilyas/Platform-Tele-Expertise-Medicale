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
    
    @Override
    public void save(Specialite specialite) {
        em.persist(specialite);
    }
    
    @Override
    public Specialite findById(Integer id) {
        return em.find(Specialite.class, id);
    }
    
    @Override
    public List<Specialite> findAll() {
        return em.createQuery("SELECT s FROM Specialite s ORDER BY s.specialiteName", Specialite.class).getResultList();
    }
    
    @Override
    public void update(Specialite specialite) {
        em.merge(specialite);
    }
    
    @Override
    public void delete(Integer id) {
        Specialite specialite = findById(id);
        if (specialite != null) {
            em.remove(specialite);
        }
    }
    
    public Specialite findByName(String specialiteName) {
        TypedQuery<Specialite> query = em.createQuery(
            "SELECT s FROM Specialite s WHERE s.specialiteName = :name", Specialite.class);
        query.setParameter("name", specialiteName);
        return query.getResultStream().findFirst().orElse(null);
    }
}