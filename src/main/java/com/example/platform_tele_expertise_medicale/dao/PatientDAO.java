package com.example.platform_tele_expertise_medicale.dao;

import com.example.platform_tele_expertise_medicale.model.Patient;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import jakarta.ejb.Stateless;
import java.time.LocalDate;
import java.util.List;

@Stateless
public class PatientDAO implements BaseDAO<Patient, Long> {
    
    @PersistenceContext(unitName = "medicalPU")
    private EntityManager em;
    
    public PatientDAO() {
        // Default constructor for manual instantiation
    }
    
    private EntityManager getEntityManager() {
        if (em == null) {
            try {
                return com.example.platform_tele_expertise_medicale.util.EntityManagerUtil.getEntityManager();
            } catch (Exception e) {
                throw new RuntimeException("Cannot create EntityManager. Check database connection and persistence.xml: " + e.getMessage(), e);
            }
        }
        return em;
    }
    
    @Override
    public void save(Patient patient) {
        EntityManager entityManager = getEntityManager();
        try {
            entityManager.getTransaction().begin();
            entityManager.persist(patient);
            entityManager.getTransaction().commit();
        } catch (Exception e) {
            entityManager.getTransaction().rollback();
            throw e;
        } finally {
            if (em == null) entityManager.close();
        }
    }
    
    @Override
    public Patient findById(Long id) {
        EntityManager entityManager = getEntityManager();
        try {
            return entityManager.find(Patient.class, id);
        } finally {
            if (em == null) entityManager.close();
        }
    }
    
    @Override
    public List<Patient> findAll() {
        EntityManager entityManager = getEntityManager();
        try {
            return entityManager.createQuery("SELECT p FROM Patient p", Patient.class).getResultList();
        } finally {
            if (em == null) entityManager.close();
        }
    }
    
    @Override
    public void update(Patient patient) {
        EntityManager entityManager = getEntityManager();
        try {
            entityManager.getTransaction().begin();
            entityManager.merge(patient);
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
            Patient patient = entityManager.find(Patient.class, id);
            if (patient != null) {
                entityManager.remove(patient);
            }
            entityManager.getTransaction().commit();
        } catch (Exception e) {
            entityManager.getTransaction().rollback();
            throw e;
        } finally {
            if (em == null) entityManager.close();
        }
    }
    
    public Patient findByNumeroSecuriteSociale(String numeroSS) {
        EntityManager entityManager = getEntityManager();
        try {
            TypedQuery<Patient> query = entityManager.createQuery(
                "SELECT p FROM Patient p WHERE p.numeroSecuriteSociale = :numeroSS", Patient.class);
            query.setParameter("numeroSS", numeroSS);
            return query.getResultStream().findFirst().orElse(null);
        } finally {
            if (em == null) entityManager.close();
        }
    }
    
    public List<Patient> findPatientsOfDay(LocalDate date) {
        EntityManager entityManager = getEntityManager();
        try {
            TypedQuery<Patient> query = entityManager.createQuery(
                "SELECT p FROM Patient p WHERE DATE(p.createdAt) = :date ORDER BY p.createdAt", Patient.class);
            query.setParameter("date", date);
            return query.getResultList();
        } finally {
            if (em == null) entityManager.close();
        }
    }
    
    public List<Patient> searchByName(String nom, String prenom) {
        EntityManager entityManager = getEntityManager();
        try {
            TypedQuery<Patient> query = entityManager.createQuery(
                "SELECT p FROM Patient p WHERE p.nom LIKE :nom AND p.prenom LIKE :prenom", Patient.class);
            query.setParameter("nom", "%" + nom + "%");
            query.setParameter("prenom", "%" + prenom + "%");
            return query.getResultList();
        } finally {
            if (em == null) entityManager.close();
        }
    }
    
    public Patient findByEmail(String email) {
        EntityManager entityManager = getEntityManager();
        try {
            TypedQuery<Patient> query = entityManager.createQuery(
                "SELECT p FROM Patient p WHERE p.email = :email", Patient.class);
            query.setParameter("email", email);
            return query.getResultStream().findFirst().orElse(null);
        } finally {
            if (em == null) entityManager.close();
        }
    }
    
    public List<Patient> findPatientsOfToday() {
        EntityManager entityManager = getEntityManager();
        try {
            TypedQuery<Patient> query = entityManager.createQuery(
                "SELECT p FROM Patient p WHERE DATE(p.createdAt) = CURRENT_DATE ORDER BY p.createdAt", Patient.class);
            return query.getResultList();
        } finally {
            if (em == null) entityManager.close();
        }
    }
}