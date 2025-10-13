package com.example.platform_tele_expertise_medicale.service;

import com.example.platform_tele_expertise_medicale.dao.UtilisateurDAO;
import com.example.platform_tele_expertise_medicale.dao.RoleDAO;
import com.example.platform_tele_expertise_medicale.model.Utilisateur;
import com.example.platform_tele_expertise_medicale.model.Role;
import com.example.platform_tele_expertise_medicale.model.enums.RoleName;
import jakarta.ejb.Stateless;
import jakarta.inject.Inject;

@Stateless
public class AuthenticationService {
    
    @Inject
    private UtilisateurDAO utilisateurDAO;
    
    @Inject
    private RoleDAO roleDAO;
    
    public Utilisateur authenticate(String email, String password) {
        Utilisateur user = utilisateurDAO.findByEmail(email);
        if (user != null && user.getMotDePasse().equals(password)) {
            return user;
        }
        return null;
    }
    
    public Utilisateur registerUser(String nom, String prenom, String email, String password, 
                                   String telephone, RoleName roleName) {
        // Check if email exists
        if (utilisateurDAO.findByEmail(email) != null) {
            throw new RuntimeException("Email déjà utilisé");
        }
        
        Utilisateur user = new Utilisateur();
        user.setNom(nom);
        user.setPrenom(prenom);
        user.setEmail(email);
        user.setMotDePasse(password);
        user.setTelephone(telephone);
        
        Role role = roleDAO.findByRoleName(roleName);
        user.setRole(role);
        
        utilisateurDAO.save(user);
        return user;
    }
}
