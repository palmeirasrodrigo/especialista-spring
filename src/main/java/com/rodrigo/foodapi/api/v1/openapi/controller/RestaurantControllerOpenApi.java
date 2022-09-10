package com.rodrigo.foodapi.api.v1.openapi.controller;

import com.fasterxml.jackson.annotation.JsonView;
import com.rodrigo.foodapi.api.exceptionhandler.Problem;
import com.rodrigo.foodapi.api.v1.model.request.RestaurantRequest;
import com.rodrigo.foodapi.api.v1.model.response.RestaurantResponse;
import com.rodrigo.foodapi.api.v1.model.response.RestaurantResumeResponse;
import com.rodrigo.foodapi.api.v1.model.view.RestaurantView;
import com.rodrigo.foodapi.api.v1.model.response.RestaurantBasicModel;
import io.swagger.annotations.*;
import org.springframework.hateoas.CollectionModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

@Api(tags = "Restaurantes")
public interface RestaurantControllerOpenApi {
    @ApiOperation(value = "Lista restaurantes", response = RestaurantBasicModel.class)
    @ApiImplicitParams({
            @ApiImplicitParam(value = "Nome da projeção de pedidos", allowableValues = "name",
                    name = "projec", paramType = "query", type = "string")
    })
    CollectionModel<RestaurantBasicModel> listAll();

    @ApiOperation(value = "Lista restaurantes", response = RestaurantBasicModel.class)
    @ApiImplicitParams({
            @ApiImplicitParam(value = "Nome da projeção de pedidos", allowableValues = "name",
                    name = "projec", paramType = "query", type = "string")
    })
    @JsonView(RestaurantView.Resume.class)
    CollectionModel<RestaurantResponse> listResume();

    @ApiOperation(value = "Lista restaurantes", hidden = true)
    @JsonView(RestaurantView.JustName.class)
    CollectionModel<RestaurantResumeResponse> listNames();

    @ApiOperation("Busca um restaurante por ID")
    @ApiResponses({
            @ApiResponse(code = 400, message = "ID do restaurante inválido", response = Problem.class),
            @ApiResponse(code = 404, message = "Restaurante não encontrado", response = Problem.class)
    })
    RestaurantResponse find(@ApiParam(value = "ID de um restaurante", example = "1", required = true) Long restaurantId);

    @ApiOperation("Cadastra um restaurante")
    @ApiResponses({
            @ApiResponse(code = 201, message = "Restaurante cadastrado"),
    })
    RestaurantResponse create(@ApiParam(name = "corpo", value = "Representação de um novo restaurante", required = true) RestaurantRequest restaurantRequest);

    @ApiOperation("Atualiza um restaurante por ID")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Restaurante atualizado"),
            @ApiResponse(code = 404, message = "Restaurante não encontrado", response = Problem.class)
    })
    RestaurantResponse update(@ApiParam(value = "ID de um restaurante", example = "1", required = true) Long restaurantId,
                              @ApiParam(name = "corpo", value = "Representação de um restaurante com os novos dados",
                                      required = true) RestaurantRequest restaurantRequest);

    @ApiOperation("Atualiza parcial por um restaurante por ID")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Restaurante atualizado"),
            @ApiResponse(code = 404, message = "Restaurante não encontrado", response = Problem.class)
    })
    RestaurantResponse partialUpdate(@ApiParam(value = "ID de um restaurante", example = "1", required = true) Long restaurantId,
                                     @RequestBody Map<String, Object> fields, HttpServletRequest request);

    @ApiOperation("Exclui um Restaurante por ID")
    @ApiResponses({
            @ApiResponse(code = 204, message = "Restaurante excluído"),
            @ApiResponse(code = 404, message = "Restaurante não encontrado", response = Problem.class)
    })
    void remove(@PathVariable Long restaurantId);

    @ApiOperation("Ativa múltiplos restaurantes")
    @ApiResponses({
            @ApiResponse(code = 204, message = "Restaurantes ativados com sucesso")
    })
    void activateMultiples(@ApiParam(name = "corpo", value = "IDs de restaurantes", required = true) List<Long> restaurantId);

    @ApiOperation("Inativa múltiplos restaurantes")
    @ApiResponses({
            @ApiResponse(code = 204, message = "Restaurantes ativados com sucesso")
    })
    void deactivationsMultiples(@ApiParam(name = "corpo", value = "IDs de restaurantes", required = true) List<Long> restaurantId);

    @ApiOperation("Ativa um restaurante por ID")
    @ApiResponses({
            @ApiResponse(code = 204, message = "Restaurante ativado com sucesso"),
            @ApiResponse(code = 404, message = "Restaurante não encontrado", response = Problem.class)
    })
    ResponseEntity<Void> active(@ApiParam(value = "ID de um restaurante", example = "1", required = true) Long restaurantId);

    @ApiOperation("Inativa um restaurante por ID")
    @ApiResponses({
            @ApiResponse(code = 204, message = "Restaurante inativado com sucesso"),
            @ApiResponse(code = 404, message = "Restaurante não encontrado", response = Problem.class)
    })
    ResponseEntity<Void>  inactive(@ApiParam(value = "ID de um restaurante", example = "1", required = true) Long restaurantId);

    @ApiOperation("Abre um restaurante por ID")
    @ApiResponses({
            @ApiResponse(code = 204, message = "Restaurante aberto com sucesso"),
            @ApiResponse(code = 404, message = "Restaurante não encontrado", response = Problem.class)
    })
    ResponseEntity<Void>  open(@ApiParam(value = "ID de um restaurante", example = "1", required = true) Long restaurantId);

    @ApiOperation("Fecha um restaurante por ID")
    @ApiResponses({
            @ApiResponse(code = 204, message = "Restaurante fechado com sucesso"),
            @ApiResponse(code = 404, message = "Restaurante não encontrado", response = Problem.class)
    })
    ResponseEntity<Void>  close(@ApiParam(value = "ID de um restaurante", example = "1", required = true) Long restaurantId);
}
