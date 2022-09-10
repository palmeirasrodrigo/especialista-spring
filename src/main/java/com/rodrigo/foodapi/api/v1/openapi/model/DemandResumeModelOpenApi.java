package com.rodrigo.foodapi.api.v1.openapi.model;

import com.rodrigo.foodapi.api.v1.model.response.DemandResumeResponse;
import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.hateoas.Links;

import java.util.List;

@ApiModel("PedidosResumoModel")
@Getter
@Setter
public class DemandResumeModelOpenApi {

    private PedidosResumoEmbeddedModelOpenApi _embedded;
    private Links _links;
    private PagedModelOpenApi page;

    @ApiModel("PedidosResumoEmbeddedModel")
    @Data
    public class PedidosResumoEmbeddedModelOpenApi {

        private List<DemandResumeResponse> pedidos;

    }
}
