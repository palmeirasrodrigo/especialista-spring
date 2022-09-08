package com.rodrigo.foodapi.api.controller;

import com.rodrigo.foodapi.api.assembler.permission.PermissionModelAssembler;
import com.rodrigo.foodapi.api.model.response.PermissionResponse;
import com.rodrigo.foodapi.api.openapi.controller.GroupPermissionControllerOpenApi;
import com.rodrigo.foodapi.domain.model.Group;
import com.rodrigo.foodapi.domain.service.GroupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/groups/{groupId}/permissions")
public class GroupPermissionController implements GroupPermissionControllerOpenApi {

    @Autowired
    private PermissionModelAssembler permissionModelAssembler;

    @Autowired
    private GroupService groupService;


    @Override
    @GetMapping
    public List<PermissionResponse> list(@PathVariable Long groupId) {
        Group group = groupService.find(groupId);
        return permissionModelAssembler.toCollectionModel(group.getPermissions());
    }

    @Override
    @DeleteMapping("/{permissionId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void disassociate(@PathVariable Long groupId, @PathVariable Long permissionId) {
        groupService.disassociatePermission(groupId, permissionId);
    }

    @Override
    @PutMapping("/{permissionId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void associate(@PathVariable Long groupId, @PathVariable Long permissionId) {
        groupService.associatePermission(groupId, permissionId);
    }
}