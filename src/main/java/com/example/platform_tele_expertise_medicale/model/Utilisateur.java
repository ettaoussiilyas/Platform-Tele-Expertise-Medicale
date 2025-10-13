package com.example.platform_tele_expertise_medicale.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;


@Entity
@Table(name = "utilisateurs")
public class Utilisateur {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 100)
    private String nom;

    @Column(nullable = false, length = 100)
    private String prenom;

    @Email(message = "Inccorect Email Format")
    @Column(nullable = false, unique = true, length = 150)
    private String email;

    @Column(name = "mot_de_passe", nullable = false)
    private String motDePasse;

    @Column(length = 20)
    private String telephone;

    @ManyToOne
    @JoinColumn(name = "role_id", nullable = false)
    private Role role;

    @ManyToOne
    @JoinColumn(name = "specialite_id")
    private Specialite specialite;

    @Column(precision = 10, scale = 2)
    private BigDecimal tarif;

    @Column(name ="duree_consultation")
    private Integer dureeConsultation;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @OneToMany(mappedBy = "medecin")
    private List<Consultation> consultations;

    @OneToMany(mappedBy = "generaliste")
    private List<DemandeExpertise> demandeEnvoyees;

    @OneToMany(mappedBy = "specialiste")
    private List<DemandeExpertise> demandesRecues;

    @OneToMany(mappedBy = "medecin")
    private List<CreneauDisponible> creneauxDisponibles;

    public Utilisateur(){
        //constructor
    }

    @PrePersist
    protected void onCreate(){
        createdAt = LocalDateTime.now();
        updatedAt = LocalDateTime.now();
    }

    @PreUpdate
    protected void onUpdate(){
        updatedAt = LocalDateTime.now();
    }

    public Long getId() {
        return id;
    }

    public String getNom() {
        return nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public String getEmail() {
        return email;
    }

    public String getMotDePasse() {
        return motDePasse;
    }

    public String getTelephone() {
        return telephone;
    }

    public Role getRole() {
        return role;
    }

    public Specialite getSpecialite() {
        return specialite;
    }

    public BigDecimal getTarif() {
        return tarif;
    }

    public Integer getDureeConsultation() {
        return dureeConsultation;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public List<Consultation> getConsultations() {
        return consultations;
    }

    public List<DemandeExpertise> getDemandeEnvoyees() {
        return demandeEnvoyees;
    }

    public List<DemandeExpertise> getDemandesRecues() {
        return demandesRecues;
    }

    public List<CreneauDisponible> getCreneauxDisponibles() {
        return creneauxDisponibles;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setMotDePasse(String motDePasse) {
        this.motDePasse = motDePasse;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public void setSpecialite(Specialite specialite) {
        this.specialite = specialite;
    }

    public void setTarif(BigDecimal tarif) {
        this.tarif = tarif;
    }

    public void setDureeConsultation(Integer dureeConsultation) {
        this.dureeConsultation = dureeConsultation;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }

    public void setConsultations(List<Consultation> consultations) {
        this.consultations = consultations;
    }

    public void setDemandeEnvoyees(List<DemandeExpertise> demandeEnvoyees) {
        this.demandeEnvoyees = demandeEnvoyees;
    }

    public void setDemandesRecues(List<DemandeExpertise> demandesRecues) {
        this.demandesRecues = demandesRecues;
    }

    public void setCreneauxDisponibles(List<CreneauDisponible> creneauxDisponibles) {
        this.creneauxDisponibles = creneauxDisponibles;
    }
}
