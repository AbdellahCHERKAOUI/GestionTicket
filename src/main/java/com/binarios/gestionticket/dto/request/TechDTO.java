package com.binarios.gestionticket.dto.request;

import com.binarios.gestionticket.enums.Specialite;

import java.time.LocalDate;

public class TechDTO {
    private String fullName;
    private String password;
    private String role;
    private String email;
    private String phoneNumber;
    private LocalDate birthDate;
    private Specialite specialite;

    public TechDTO() {
    }

    public TechDTO(String fullName, String password, String role, String email, String phoneNumber, LocalDate birthDate, Specialite specialite) {
        this.fullName = fullName;
        this.password = password;
        this.role = role;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.birthDate = birthDate;
        this.specialite = specialite;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
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

    public Specialite getSpecialite() {
        return specialite;
    }

    public void setSpecialite(Specialite specialite) {
        this.specialite = specialite;
    }
}
