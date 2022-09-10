package com.rodrigo.foodapi.api.v1.openapi.model;

import com.rodrigo.foodapi.api.v1.model.response.FormPaymentResponse;
import io.swagger.annotations.ApiModel;
import lombok.Data;
import org.springframework.hateoas.Links;

import java.util.List;

@ApiModel("FormasPagamentoModel")
@Data
public class FormPaymentModelOpenApi {

    private FormPaymentEmbeddedModelOpenApi _embedded;
    private Links _links;

    @ApiModel("FormasPagamentoEmbeddedModel")
    @Data
    public class FormPaymentEmbeddedModelOpenApi {

        private List<FormPaymentResponse> formPayment;

    }
}
