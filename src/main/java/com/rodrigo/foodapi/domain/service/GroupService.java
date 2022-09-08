package com.rodrigo.foodapi.domain.service;

import com.rodrigo.foodapi.domain.exception.EntityInUseException;
import com.rodrigo.foodapi.domain.exception.GroupNotFoundException;
import com.rodrigo.foodapi.domain.model.Group;
import com.rodrigo.foodapi.domain.model.Permission;
import com.rodrigo.foodapi.domain.repository.GroupRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class GroupService {

    public static final String MSG_GROUP_IN_USE = "Grupo de código %d não pode ser removida, pois está em uso ";
    @Autowired
    private GroupRepository groupRepository;

    @Autowired
    private PermissionService permissionService;

    public List<Group> listAll() {
        return groupRepository.findAll();
    }

    public Group find(Long groupId) {
        return groupRepository.findById(groupId).orElseThrow(() -> new GroupNotFoundException(groupId)
        );
    }

    @Transactional
    public Group create(Group group) {
        return groupRepository.save(group);
    }

    @Transactional
    public void remove(Long groupId) {
        try {
            groupRepository.deleteById(groupId);
            groupRepository.flush();
        } catch (EmptyResultDataAccessException e) {
            throw new GroupNotFoundException(groupId);
        } catch (DataIntegrityViolationException e) {
            throw new EntityInUseException(
                    String.format(MSG_GROUP_IN_USE, groupId)
            );
        }
    }

    @Transactional
    public void disassociatePermission(Long groupId, Long permissionId) {
        Group group = find(groupId);
        Permission permission = permissionService.find(permissionId);
        group.removePermission(permission);
    }

    @Transactional
    public void associatePermission(Long groupId, Long permissionId) {
        Group group = find(groupId);
        Permission permission = permissionService.find(permissionId);
        group.addPermission(permission);
    }
}
