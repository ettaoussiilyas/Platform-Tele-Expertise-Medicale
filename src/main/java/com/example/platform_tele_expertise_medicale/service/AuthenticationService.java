package com.example.platform_tele_expertise_medicale.service;

import com.example.platform_tele_expertise_medicale.dao.UtilisateurDAO;
import com.example.platform_tele_expertise_medicale.dao.RoleDAO;
import com.example.platform_tele_expertise_medicale.model.Utilisateur;
import com.example.platform_tele_expertise_medicale.model.Role;
import com.example.platform_tele_expertise_medicale.model.enums.RoleName;
import jakarta.ejb.Stateless;
import jakarta.inject.Inject;
import org.mindrot.jbcrypt.BCrypt;

@Stateless
public class AuthenticationService {
    
    @Inject
    private UtilisateurDAO utilisateurDAO;
    
    @Inject
    private RoleDAO roleDAO;
    
    public Utilisateur authenticate(String email, String password) {
        Utilisateur user = utilisateurDAO.findByEmail(email);
        if (user != null && BCrypt.checkpw(password, user.getMotDePasse())) {
            return user;
        }
        return null;
    }
    
    public Utilisateur registerUser(String nom, String prenom, String email, String password, 
                                   String telephone, RoleName roleName) {
        if (utilisateurDAO.findByEmail(email) != null) {
            throw new RuntimeException("Email déjà utilisé");
        }
        
        Utilisateur user = new Utilisateur();
        user.setNom(nom);
        user.setPrenom(prenom);
        user.setEmail(email);
        user.setMotDePasse(hashPassword(password));
        user.setTelephone(telephone);
        
        Role role = roleDAO.findByRoleName(roleName);
        user.setRole(role);
        
        utilisateurDAO.save(user);
        return user;
    }
    
    private String hashPassword(String password) {
        return BCrypt.hashpw(password, BCrypt.gensalt());
    }
}
