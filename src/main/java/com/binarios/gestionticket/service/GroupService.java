package com.binarios.gestionticket.service;

import com.binarios.gestionticket.dto.request.GroupDTO;
import com.binarios.gestionticket.dto.response.GroupResponseDTO;
import com.binarios.gestionticket.entities.Group;
import com.binarios.gestionticket.repositories.GroupRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;

@Service
public class GroupService {
    private final GroupRepository groupRepository;

    @Autowired
    public GroupService(GroupRepository groupRepository) {
        this.groupRepository = groupRepository;
    }

    //Create
    public GroupResponseDTO createGroup(GroupDTO groupDTO) {
        Group group = new Group();
        group.setName(groupDTO.getName());
        group.setDescription(groupDTO.getDescription());

        Group savedGroup = groupRepository.save(group);

        // Create a new GroupResponseDTO and set the ID
        GroupResponseDTO groupResponseDTO = new GroupResponseDTO();
        groupResponseDTO.setId(savedGroup.getId());
        groupResponseDTO.setName(savedGroup.getName());
        groupResponseDTO.setDescription(savedGroup.getDescription());

        return groupResponseDTO;
    }

    //Show
    public Collection<Group> allGroups(){
        return groupRepository.findAll();
    }

    //Update
    public GroupResponseDTO editGroup(Long groupId, GroupDTO groupDTO) {
        // Check if the group with the given groupId exists
        Group existingGroup = groupRepository.findById(groupId)
                .orElseThrow(() -> new EntityNotFoundException("Group not found with ID: " + groupId));

        // Update the group with the new data
        existingGroup.setName(groupDTO.getName());
        existingGroup.setDescription(groupDTO.getDescription());

        Group editedGroup = groupRepository.save(existingGroup);

        /* Create a new GroupResponseDTO and set the ID, name, and description */
        GroupResponseDTO editedGroupResponseDTO = new GroupResponseDTO();
        editedGroupResponseDTO.setId(editedGroup.getId());
        editedGroupResponseDTO.setName(editedGroup.getName());
        editedGroupResponseDTO.setDescription(editedGroup.getDescription());

        return editedGroupResponseDTO;
    }

    public void deleteGroup(Long id) {
        groupRepository.deleteById(id);
    }

    public GroupResponseDTO getGroupById(Long id) {
        Group existingGroup = groupRepository.findById(id).get();
        GroupResponseDTO editedGroupResponseDTO = new GroupResponseDTO();
        editedGroupResponseDTO.setId(existingGroup.getId());
        editedGroupResponseDTO.setName(existingGroup.getName());
        editedGroupResponseDTO.setDescription(existingGroup.getDescription());

        return editedGroupResponseDTO;
    }
}
