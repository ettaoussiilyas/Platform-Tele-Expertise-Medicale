package com.example.platform_tele_expertise_medicale.dao;

import com.example.platform_tele_expertise_medicale.model.DemandeExpertise;
import com.example.platform_tele_expertise_medicale.model.enums.StatutDemande;
import com.example.platform_tele_expertise_medicale.model.enums.Priorite;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import jakarta.ejb.Stateless;
import java.util.List;

@Stateless
public class DemandeExpertiseDAO implements BaseDAO<DemandeExpertise, Long> {
    
    @PersistenceContext(unitName = "medicalPU")
    private EntityManager em;
    
    @Override
    public void save(DemandeExpertise demande) {
        em.persist(demande);
    }
    
    @Override
    public DemandeExpertise findById(Long id) {
        return em.find(DemandeExpertise.class, id);
    }
    
    @Override
    public List<DemandeExpertise> findAll() {
        return em.createQuery("SELECT d FROM DemandeExpertise d", DemandeExpertise.class).getResultList();
    }
    
    @Override
    public void update(DemandeExpertise demande) {
        em.merge(demande);
    }
    
    @Override
    public void delete(Long id) {
        DemandeExpertise demande = findById(id);
        if (demande != null) {
            em.remove(demande);
        }
    }
    
    public List<DemandeExpertise> findBySpecialisteId(Long specialisteId) {
        TypedQuery<DemandeExpertise> query = em.createQuery(
            "SELECT d FROM DemandeExpertise d WHERE d.specialiste.id = :specialisteId ORDER BY d.priorite, d.dateCreation", 
            DemandeExpertise.class);
        query.setParameter("specialisteId", specialisteId);
        return query.getResultList();
    }
    
    public List<DemandeExpertise> findByStatutAndPriorite(StatutDemande statut, Priorite priorite) {
        TypedQuery<DemandeExpertise> query = em.createQuery(
            "SELECT d FROM DemandeExpertise d WHERE d.statut = :statut AND d.priorite = :priorite ORDER BY d.dateCreation", 
            DemandeExpertise.class);
        query.setParameter("statut", statut);
        query.setParameter("priorite", priorite);
        return query.getResultList();
    }
    
    public List<DemandeExpertise> findByGeneralisteId(Long generalisteId) {
        TypedQuery<DemandeExpertise> query = em.createQuery(
            "SELECT d FROM DemandeExpertise d WHERE d.generaliste.id = :generalisteId ORDER BY d.dateCreation DESC", 
            DemandeExpertise.class);
        query.setParameter("generalisteId", generalisteId);
        return query.getResultList();
    }
}