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
    
    @Override
    public void save(Patient patient) {
        em.persist(patient);
    }
    
    @Override
    public Patient findById(Long id) {
        return em.find(Patient.class, id);
    }
    
    @Override
    public List<Patient> findAll() {
        return em.createQuery("SELECT p FROM Patient p", Patient.class).getResultList();
    }
    
    @Override
    public void update(Patient patient) {
        em.merge(patient);
    }
    
    @Override
    public void delete(Long id) {
        Patient patient = findById(id);
        if (patient != null) {
            em.remove(patient);
        }
    }
    
    public Patient findByNumeroSecuriteSociale(String numeroSS) {
        TypedQuery<Patient> query = em.createQuery(
            "SELECT p FROM Patient p WHERE p.numeroSecuriteSociale = :numeroSS", Patient.class);
        query.setParameter("numeroSS", numeroSS);
        return query.getResultStream().findFirst().orElse(null);
    }
    
    public List<Patient> findPatientsOfDay(LocalDate date) {
        TypedQuery<Patient> query = em.createQuery(
            "SELECT p FROM Patient p WHERE DATE(p.createdAt) = :date ORDER BY p.createdAt", Patient.class);
        query.setParameter("date", date);
        return query.getResultList();
    }
    
    public List<Patient> searchByName(String nom, String prenom) {
        TypedQuery<Patient> query = em.createQuery(
            "SELECT p FROM Patient p WHERE p.nom LIKE :nom AND p.prenom LIKE :prenom", Patient.class);
        query.setParameter("nom", "%" + nom + "%");
        query.setParameter("prenom", "%" + prenom + "%");
        return query.getResultList();
    }
}