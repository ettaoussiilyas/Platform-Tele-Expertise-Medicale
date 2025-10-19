package com.example.platform_tele_expertise_medicale.dao;

import com.example.platform_tele_expertise_medicale.model.Utilisateur;
import com.example.platform_tele_expertise_medicale.model.enums.RoleName;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import jakarta.ejb.Stateless;
import java.util.List;

@Stateless
public class UtilisateurDAO extends BaseDAO<Utilisateur> {
    
    public UtilisateurDAO() {
        super(Utilisateur.class);
    }
    
    public Utilisateur findByEmail(String email) {
        TypedQuery<Utilisateur> query = entityManager.createQuery(
            "SELECT u FROM Utilisateur u WHERE u.email = :email", Utilisateur.class);
        query.setParameter("email", email);
        return query.getResultStream().findFirst().orElse(null);
    }
    
    public List<Utilisateur> findByRole(RoleName roleName) {
        TypedQuery<Utilisateur> query = entityManager.createQuery(
            "SELECT u FROM Utilisateur u WHERE u.role.roleName = :roleName", Utilisateur.class);
        query.setParameter("roleName", roleName);
        return query.getResultList();
    }
    
    public List<Utilisateur> findSpecialistesBySpecialite(Integer specialiteId) {
        TypedQuery<Utilisateur> query = entityManager.createQuery(
            "SELECT u FROM Utilisateur u WHERE u.specialite.id = :specialiteId ORDER BY u.tarif", Utilisateur.class);
        query.setParameter("specialiteId", specialiteId);
        return query.getResultList();
    }
}
