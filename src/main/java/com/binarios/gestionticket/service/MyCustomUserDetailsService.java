package com.binarios.gestionticket.service;

import com.binarios.gestionticket.entities.Person;
import com.binarios.gestionticket.repositories.PersonRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class MyCustomUserDetailsService implements UserDetailsService {

    private final PersonRepository personRepository;

    public MyCustomUserDetailsService(PersonRepository personRepository) {
        this.personRepository = personRepository;
    }

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {

        Person person = personRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("MyCustomUserDetails not found"));

        return new MyCustomUserDetails(person);
    }
}
