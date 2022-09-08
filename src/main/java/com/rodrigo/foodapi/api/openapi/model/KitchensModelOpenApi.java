package com.rodrigo.foodapi.api.openapi.model;

import com.rodrigo.foodapi.api.model.response.KitchenResponse;
import io.swagger.annotations.ApiModel;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@ApiModel("CozinhasModel")
public class KitchensModelOpenApi extends PagedModelOpenApi<KitchenResponse>{

}
