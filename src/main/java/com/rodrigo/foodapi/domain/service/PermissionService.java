package com.rodrigo.foodapi.domain.service;

import com.rodrigo.foodapi.domain.exception.EntityInUseException;
import com.rodrigo.foodapi.domain.exception.PermissionNotFoundException;
import com.rodrigo.foodapi.domain.model.Permission;
import com.rodrigo.foodapi.domain.repository.PermissionRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class PermissionService {

    public static final String MSG_PERMISSION_IN_USE = "Permissão de código %d não pode ser removida, pois está em uso ";
    @Autowired
    private PermissionRepository permissionRepository;

    public List<Permission> listAll() {
        return permissionRepository.findAll();
    }

    public Permission find(Long permissionId) {
        return permissionRepository.findById(permissionId).orElseThrow(
                () -> new PermissionNotFoundException(permissionId)
        );
    }

    @Transactional
    public Permission create(Permission permission) {
        return permissionRepository.save(permission);
    }

    @Transactional
    public Permission update(Long permissionId, Permission permission) {
        Permission permissionActual = find(permissionId);
        BeanUtils.copyProperties(permission, permissionActual, "id");
        return permissionRepository.save(permissionActual);
    }

    @Transactional
    public void remove(Long permissionId) {
        try {
            permissionRepository.deleteById(permissionId);
            permissionRepository.flush();
        } catch (EmptyResultDataAccessException e) {
            throw new PermissionNotFoundException(permissionId);
        } catch (DataIntegrityViolationException e) {
            throw new EntityInUseException(
                    String.format(MSG_PERMISSION_IN_USE, permissionId)
            );
        }
    }
}
