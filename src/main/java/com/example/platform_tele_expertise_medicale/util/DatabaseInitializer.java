package com.example.platform_tele_expertise_medicale.util;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.stream.Collectors;

public class DatabaseInitializer {
    
    public static void initializeDatabase() {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("medicalPU");
        EntityManager em = emf.createEntityManager();
        
        try {
            Long roleCount = em.createQuery("SELECT COUNT(r) FROM Role r", Long.class).getSingleResult();
            
            if (roleCount == 0) {
                System.out.println("Inserting initial data...");
                executeInitialDataScript(em);
            } else {
                System.out.println("Data already exists, skipping initialization.");
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            em.close();
            emf.close();
        }
    }
    
    private static void executeInitialDataScript(EntityManager em) {
        try {
            InputStream is = DatabaseInitializer.class.getClassLoader().getResourceAsStream("initial-data.sql");
            if (is == null) {
                System.out.println("initial-data.sql not found in resources");
                return;
            }
            
            String sql = new BufferedReader(new InputStreamReader(is))
                .lines().collect(Collectors.joining("\n"));
            
            String[] statements = sql.split(";");
            
            em.getTransaction().begin();
            for (String statement : statements) {
                if (!statement.trim().isEmpty()) {
                    em.createNativeQuery(statement.trim()).executeUpdate();
                }
            }
            em.getTransaction().commit();
            
            System.out.println("Initial data inserted successfully.");
        } catch (Exception e) {
            em.getTransaction().rollback();
            e.printStackTrace();
        }
    }
}