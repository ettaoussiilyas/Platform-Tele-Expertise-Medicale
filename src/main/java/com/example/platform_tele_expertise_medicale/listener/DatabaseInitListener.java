package com.example.platform_tele_expertise_medicale.listener;

import com.example.platform_tele_expertise_medicale.util.DatabaseInitializer;
import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;
import jakarta.servlet.annotation.WebListener;

@WebListener
public class DatabaseInitListener implements ServletContextListener {
    
    @Override
    public void contextInitialized(ServletContextEvent sce) {
        System.out.println("Initializing database tables...");
        DatabaseInitializer.initializeDatabase();
        System.out.println("Database initialization completed.");
    }
    
    @Override
    public void contextDestroyed(ServletContextEvent sce) {
    }
}