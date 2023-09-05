package com.binarios.gestionticket.jwt;

import com.binarios.gestionticket.service.MyCustomUserDetails;
import io.jsonwebtoken.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;
import org.springframework.security.core.GrantedAuthority;


import java.util.*;
import java.util.stream.Collectors;

@Component
public class JwtUtils {
    private static final Logger logger = LoggerFactory.getLogger(JwtUtils.class);
    @Value("${javapuzzle.app.jwtSecret}")
    private String jwtSecret; //JavaPuzzle
    @Value("${javapuzzle.app.jwtExpirationMs}")
    private int jwtExpirationMs; //86400000

    public String generateJwtToken(Authentication authentication) {
        MyCustomUserDetails myCustomUserDetailsPrincipal = (MyCustomUserDetails) authentication.getPrincipal();
        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
        List<String> roles = authorities.stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.toList());

        // Build the JWT claims
        Map<String, Object> claims = new HashMap<>();
        claims.put("roles", roles);
        claims.put("username", myCustomUserDetailsPrincipal.getPerson().getUsername());
        claims.put("fullName", myCustomUserDetailsPrincipal.getPerson().getFullName());
        claims.put("email", myCustomUserDetailsPrincipal.getPerson().getEmail());
        claims.put("phoneNumber", myCustomUserDetailsPrincipal.getPerson().getPhoneNumber());
        claims.put("sub", myCustomUserDetailsPrincipal.getPerson().getEmail());

        return Jwts.builder()
                .setSubject(myCustomUserDetailsPrincipal.getUsername())
                .setClaims(claims)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + jwtExpirationMs))
                .signWith(SignatureAlgorithm.HS512, jwtSecret)
                .compact();

    }

    public String getUserNameFromJwtToken(String token) {
        return Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(token).getBody().getSubject();
    }

    public boolean validateJwtToken(String authToken) {
        try {
            Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(authToken);
            return true;
        } catch (SignatureException e) {
            logger.error("Invalid JWT signature: {}", e.getMessage());
        } catch (MalformedJwtException e) {
            logger.error("Invalid JWT token: {}", e.getMessage());
        } catch (ExpiredJwtException e) {
            logger.error("JWT token is expired: {}", e.getMessage());
        } catch (UnsupportedJwtException e) {
            logger.error("JWT token is unsupported: {}", e.getMessage());
        } catch (IllegalArgumentException e) {
            logger.error("JWT claims string is empty: {}", e.getMessage());
        }
        return false;
    }
}