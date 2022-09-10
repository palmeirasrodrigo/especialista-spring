package com.rodrigo.foodapi.api.v1.model.response;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.core.Relation;

@Relation(collectionRelation = "permission")
@Getter
@Setter
public class PermissionResponse extends RepresentationModel<PermissionResponse> {

    @ApiModelProperty(example = "1")
    private Long id;

    @ApiModelProperty(example = "CONSULTAR_COZINHAS")
    private String name;

    @ApiModelProperty(example = "Permite consultar cozinhas")
    private String description;
}
