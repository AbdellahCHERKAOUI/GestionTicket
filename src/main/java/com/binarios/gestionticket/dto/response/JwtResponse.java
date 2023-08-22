package com.binarios.gestionticket.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class JwtResponse {
    private String token;
    private String username;
    private String fullName;
    private String email;
    private String phoneNumber;
    private List<String> roles;
    private Long userId;
}