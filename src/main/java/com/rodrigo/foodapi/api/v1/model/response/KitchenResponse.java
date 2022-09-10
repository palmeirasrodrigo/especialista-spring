package com.rodrigo.foodapi.api.v1.model.response;

import com.fasterxml.jackson.annotation.JsonView;
import com.rodrigo.foodapi.api.v1.model.view.RestaurantView;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.core.Relation;

@Relation(collectionRelation = "kitchens")
@Getter
@Setter
public class KitchenResponse extends RepresentationModel<KitchenResponse> {

    @ApiModelProperty(example = "1")
    @JsonView(RestaurantView.Resume.class)
    private Long id;

    @ApiModelProperty(example = "Brasileira")
    @JsonView(RestaurantView.Resume.class)
    private String name;

}
