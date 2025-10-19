package com.example.platform_tele_expertise_medicale.dao;

import com.example.platform_tele_expertise_medicale.model.ActeTechnique;
import jakarta.ejb.Stateless;
import java.util.List;

@Stateless
public class ActeTechniqueDAO extends BaseDAO<ActeTechnique> {

    public ActeTechniqueDAO() {
        super(ActeTechnique.class);
    }

    public List<ActeTechnique> findAll() {
        return entityManager.createQuery("SELECT a FROM ActeTechnique a ORDER BY a.nom", ActeTechnique.class)
                .getResultList();
    }

    public List<ActeTechnique> findByNomContaining(String nom) {
        return entityManager.createQuery("SELECT a FROM ActeTechnique a WHERE LOWER(a.nom) LIKE LOWER(:nom) ORDER BY a.nom", ActeTechnique.class)
                .setParameter("nom", "%" + nom + "%")
                .getResultList();
    }
}