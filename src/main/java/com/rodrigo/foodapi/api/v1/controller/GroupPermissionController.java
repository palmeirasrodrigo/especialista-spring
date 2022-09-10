package com.rodrigo.foodapi.api.v1.controller;

import com.rodrigo.foodapi.api.v1.AlgaLinks;
import com.rodrigo.foodapi.api.v1.assembler.permission.PermissionModelAssembler;
import com.rodrigo.foodapi.api.v1.model.response.PermissionResponse;
import com.rodrigo.foodapi.api.v1.openapi.controller.GroupPermissionControllerOpenApi;
import com.rodrigo.foodapi.domain.model.Group;
import com.rodrigo.foodapi.domain.service.GroupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/v1/groups/{groupId}/permissions",produces = MediaType.APPLICATION_JSON_VALUE)
public class GroupPermissionController implements GroupPermissionControllerOpenApi {

    @Autowired
    private PermissionModelAssembler permissionModelAssembler;

    @Autowired
    private GroupService groupService;

    @Autowired
    private AlgaLinks algaLinks;


    @Override
    @GetMapping
    public CollectionModel<PermissionResponse> list(@PathVariable Long groupId) {
        Group group = groupService.find(groupId);
        CollectionModel<PermissionResponse> permissionResponse
                = permissionModelAssembler.toCollectionModel(group.getPermissions())
                .removeLinks()
                .add(algaLinks.linkToGroupPermissions(groupId))
                .add(algaLinks.linkToGroupPermissionAssociation(groupId, "association"));

        permissionResponse.getContent().forEach(permissionModel -> {
            permissionModel.add(algaLinks.linkToGroupPermissionDisassociation(
                    groupId, permissionModel.getId(), "disassociate"));
        });

        return permissionResponse;
    }

    @Override
    @DeleteMapping("/{permissionId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ResponseEntity<Void> disassociate(@PathVariable Long groupId, @PathVariable Long permissionId) {
        groupService.disassociatePermission(groupId, permissionId);
        return ResponseEntity.noContent().build();
    }

    @Override
    @PutMapping("/{permissionId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ResponseEntity<Void> associate(@PathVariable Long groupId, @PathVariable Long permissionId) {
        groupService.associatePermission(groupId, permissionId);
        return ResponseEntity.noContent().build();
    }
}