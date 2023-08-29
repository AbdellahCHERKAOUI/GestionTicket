package com.binarios.gestionticket.controller;

import com.binarios.gestionticket.dto.request.LoginRequest;
import com.binarios.gestionticket.dto.response.JwtResponse;
import com.binarios.gestionticket.entities.Person;
import com.binarios.gestionticket.exception.AccountDisabledException;
import com.binarios.gestionticket.exception.ExceptionResponse;
import com.binarios.gestionticket.jwt.JwtUtils;
import com.binarios.gestionticket.repositories.PersonRepository;
import com.binarios.gestionticket.service.MyCustomUserDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    JwtUtils jwtUtils;
    @Autowired
    private PersonRepository personRepository;

    @PostMapping("/token")
    public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {
        try {
            Person person = personRepository.findByEmail(loginRequest.getUsername()).orElseThrow(() -> new BadCredentialsException("Invalid username or password."));
            if (!person.isActive()) {
                throw new AccountDisabledException("The account is disabled.");
            }
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));

            SecurityContextHolder.getContext().setAuthentication(authentication);
            String jwt = jwtUtils.generateJwtToken(authentication);

            MyCustomUserDetails userDetails = (MyCustomUserDetails) authentication.getPrincipal();



            List<String> roles = userDetails.getAuthorities()
                    .stream().map(GrantedAuthority::getAuthority)
                    .collect(Collectors.toList());

            return ResponseEntity.ok(new JwtResponse(jwt,
                    userDetails.getUsername(), userDetails.getPerson().getFullName(), userDetails.getPerson().getEmail(), userDetails.getPerson().getPhoneNumber(),
                    roles, userDetails.getPerson().getId()));
        } catch (BadCredentialsException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(
                    new ExceptionResponse(HttpStatus.UNAUTHORIZED.value(), "Bad Credentials", "Invalid username or password."));
        }  catch (AccountDisabledException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(
                    new ExceptionResponse(HttpStatus.UNAUTHORIZED.value(), "Account Disabled", e.getMessage()));
//        } catch (Exception e) {
//            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(
//                    new ExceptionResponse(HttpStatus.INTERNAL_SERVER_ERROR.value(), "Internal Server Error", "An error occurred while processing the request."));
//        }
        }
    }
}
