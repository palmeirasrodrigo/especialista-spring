package com.rodrigo.foodapi.api.v1.model.response;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.core.Relation;

import java.math.BigDecimal;

@Relation(collectionRelation = "products")
@Getter
@Setter
public class ProductResponse extends RepresentationModel<ProductResponse> {

    @ApiModelProperty(example = "1")
    private Long id;

    @ApiModelProperty(example = "Espetinho de Cupim")
    private String name;

    @ApiModelProperty(example = "Acompanha farinha, mandioca e vinagrete")
    private String description;

    @ApiModelProperty(example = "12.50")
    private BigDecimal price;

    @ApiModelProperty(example = "true")
    private Boolean active;

}
