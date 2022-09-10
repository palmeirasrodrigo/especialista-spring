package com.rodrigo.foodapi.api.v1.model.response;

import com.fasterxml.jackson.annotation.JsonView;
import com.rodrigo.foodapi.api.v1.model.view.RestaurantView;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.core.Relation;

@Relation(collectionRelation = "restaurants")
@Setter
@Getter
public class RestaurantResumeResponse extends RepresentationModel<RestaurantResumeResponse> {

    @ApiModelProperty(example = "1")
    @JsonView({RestaurantView.Resume.class, RestaurantView.JustName.class})
    private Long id;

    @ApiModelProperty(example = "Thai Gourmet")
    @JsonView({RestaurantView.Resume.class, RestaurantView.JustName.class})
    private String name;

}
