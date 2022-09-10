package com.rodrigo.foodapi.api.v2.openapi.model;

import com.rodrigo.foodapi.api.v1.model.response.KitchenResponse;
import com.rodrigo.foodapi.api.v1.openapi.model.PagedModelOpenApi;
import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.hateoas.Links;

import java.util.List;

@ApiModel("CozinhasModel")
@Setter
@Getter
public class KitchensModelV2OpenApi {

    private KitchensEmbeddedModelOpenApi _embedded;
    private Links _links;
    private PagedModelOpenApi page;

    @ApiModel("CozinhasEmbeddedModel")
    @Data
    public class KitchensEmbeddedModelOpenApi {

        private List<KitchenResponse> kitchens;

    }
}
