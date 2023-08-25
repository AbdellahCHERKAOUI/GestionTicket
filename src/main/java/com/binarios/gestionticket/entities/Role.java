package com.binarios.gestionticket.entities;

import lombok.Data;

import javax.persistence.*;

@Entity
@Table(name = "roles")
@Data
public class Role {
    @javax.persistence.Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "role_id")
    private Long Id;
    @Column(name = "role_name")
    private String name;

    public Role() {

    }

    public Role(String name) {
        this.name = name;
    }
}