package com.rodrigo.foodapi.api.v1.model.response;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.core.Relation;

@ApiModel(value = "Cidade", description = "Representa uma cidade")
@Relation(collectionRelation = "cities")
@Getter
@Setter
public class CityResponse extends RepresentationModel<CityResponse> {

    @ApiModelProperty(value = "ID da cidade", example = "1")
    private Long id;

    @ApiModelProperty(example = "Salvador")
    private String name;

    private StateResponse state;
}
