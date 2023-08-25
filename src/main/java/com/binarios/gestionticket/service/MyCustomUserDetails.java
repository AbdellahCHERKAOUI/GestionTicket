package com.binarios.gestionticket.service;

import com.binarios.gestionticket.entities.Person;
import com.binarios.gestionticket.entities.Role;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.*;

@Getter
@Setter
public class MyCustomUserDetails implements UserDetails {

    private final Person person;

    public MyCustomUserDetails(Person person) {
        this.person = person;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
//        String role = person.getRoles().toString();
//        // Return the user's roles as authorities
//        return Collections.singleton(new SimpleGrantedAuthority(person.getRoles().toString()));

        Set<Role> roleSet = person.getRoles();
        List<SimpleGrantedAuthority> simpleGrantedAuthorityList = new ArrayList<>();
        for(Role role: roleSet){
            simpleGrantedAuthorityList.add(new SimpleGrantedAuthority(role.getName()));
        }
        return simpleGrantedAuthorityList;
//        simpleGrantedAuthorityList.add(new SimpleGrantedAuthority(person.getRole().name()));

    }

    @Override
    public String getPassword() {
        return person.getPassword();
    }

    @Override
    public String getUsername() {
        return person.getEmail();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true; // Assuming no account expiration logic
    }

    @Override
    public boolean isAccountNonLocked() {
        return true; // Assuming no account locking logic
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true; // Assuming no credentials expiration logic
    }

    @Override
    public boolean isEnabled() {
        return person.isActive();
    }
}
