package com.binarios.gestionticket.dto.request;

import java.time.LocalDate;

public class PersonDTO {
    private String username;
    private String password;
    private Long roleId;
    private String email;
    private String phoneNumber;
    private LocalDate birthDate;
    private String fullName;


    public PersonDTO() {
    }

    public PersonDTO(String username, String password, Long roleId, String email, String phoneNumber, LocalDate birthDate, String fullName) {
        this.username = username;
        this.password = password;
        this.roleId = roleId;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.birthDate = birthDate;
        this.fullName = fullName;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Long getRoleId() {
        return roleId;
    }

    public void setRoleId(Long roleId) {
        this.roleId = roleId;
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
}
