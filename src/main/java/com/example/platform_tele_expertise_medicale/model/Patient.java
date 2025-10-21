package com.example.platform_tele_expertise_medicale.model;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "patients")
public class Patient {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 100)
    private String nom;

    @Column(nullable = false, length = 100)
    private String prenom;

    @Column(length = 150)
    private String email;

    @Column(length = 20)
    private String telephone;

    @Column(name = "date_naissance")
    private LocalDate dateNaissance;

    @Column(name = "numero_securite_sociale", length = 50)
    private String numeroSecuriteSociale;

    @Column(columnDefinition = "TEXT")
    private String adresse;

    @Column(length = 20)
    private String tension;

    @Column(precision = 4, scale = 1)
    private BigDecimal temperature;

    @Column(name = "frequence_cardiaque")
    private Integer frequenceCardiaque;

    @Column(name = "frequence_respiratoire")
    private Integer frequenceRespiratoire;

    @Column(precision = 5, scale = 2)
    private BigDecimal poids;

    @Column(precision = 5, scale = 2)
    private BigDecimal taille;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @OneToMany(mappedBy = "patient")
    private List<Consultation> consultations;

    @OneToMany(mappedBy = "patient")
    private List<DossierMedical> dossiersMedicaux;

    public Patient() {}

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getNom() { return nom; }
    public void setNom(String nom) { this.nom = nom; }

    public String getPrenom() { return prenom; }
    public void setPrenom(String prenom) { this.prenom = prenom; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getTelephone() { return telephone; }
    public void setTelephone(String telephone) { this.telephone = telephone; }

    public LocalDate getDateNaissance() { return dateNaissance; }
    public void setDateNaissance(LocalDate dateNaissance) { this.dateNaissance = dateNaissance; }

    public String getNumeroSecuriteSociale() { return numeroSecuriteSociale; }
    public void setNumeroSecuriteSociale(String numeroSecuriteSociale) { this.numeroSecuriteSociale = numeroSecuriteSociale; }

    public String getAdresse() { return adresse; }
    public void setAdresse(String adresse) { this.adresse = adresse; }

    public String getTension() { return tension; }
    public void setTension(String tension) { this.tension = tension; }

    public BigDecimal getTemperature() { return temperature; }
    public void setTemperature(BigDecimal temperature) { this.temperature = temperature; }

    public Integer getFrequenceCardiaque() { return frequenceCardiaque; }
    public void setFrequenceCardiaque(Integer frequenceCardiaque) { this.frequenceCardiaque = frequenceCardiaque; }

    public Integer getFrequenceRespiratoire() { return frequenceRespiratoire; }
    public void setFrequenceRespiratoire(Integer frequenceRespiratoire) { this.frequenceRespiratoire = frequenceRespiratoire; }

    public BigDecimal getPoids() { return poids; }
    public void setPoids(BigDecimal poids) { this.poids = poids; }

    public BigDecimal getTaille() { return taille; }
    public void setTaille(BigDecimal taille) { this.taille = taille; }

    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }

    public List<Consultation> getConsultations() { return consultations; }
    public void setConsultations(List<Consultation> consultations) { this.consultations = consultations; }

    public List<DossierMedical> getDossiersMedicaux() { return dossiersMedicaux; }
    public void setDossiersMedicaux(List<DossierMedical> dossiersMedicaux) { this.dossiersMedicaux = dossiersMedicaux; }
}
