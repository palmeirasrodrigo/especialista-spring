package com.rodrigo.foodapi.api.v2.openapi.model;

import com.rodrigo.foodapi.api.v1.model.response.CityResponse;
import io.swagger.annotations.ApiModel;
import lombok.Data;
import org.springframework.hateoas.Links;

import java.util.List;

@ApiModel("CidadesModel")
@Data
public class CitiesModelV2OpenApi {

    private CitiesEmbeddedModelOpenApi _embedded;
    private Links _links;

    @ApiModel("CidadesEmbeddedModel")
    @Data
    public class CitiesEmbeddedModelOpenApi {

        private List<CityResponse> cities;

    }
}
