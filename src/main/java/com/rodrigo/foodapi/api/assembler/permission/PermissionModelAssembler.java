package com.rodrigo.foodapi.api.assembler.permission;

import com.rodrigo.foodapi.api.model.response.PermissionResponse;
import com.rodrigo.foodapi.domain.model.Permission;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.List;

@Component
public class PermissionModelAssembler {

    @Autowired
    private ModelMapper modelMapper;

    public PermissionResponse toModel(Permission permission) {
        return modelMapper.map(permission, PermissionResponse.class);
    }

    public List<PermissionResponse> toCollectionModel(Collection<Permission> states) {
        return states.stream()
                .map(this::toModel)
                .toList();
    }
}
