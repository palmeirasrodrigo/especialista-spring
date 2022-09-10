package com.rodrigo.foodapi.api.v2.model.response;

import com.fasterxml.jackson.annotation.JsonView;
import com.rodrigo.foodapi.api.v1.model.view.RestaurantView;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.core.Relation;

@ApiModel("CozinhaModel")
@Relation(collectionRelation = "kitchens")
@Getter
@Setter
public class KitchenResponseV2 extends RepresentationModel<KitchenResponseV2> {

    @ApiModelProperty(example = "1")
    private Long kitchenId;

    @ApiModelProperty(example = "Brasileira")
    private String kitchenName;

}
