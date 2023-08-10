package com.binarios.gestionticket.dto.response;

import com.binarios.gestionticket.enums.Specialite;

import java.time.LocalDate;

public class PersonResponseDTO {
    private Long id;
    private String username;
    private String role;
    private String email;
    private String phoneNumber;
    private LocalDate birthDate;
    private String fullName;
    private Specialite specialite;
    private Long group;
    private boolean active;

    public PersonResponseDTO() {
    }

    public PersonResponseDTO(Long id, String username, String role, String email, String phoneNumber, LocalDate birthDate, String fullName, Specialite specialite, Long group, boolean active) {
        this.id = id;
        this.username = username;
        this.role = role;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.birthDate = birthDate;
        this.fullName = fullName;
        this.specialite = specialite;
        this.group = group;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public LocalDate getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(LocalDate birthDate) {
        this.birthDate = birthDate;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public Specialite getSpecialite() {
        return specialite;
    }

    public void setSpecialite(Specialite specialite) {
        this.specialite = specialite;
    }

    public Long getGroup() {
        return group;
    }

    public void setGroup(Long group) {
        this.group = group;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }
}
