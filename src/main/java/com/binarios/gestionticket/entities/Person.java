package com.binarios.gestionticket.entities;

import com.binarios.gestionticket.enums.PersonRole;
import com.binarios.gestionticket.enums.Specialite;

import javax.persistence.*;
import javax.validation.constraints.Size;

import javax.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;

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
    @Size(min = 5, max = 30, message = "Username should be between 5 and 30 characters in length")
    private String username;

    @Column(nullable = false)
    @Size(min = 10, message = "Username should be less than 10 characters in length")
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

    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH, CascadeType.DETACH}, fetch = FetchType.EAGER)
    @JoinTable(name = "users_roles",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Set<Role> roles;


}
