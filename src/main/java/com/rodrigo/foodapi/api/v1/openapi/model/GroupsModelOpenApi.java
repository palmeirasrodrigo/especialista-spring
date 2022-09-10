package com.rodrigo.foodapi.api.v1.openapi.model;

import com.rodrigo.foodapi.api.v1.model.response.GroupResponse;
import io.swagger.annotations.ApiModel;
import lombok.Data;
import org.springframework.hateoas.Links;

import java.util.List;

@ApiModel("GruposModel")
@Data
public class GroupsModelOpenApi {

    private GroupsEmbeddedModelOpenApi _embedded;
    private Links _links;

    @ApiModel("GruposEmbeddedModel")
    @Data
    public class GroupsEmbeddedModelOpenApi {

        private List<GroupResponse> groups;

    }
}
