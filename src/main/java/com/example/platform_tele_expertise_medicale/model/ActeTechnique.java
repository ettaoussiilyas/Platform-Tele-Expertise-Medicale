package com.example.platform_tele_expertise_medicale.model;

import jakarta.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name = "actes_techniques")
public class ActeTechnique {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String nom;

    @Column(columnDefinition = "TEXT")
    private String description;

    @Column(precision = 10, scale = 2, nullable = false)
    private BigDecimal tarif;

    @Column(name = "duree_minutes")
    private Integer dureeMinutes;

    public ActeTechnique() {}

    public ActeTechnique(String nom, String description, BigDecimal tarif, Integer dureeMinutes) {
        this.nom = nom;
        this.description = description;
        this.tarif = tarif;
        this.dureeMinutes = dureeMinutes;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getNom() { return nom; }
    public void setNom(String nom) { this.nom = nom; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public BigDecimal getTarif() { return tarif; }
    public void setTarif(BigDecimal tarif) { this.tarif = tarif; }

    public Integer getDureeMinutes() { return dureeMinutes; }
    public void setDureeMinutes(Integer dureeMinutes) { this.dureeMinutes = dureeMinutes; }
}