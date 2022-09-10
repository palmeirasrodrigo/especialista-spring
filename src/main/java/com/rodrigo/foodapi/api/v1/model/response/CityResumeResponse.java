package com.rodrigo.foodapi.api.v1.model.response;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.core.Relation;

@Relation(collectionRelation = "cities")
@Getter
@Setter
public class CityResumeResponse extends RepresentationModel<CityResumeResponse> {

    @ApiModelProperty(example = "1")
    private Long id;

    @ApiModelProperty(example = "Uberlândia")
    private String name;

    @ApiModelProperty(example = "Minas Gerais")
    private String state;
}
