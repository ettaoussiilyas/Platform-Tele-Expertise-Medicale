package com.example.platform_tele_expertise_medicale.model;

import com.example.platform_tele_expertise_medicale.model.enums.StatuConsultation;
import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "consultations")
public class Consultation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "patient_id", nullable = false)
    private Patient patient;

    @ManyToOne
    @JoinColumn(name = "medecin_id", nullable = false)
    private Utilisateur medecin;

    @Column(name = "date_consultation", nullable = false)
    private LocalDateTime dateConsultation;

    @Column(name = "duree_minutes")
    private Integer dureeMinutes = 30;

    @Column(precision = 10, scale = 2)
    private BigDecimal tarif;

    @Enumerated(EnumType.STRING)
    private StatuConsultation statut = StatuConsultation.PROGRAMMEE;

    @Column(columnDefinition = "TEXT")
    private String notes;

    @Column(columnDefinition = "TEXT")
    private String diagnostic;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @OneToMany(mappedBy = "consultation")
    private List<DemandeExpertise> demandesExpertises;

    @OneToMany(mappedBy = "consultation")
    private List<DossierMedical> dossiersMedicaux;

    public Consultation() {}

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Patient getPatient() { return patient; }
    public void setPatient(Patient patient) { this.patient = patient; }

    public Utilisateur getMedecin() { return medecin; }
    public void setMedecin(Utilisateur medecin) { this.medecin = medecin; }

    public LocalDateTime getDateConsultation() { return dateConsultation; }
    public void setDateConsultation(LocalDateTime dateConsultation) { this.dateConsultation = dateConsultation; }

    public Integer getDureeMinutes() { return dureeMinutes; }
    public void setDureeMinutes(Integer dureeMinutes) { this.dureeMinutes = dureeMinutes; }

    public BigDecimal getTarif() { return tarif; }
    public void setTarif(BigDecimal tarif) { this.tarif = tarif; }

    public StatuConsultation getStatut() { return statut; }
    public void setStatut(StatuConsultation statut) { this.statut = statut; }

    public String getNotes() { return notes; }
    public void setNotes(String notes) { this.notes = notes; }

    public String getDiagnostic() { return diagnostic; }
    public void setDiagnostic(String diagnostic) { this.diagnostic = diagnostic; }

    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }

    public List<DemandeExpertise> getDemandesExpertises() { return demandesExpertises; }
    public void setDemandesExpertises(List<DemandeExpertise> demandesExpertises) { this.demandesExpertises = demandesExpertises; }

    public List<DossierMedical> getDossiersMedicaux() { return dossiersMedicaux; }
    public void setDossiersMedicaux(List<DossierMedical> dossiersMedicaux) { this.dossiersMedicaux = dossiersMedicaux; }
}
