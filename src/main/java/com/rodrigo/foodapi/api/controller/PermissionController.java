package com.rodrigo.foodapi.api.controller;

import com.rodrigo.foodapi.domain.model.Permission;
import com.rodrigo.foodapi.domain.service.PermissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/permissions")
public class PermissionController {

    @Autowired
    private PermissionService permissionService;

    @GetMapping()
    public List<Permission> listAll() {
        return permissionService.listAll();
    }

    @GetMapping("/permission/{permissionId}")
    public Permission find(@PathVariable Long permissionId) {
        return permissionService.find(permissionId);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Permission create(@RequestBody Permission permission) {
        return permissionService.create(permission);
    }

    @PutMapping("/permission/{permissionId}")
    public Permission update(@PathVariable Long permissionId, @RequestBody Permission permission) {
        return permissionService.update(permissionId, permission);
    }

    @DeleteMapping("/permission/{permissionId}")
    public void remove(@PathVariable Long permissionId) {
        permissionService.remove(permissionId);
    }
}