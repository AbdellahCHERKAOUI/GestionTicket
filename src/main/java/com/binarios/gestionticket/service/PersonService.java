package com.binarios.gestionticket.service;

import com.binarios.gestionticket.dto.request.ClientDTO;
import com.binarios.gestionticket.dto.request.PersonDTO;
import com.binarios.gestionticket.dto.request.TechDTO;
import com.binarios.gestionticket.dto.request.UpdatePasswordDTO;
import com.binarios.gestionticket.dto.response.ClientResponseDTO;
import com.binarios.gestionticket.dto.response.PersonResponseDTO;
import com.binarios.gestionticket.dto.response.TechResponseDTO;
import com.binarios.gestionticket.entities.Group;
import com.binarios.gestionticket.entities.Person;
import com.binarios.gestionticket.entities.Role;
import com.binarios.gestionticket.enums.PersonRole;
import com.binarios.gestionticket.exception.DuplicateResource;
import com.binarios.gestionticket.exception.ResourceNotFoundException;
import com.binarios.gestionticket.repositories.GroupRepository;
import com.binarios.gestionticket.repositories.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.*;

@Service
//@AllArgsConstructor
public class PersonService {
    private final PersonRepository personRepository;
    private final GroupRepository groupRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;


    @Autowired
    public PersonService(PersonRepository personRepository,
                         GroupRepository groupRepository, BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.personRepository = personRepository;
        this.groupRepository = groupRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    public PersonResponseDTO createAdmin(PersonDTO personDTO) throws Exception {
        if (personRepository.findByEmail(personDTO.getEmail()).isPresent()) {
            throw new DuplicateResource(String.format("Email : %s is duplicated", personDTO.getEmail()));
        }
        Person person = new Person();
        person.setFullName(personDTO.getFullName());
        person.setPassword(bCryptPasswordEncoder.encode(personDTO.getPassword()));
        Set<Role> roles = new HashSet<>();
        roles.add(new Role("ADMIN"));
        person.setRoles(roles);
        //person.setRoles(roles);
        person.setRole(PersonRole.ADMIN);
        person.setEmail(personDTO.getEmail());
        person.setPhoneNumber(personDTO.getPhoneNumber());
        person.setBirthDate(personDTO.getBirthDate());
        person.setActive(true);

        // Generate the username automatically based on fullName and a random 4-digit number
        String generatedUsername = generateUsername(personDTO.getFullName());
        person.setUsername(generatedUsername);

        Person savedPerson = personRepository.save(person);

        // Create a new PersonDTO and set the ID and generated username
        PersonResponseDTO createdPersonDTO = new PersonResponseDTO();
        createdPersonDTO.setId(savedPerson.getId());
        createdPersonDTO.setUsername(savedPerson.getUsername());
        createdPersonDTO.setRole(savedPerson.getRole().name());
        createdPersonDTO.setEmail(savedPerson.getEmail());
        createdPersonDTO.setPhoneNumber(savedPerson.getPhoneNumber());
        createdPersonDTO.setBirthDate(savedPerson.getBirthDate());
        createdPersonDTO.setFullName(savedPerson.getFullName());
        createdPersonDTO.setActive(savedPerson.isActive());

        return createdPersonDTO;
    }

    //Username Creation Logic (fullName + 4-digits (Randomly generated))

    public String generateUsername(String fullName) {
        // Remove any spaces from the full name and convert to lowercase
        String fullNameWithoutSpace = fullName.replaceAll("\\s", "").toLowerCase();

        // Generate a random 4-digit number
        String randomNumber = String.format("%04d", new Random().nextInt(10000));

        // Combine the full name (without spaces) and random number to form the username
        return fullNameWithoutSpace + randomNumber;
    }

    public Collection<PersonResponseDTO> allUsers() {
        Collection<Person> people  = personRepository.findAll(Sort.by(Sort.Direction.DESC, "id"));;
        Collection<PersonResponseDTO> personResponseDTOS = new ArrayList<>();

        for (Person person : people) {
            PersonResponseDTO createdPersonDTO = new PersonResponseDTO();
            createdPersonDTO.setId(person.getId());
            createdPersonDTO.setUsername(person.getUsername());
            createdPersonDTO.setRole(person.getRole().name());
            createdPersonDTO.setEmail(person.getEmail());
            createdPersonDTO.setPhoneNumber(person.getPhoneNumber());
            createdPersonDTO.setBirthDate(person.getBirthDate());
            createdPersonDTO.setFullName(person.getFullName());
            createdPersonDTO.setSpecialite(person.getSpecialite());
            createdPersonDTO.setActive(person.isActive());
            //createdPersonDTO.setGroup(person.getGroup().getId());
            if (person.getGroup() != null) {
                createdPersonDTO.setGroup(person.getGroup().getId());
            } else {
                createdPersonDTO.setGroup(null);
            }
            personResponseDTOS.add(createdPersonDTO);
        }
        return personResponseDTOS;
    }

    public PersonResponseDTO editAdmin(Long id, PersonDTO personDTO) {
        Optional<Person> optionalPerson = personRepository.findById(id);
        //If the personDTO is empty we will throw an exception
        if (optionalPerson.isEmpty()) {
            throw new ResourceNotFoundException("Person with ID " + id + " not found.");
        }

        Person existingPerson = optionalPerson.get();
        // Update the person with the new data
        existingPerson.setFullName(personDTO.getFullName());
        existingPerson.setRole(PersonRole.ADMIN);
        Set<Role> roles = new HashSet<>();
        roles.add(new Role("ADMIN"));
        existingPerson.setRoles(roles);
        //existingPerson.setRoles(roles);
        existingPerson.setPassword(bCryptPasswordEncoder.encode(personDTO.getPassword()));
        existingPerson.setEmail(personDTO.getEmail());
        existingPerson.setPhoneNumber(personDTO.getPhoneNumber());
        existingPerson.setBirthDate(personDTO.getBirthDate());


        Person updatedPerson = personRepository.save(existingPerson);

        // Create a new PersonResponseDTO to give it back as a response
        PersonResponseDTO updatedPersonResponseDTO = new PersonResponseDTO();
        updatedPersonResponseDTO.setId(updatedPerson.getId());
        updatedPersonResponseDTO.setUsername(updatedPerson.getUsername());
        updatedPersonResponseDTO.setRole(updatedPerson.getRole().name());
        updatedPersonResponseDTO.setEmail(updatedPerson.getEmail());
        updatedPersonResponseDTO.setPhoneNumber(updatedPerson.getPhoneNumber());
        updatedPersonResponseDTO.setBirthDate(updatedPerson.getBirthDate());
        updatedPersonResponseDTO.setFullName(updatedPerson.getFullName());

        return updatedPersonResponseDTO;
    }

    public void deletePerson(Long id) {
        Person person = personRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException(String.format("User Id : %d is not found", id)));
        personRepository.deleteById(id);
    }

    public PersonResponseDTO getUserById(Long id) {
        Person person = personRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException(String.format("User Id : %d is not found", id)));
        PersonResponseDTO updatedPersonResponseDTO = new PersonResponseDTO();
        updatedPersonResponseDTO.setId(person.getId());
        updatedPersonResponseDTO.setUsername(person.getUsername());
        updatedPersonResponseDTO.setRole(person.getRole().name());
        updatedPersonResponseDTO.setEmail(person.getEmail());
        updatedPersonResponseDTO.setPhoneNumber(person.getPhoneNumber());
        updatedPersonResponseDTO.setBirthDate(person.getBirthDate());
        updatedPersonResponseDTO.setFullName(person.getFullName());
        updatedPersonResponseDTO.setActive(person.isActive());
        if (Objects.nonNull(person.getSpecialite())) {
            updatedPersonResponseDTO.setSpecialite(person.getSpecialite());
        }
        //updatedPersonResponseDTO.setSpecialite(person.getSpecialite());
        if (Objects.nonNull(person.getGroup())) {
            updatedPersonResponseDTO.setGroup(person.getGroup().getId());
        }

        return updatedPersonResponseDTO;
    }

    public TechResponseDTO createTech(TechDTO techDTO) {
        if (personRepository.findByEmail(techDTO.getEmail()).isPresent()){
            throw new DuplicateResource("There already a user with the same email");
        }
        Person person = new Person();
        person.setFullName(techDTO.getFullName());
        person.setPassword(bCryptPasswordEncoder.encode(techDTO.getPassword()));
        Set<Role> roles = new HashSet<>();
        roles.add(new Role("TECH"));
        person.setRoles(roles);
        //person.setRoles(roles);
        person.setRole(PersonRole.TECH);
        person.setEmail(techDTO.getEmail());
        person.setPhoneNumber(techDTO.getPhoneNumber());
        person.setBirthDate(techDTO.getBirthDate());
        person.setSpecialite(techDTO.getSpecialite());
        person.setActive(true);

        // Generate the username automatically based on fullName and a random 4-digit number
        String generatedUsername = generateUsername(techDTO.getFullName());
        person.setUsername(generatedUsername);

        Person savedPerson = personRepository.save(person);

        // Create a new PersonDTO and set the ID and generated username
        TechResponseDTO createdTechDTO = new TechResponseDTO();
        createdTechDTO.setId(savedPerson.getId());
        createdTechDTO.setUsername(savedPerson.getUsername());
        createdTechDTO.setRole(savedPerson.getRole().name());
        createdTechDTO.setEmail(savedPerson.getEmail());
        createdTechDTO.setPhoneNumber(savedPerson.getPhoneNumber());
        createdTechDTO.setBirthDate(savedPerson.getBirthDate());
        createdTechDTO.setFullName(savedPerson.getFullName());
        createdTechDTO.setSpecialite(savedPerson.getSpecialite().name());
        createdTechDTO.setActive(savedPerson.isActive());

        return createdTechDTO;
    }

    public TechResponseDTO editTech(Long id, TechDTO techDTO) {
        Person existingPerson = personRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("There is no tech with this id" + id));

        // Update the person with the new data
        existingPerson.setFullName(techDTO.getFullName());
        Set<Role> roles = new HashSet<>();
        roles.add(new Role("TECH"));
        existingPerson.setRoles(roles);
        //existingPerson.setRoles(roles);
        existingPerson.setRole(PersonRole.TECH);
        existingPerson.setEmail(techDTO.getEmail());
        existingPerson.setPhoneNumber(techDTO.getPhoneNumber());
        existingPerson.setBirthDate(techDTO.getBirthDate());
        existingPerson.setSpecialite(techDTO.getSpecialite());


        Person updatedTech = personRepository.save(existingPerson);

        // Create a new PersonResponseDTO to give it back as a response
        TechResponseDTO updatedTechResponseDTO = new TechResponseDTO();
        updatedTechResponseDTO.setId(updatedTech.getId());
        updatedTechResponseDTO.setUsername(updatedTech.getUsername());
        updatedTechResponseDTO.setRole(updatedTech.getRole().name());
        updatedTechResponseDTO.setEmail(updatedTech.getEmail());
        updatedTechResponseDTO.setPhoneNumber(updatedTech.getPhoneNumber());
        updatedTechResponseDTO.setBirthDate(updatedTech.getBirthDate());
        updatedTechResponseDTO.setFullName(updatedTech.getFullName());
        updatedTechResponseDTO.setSpecialite(updatedTech.getSpecialite().name());

        return updatedTechResponseDTO;
    }

    public ClientResponseDTO createClient(ClientDTO clientDTO) throws Exception {

        Group group = groupRepository.findById(clientDTO.getGroup()).orElseThrow(() -> new ResourceNotFoundException("There is no group with this id : " + clientDTO.getGroup()));
        if (personRepository.findByEmail(clientDTO.getEmail()).isPresent()) {
            throw new DuplicateResource("There is already an account with this email " + clientDTO.getEmail());
        }

        Person person = new Person();
        person.setFullName(clientDTO.getFullName());
        person.setPassword(bCryptPasswordEncoder.encode(clientDTO.getPassword()));
        Set<Role> roles = new HashSet<>();
        roles.add(new Role("CLIENT"));
        person.setRoles(roles);
        //person.setRoles(roles);
        person.setRole(PersonRole.CLIENT);
        person.setEmail(clientDTO.getEmail());
        person.setPhoneNumber(clientDTO.getPhoneNumber());
        person.setBirthDate(clientDTO.getBirthDate());
        person.setGroup(group);
        person.setActive(true);

        // Generate the username automatically based on fullName and a random 4-digit number
        String generatedUsername = generateUsername(clientDTO.getFullName());
        person.setUsername(generatedUsername);

        Person savedPerson = personRepository.save(person);

        // Create a new PersonDTO and set the ID and generated username
        ClientResponseDTO createdClientDTO = new ClientResponseDTO();
        createdClientDTO.setId(savedPerson.getId());
        createdClientDTO.setUsername(savedPerson.getUsername());
        createdClientDTO.setRole(savedPerson.getRole().name());
        createdClientDTO.setEmail(savedPerson.getEmail());
        createdClientDTO.setPhoneNumber(savedPerson.getPhoneNumber());
        createdClientDTO.setBirthDate(savedPerson.getBirthDate());
        createdClientDTO.setFullName(savedPerson.getFullName());
        createdClientDTO.setGroup(group.getId());
        createdClientDTO.setActive(savedPerson.isActive());

        return createdClientDTO;
    }

    public ClientResponseDTO editClient(Long id, ClientDTO clientDTO) {

        Person existingClient = personRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("There is no client with this id " + id));
        Group existingGroup = groupRepository.findById(clientDTO.getGroup()).orElseThrow(() -> new ResourceNotFoundException("There is no group with this id " + id));



        // Update the person with the new data
        existingClient.setFullName(clientDTO.getFullName());
        existingClient.setEmail(clientDTO.getEmail());
        Set<Role> roles = new HashSet<>();
        roles.add(new Role("TECH"));
        existingClient.setRoles(roles);
        //existingClient.setRoles(roles);
        existingClient.setRole(PersonRole.TECH);
        existingClient.setPhoneNumber(clientDTO.getPhoneNumber());
        existingClient.setBirthDate(clientDTO.getBirthDate());
        existingClient.setGroup(existingGroup);


        Person updatedClient = personRepository.save(existingClient);

        // Create a new PersonDTO and set the ID and generated username
        ClientResponseDTO createdClientDTO = new ClientResponseDTO();
        createdClientDTO.setId(updatedClient.getId());
        createdClientDTO.setUsername(updatedClient.getUsername());
        createdClientDTO.setRole(updatedClient.getRole().name());
        createdClientDTO.setEmail(updatedClient.getEmail());
        createdClientDTO.setPhoneNumber(updatedClient.getPhoneNumber());
        createdClientDTO.setBirthDate(updatedClient.getBirthDate());
        createdClientDTO.setFullName(updatedClient.getFullName());
        createdClientDTO.setGroup(existingGroup.getId());

        return createdClientDTO;
    }

    public PersonResponseDTO activateOrDeactivate(Long id) {
        Person person = personRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("There is no account with the id : " + id));
        person.setActive(!person.isActive());

        personRepository.save(person);
        //The personResponseDTO to return in the response
        PersonResponseDTO personResponseDTO = new PersonResponseDTO();
        personResponseDTO.setId(person.getId());
        personResponseDTO.setFullName(person.getFullName());
        personResponseDTO.setUsername(person.getUsername());
        personResponseDTO.setRole(person.getRole().name());
        personResponseDTO.setPhoneNumber(person.getPhoneNumber());
        personResponseDTO.setEmail(person.getEmail());
        personResponseDTO.setActive(person.isActive());
        personResponseDTO.setBirthDate(person.getBirthDate());

        if (person.getSpecialite() != null) {
            personResponseDTO.setSpecialite(person.getSpecialite());
        }

        if (person.getGroup() != null) {
            personResponseDTO.setGroup(person.getGroup().getId());
        }


        return personResponseDTO;

    }


    //update Password
    public void changePassword(UpdatePasswordDTO requestDTO) throws Exception {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        MyCustomUserDetails principal = (MyCustomUserDetails) authentication.getPrincipal();

        Person person = personRepository.findById(principal.getPerson().getId())
                .orElseThrow(() -> new ResourceNotFoundException("There is no person with this id"));


        if (!bCryptPasswordEncoder.matches(requestDTO.getOldPassword(), person.getPassword())) {
            throw new Exception("Old password is incorrect");
        }

        if (!requestDTO.getNewPassword().equals(requestDTO.getConfirmPassword())) {
            throw new Exception("New passwords does not match");
        }

        // Update the password
        person.setPassword(bCryptPasswordEncoder.encode(requestDTO.getNewPassword()));
        personRepository.save(person);
    }


    public boolean existByRole(PersonRole personRole) {
        Optional<Person> person = personRepository.findByRole(PersonRole.ADMIN);
        return person.isPresent();
    }

    public Collection<PersonResponseDTO> techFinder() {
        Collection<Person> people = personRepository.findAll();
        Collection<PersonResponseDTO> personResponseDTOS = new ArrayList<>();
        Collection<PersonResponseDTO> people1 = new ArrayList<>(); // Create a list for people with the "TECH" role

        for (Person person : people) {
            PersonResponseDTO createdPersonDTO = new PersonResponseDTO();
            createdPersonDTO.setId(person.getId());
            createdPersonDTO.setUsername(person.getUsername());
            createdPersonDTO.setRole(person.getRole().name());
            createdPersonDTO.setEmail(person.getEmail());
            createdPersonDTO.setPhoneNumber(person.getPhoneNumber());
            createdPersonDTO.setBirthDate(person.getBirthDate());
            createdPersonDTO.setFullName(person.getFullName());
            createdPersonDTO.setSpecialite(person.getSpecialite());
            createdPersonDTO.setActive(person.isActive());
            if (person.getGroup() != null) {
                createdPersonDTO.setGroup(person.getGroup().getId());
            } else {
                createdPersonDTO.setGroup(null);
            }
            personResponseDTOS.add(createdPersonDTO);

            // Check if the person has the "TECH" role and add them to the people1 list
            if (person.getRole() == PersonRole.TECH) {
                people1.add(createdPersonDTO);
            }
        }
       return people1;
    }
}

