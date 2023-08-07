package com.binarios.gestionticket.controller;

import com.binarios.gestionticket.dto.request.GroupDTO;
import com.binarios.gestionticket.dto.response.GroupResponseDTO;
import com.binarios.gestionticket.entities.Group;
import com.binarios.gestionticket.service.GroupService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

@RestController
@RequestMapping("/api")
public class GroupController {
    private final GroupService groupService;

    public GroupController(GroupService groupService) {
        this.groupService = groupService;
    }

    //Group (CRUD)
    @GetMapping("/group")
    public ResponseEntity<Collection<Group>> showGroups(){
        return new ResponseEntity<>(groupService.allGroups(), HttpStatus.OK);
    }

    //Get Group by id
    @GetMapping("/group/{id}")
    public ResponseEntity<GroupResponseDTO> showGroup(@PathVariable Long id, @RequestBody GroupDTO groupDTO) {

        return new ResponseEntity<>(groupService.getGroupById(id), HttpStatus.OK);
    }

    @PostMapping("/group/create")
    public ResponseEntity<GroupResponseDTO> createGroup(@RequestBody GroupDTO groupDTO) {
        GroupResponseDTO createdGroupResponseDTO = groupService.createGroup(groupDTO);
        return new ResponseEntity<>(createdGroupResponseDTO, HttpStatus.CREATED);
    }

    @PutMapping("/group/edit/{id}")
    public ResponseEntity<GroupResponseDTO> updateGroup(@PathVariable Long id, @RequestBody GroupDTO groupDTO) {
        GroupResponseDTO editedGroupResponseDTO = groupService.editGroup(id, groupDTO);
        return new ResponseEntity<>(editedGroupResponseDTO, HttpStatus.OK);
    }

    @DeleteMapping ("/group/delete/{id}")
    public ResponseEntity<String> deleteGroup(@PathVariable Long id) {
        groupService.deleteGroup(id);
        return new ResponseEntity<>("Group  number "+id+" deleted successfully", HttpStatus.OK);
    }
}
