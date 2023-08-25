package com.binarios.gestionticket.controller;

import com.binarios.gestionticket.service.MyCustomUserDetails;
import com.binarios.gestionticket.dto.request.LoginRequest;
import com.binarios.gestionticket.dto.response.JwtResponse;
import com.binarios.gestionticket.exception.ResourceNotFoundException;
import com.binarios.gestionticket.jwt.JwtUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/auth")

public class AuthController {


    @Autowired
    AuthenticationManager authenticationManager;
    @Autowired
    JwtUtils jwtUtils;

    @PostMapping("/token")
    public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {
        try{
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));

            SecurityContextHolder.getContext().setAuthentication(authentication);
            String jwt = jwtUtils.generateJwtToken(authentication);

            MyCustomUserDetails myCustomUserDetailsDetails = (MyCustomUserDetails) authentication.getPrincipal();
            if (!myCustomUserDetailsDetails.isEnabled()){
                throw new ResourceNotFoundException("The account is disabled");
            }
            List<String> roles = myCustomUserDetailsDetails.getAuthorities()
                    .stream().map(GrantedAuthority::getAuthority)
                    .collect(Collectors.toList());
            return ResponseEntity.ok(new JwtResponse(jwt,
                    myCustomUserDetailsDetails.getUsername(), myCustomUserDetailsDetails.getPerson().getFullName(), myCustomUserDetailsDetails.getPerson().getEmail(), myCustomUserDetailsDetails.getPerson().getPhoneNumber(),
                    roles, myCustomUserDetailsDetails.getPerson().getId()));
        }catch (Exception e){
            throw new ResourceNotFoundException("username or password incorrect ");
        }
    }
}