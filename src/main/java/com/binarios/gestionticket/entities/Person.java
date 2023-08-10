package com.binarios.gestionticket.entities;

import com.binarios.gestionticket.enums.PersonRole;
import com.binarios.gestionticket.enums.Specialite;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Person {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String username;

    @Column(nullable = false)
    private String password;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private PersonRole role;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String phoneNumber;

    @Column(nullable = false)
    private LocalDate birthDate;

    @Column(nullable = false)
    private String fullName;

    @Column(nullable = false)
    private boolean active;

    @Enumerated(EnumType.STRING)
    private Specialite specialite;

    @OneToMany
    @JoinColumn(name = "ticket_id")
    private List<Ticket> tickets;

    @ManyToOne
    @JoinColumn(name = "group_id")
    private Group group;


}
