package com.rodrigo.foodapi.api.v1.assembler.permission;

import com.rodrigo.foodapi.api.v1.AlgaLinks;
import com.rodrigo.foodapi.api.v1.model.response.PermissionResponse;
import com.rodrigo.foodapi.domain.model.Permission;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

@Component
public class PermissionModelAssembler implements RepresentationModelAssembler<Permission, PermissionResponse> {

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private AlgaLinks algaLinks;

    @Override
    public PermissionResponse toModel(Permission permission) {
        PermissionResponse permissionResponse = modelMapper.map(permission, PermissionResponse.class);
        return permissionResponse;
    }

    @Override
    public CollectionModel<PermissionResponse> toCollectionModel(Iterable<? extends Permission> entities) {
        return RepresentationModelAssembler.super.toCollectionModel(entities)
                .add(algaLinks.linkToPermission());
    }
}
