package com.example.platform_tele_expertise_medicale.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "specialites")
public class Specialite {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotBlank(message = "name must be not empty")
    @Column(name = "nom", nullable = false, unique = true, length = 100)
    private String specialiteName;

    @OneToMany(mappedBy = "specialite")
    private List<Utilisateur> utilisateurs = new ArrayList<>();

    @OneToMany(mappedBy = "specialite")
    private List<DemandeExpertise> demandeExpertises = new ArrayList<>();

    public Specialite(){

    }

    public Specialite(String specialiteName){
        this.specialiteName = specialiteName;
    }

    public Integer getId() {
        return id;
    }

    public String getSpecialiteName() {
        return specialiteName;
    }
    
    public String getNom() {
        return specialiteName;
    }

    public List<Utilisateur> getUtilisateurs() {
        return utilisateurs;
    }

    public List<DemandeExpertise> getDemandeExpertises() {
        return demandeExpertises;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setSpecialiteName(String specialiteName) {
        this.specialiteName = specialiteName;
    }
    
    public void setNom(String nom) {
        this.specialiteName = nom;
    }

    public void setUtilisateurs(List<Utilisateur> utilisateurs) {
        this.utilisateurs = utilisateurs;
    }

    public void setDemandeExpertises(List<DemandeExpertise> demandeExpertises) {
        this.demandeExpertises = demandeExpertises;
    }
}
