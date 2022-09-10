package com.rodrigo.foodapi.api.v2.model.response;

import com.rodrigo.foodapi.api.v1.model.response.StateResponse;
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
public class CityResponseV2 extends RepresentationModel<CityResponseV2> {

    @ApiModelProperty(value = "ID da cidade", example = "1")
    private Long cityId;

    @ApiModelProperty(example = "Salvador")
    private String cityName;

    private Long stateId;
    private String stateName;

}
