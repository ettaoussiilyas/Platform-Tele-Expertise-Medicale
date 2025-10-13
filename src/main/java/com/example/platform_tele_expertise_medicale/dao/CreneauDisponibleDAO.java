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
    
    @Override
    public void save(CreneauDisponible creneau) {
        em.persist(creneau);
    }
    
    @Override
    public CreneauDisponible findById(Long id) {
        return em.find(CreneauDisponible.class, id);
    }
    
    @Override
    public List<CreneauDisponible> findAll() {
        return em.createQuery("SELECT c FROM CreneauDisponible c", CreneauDisponible.class).getResultList();
    }
    
    @Override
    public void update(CreneauDisponible creneau) {
        em.merge(creneau);
    }
    
    @Override
    public void delete(Long id) {
        CreneauDisponible creneau = findById(id);
        if (creneau != null) {
            em.remove(creneau);
        }
    }
    
    public List<CreneauDisponible> findAvailableByMedecinId(Long medecinId) {
        TypedQuery<CreneauDisponible> query = em.createQuery(
            "SELECT c FROM CreneauDisponible c WHERE c.medecin.id = :medecinId AND c.estDisponible = true AND c.dateDebut > :now ORDER BY c.dateDebut", 
            CreneauDisponible.class);
        query.setParameter("medecinId", medecinId);
        query.setParameter("now", LocalDateTime.now());
        return query.getResultList();
    }
    
    public List<CreneauDisponible> findByMedecinId(Long medecinId) {
        TypedQuery<CreneauDisponible> query = em.createQuery(
            "SELECT c FROM CreneauDisponible c WHERE c.medecin.id = :medecinId ORDER BY c.dateDebut", 
            CreneauDisponible.class);
        query.setParameter("medecinId", medecinId);
        return query.getResultList();
    }
}