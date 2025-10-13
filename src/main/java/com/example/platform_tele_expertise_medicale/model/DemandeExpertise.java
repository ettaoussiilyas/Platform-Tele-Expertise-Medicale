package com.example.platform_tele_expertise_medicale.model;

import com.example.platform_tele_expertise_medicale.model.enums.Priorite;
import com.example.platform_tele_expertise_medicale.model.enums.StatutDemande;
import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "demandes_expertises")
public class DemandeExpertise {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "consultation_id", nullable = false)
    private Consultation consultation;

    @ManyToOne
    @JoinColumn(name = "generaliste_id", nullable = false)
    private Utilisateur generaliste;

    @ManyToOne
    @JoinColumn(name = "specialiste_id", nullable = false)
    private Utilisateur specialiste;

    @ManyToOne
    @JoinColumn(name = "specialite_id", nullable = false)
    private Specialite specialite;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String question;

    @Column(columnDefinition = "TEXT")
    private String reponse;

    @Enumerated(EnumType.STRING)
    private Priorite priorite = Priorite.NORMALE;

    @Enumerated(EnumType.STRING)
    private StatutDemande statut = StatutDemande.EN_ATTENTE;

    @Column(name = "date_creation")
    private LocalDateTime dateCreation;

    @Column(name = "date_reponse")
    private LocalDateTime dateReponse;

    // Constructors
    public DemandeExpertise() {}

    @PrePersist
    protected void onCreate() {
        dateCreation = LocalDateTime.now();
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Consultation getConsultation() { return consultation; }
    public void setConsultation(Consultation consultation) { this.consultation = consultation; }

    public Utilisateur getGeneraliste() { return generaliste; }
    public void setGeneraliste(Utilisateur generaliste) { this.generaliste = generaliste; }

    public Utilisateur getSpecialiste() { return specialiste; }
    public void setSpecialiste(Utilisateur specialiste) { this.specialiste = specialiste; }

    public Specialite getSpecialite() { return specialite; }
    public void setSpecialite(Specialite specialite) { this.specialite = specialite; }

    public String getQuestion() { return question; }
    public void setQuestion(String question) { this.question = question; }

    public String getReponse() { return reponse; }
    public void setReponse(String reponse) { this.reponse = reponse; }

    public Priorite getPriorite() { return priorite; }
    public void setPriorite(Priorite priorite) { this.priorite = priorite; }

    public StatutDemande getStatut() { return statut; }
    public void setStatut(StatutDemande statut) { this.statut = statut; }

    public LocalDateTime getDateCreation() { return dateCreation; }
    public void setDateCreation(LocalDateTime dateCreation) { this.dateCreation = dateCreation; }

    public LocalDateTime getDateReponse() { return dateReponse; }
    public void setDateReponse(LocalDateTime dateReponse) { this.dateReponse = dateReponse; }
}
