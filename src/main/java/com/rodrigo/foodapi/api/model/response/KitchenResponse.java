package com.rodrigo.foodapi.api.model.response;

import com.fasterxml.jackson.annotation.JsonView;
import com.rodrigo.foodapi.api.model.view.RestaurantView;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class KitchenResponse {

    @ApiModelProperty(example = "1")
    @JsonView(RestaurantView.Resume.class)
    private Long id;

    @ApiModelProperty(example = "Brasileira")
    @JsonView(RestaurantView.Resume.class)
    private String name;

}
