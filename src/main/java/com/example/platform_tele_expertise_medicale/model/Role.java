package com.example.platform_tele_expertise_medicale.model;

import com.example.platform_tele_expertise_medicale.model.enums.RoleName;
import com.example.platform_tele_expertise_medicale.model.Utilisateur;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;


@Entity
@Table(name= "roles")
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Enumerated(EnumType.STRING)
    @Column(name = "role_name", nullable = false, unique = true)
    private RoleName roleName;

    @OneToMany(mappedBy = "role")
    private List<Utilisateur> utilisateurs = new ArrayList<>();

    public Role(){

    }

    public Role(RoleName roleName){
        this.roleName = roleName;
    }

    public Integer getId() {
        return id;
    }

    public RoleName getRoleName() {
        return roleName;
    }

    public List<Utilisateur> getUtilisateurs() {
        return utilisateurs;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setRoleName(RoleName roleName) {
        this.roleName = roleName;
    }

    public void setUtilisateurs(List<Utilisateur> utilisateurs) {
        this.utilisateurs = utilisateurs;
    }
}
