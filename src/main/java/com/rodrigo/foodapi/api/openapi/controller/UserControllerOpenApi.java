package com.rodrigo.foodapi.api.openapi.controller;

import com.rodrigo.foodapi.api.exceptionhandler.Problem;
import com.rodrigo.foodapi.api.model.request.PasswordRequest;
import com.rodrigo.foodapi.api.model.request.UserRequest;
import com.rodrigo.foodapi.api.model.request.UserWithPasswordRequest;
import com.rodrigo.foodapi.api.model.response.UserResponse;
import io.swagger.annotations.*;

import java.util.List;

@Api(tags = "Usuários")
public interface UserControllerOpenApi {
    @ApiOperation("Lista os usuários")
    List<UserResponse> listAll();

    @ApiOperation("Busca um usuário por ID")
    @ApiResponses({
            @ApiResponse(code = 400, message = "ID do usuário inválido", response = Problem.class),
            @ApiResponse(code = 404, message = "Usuário não encontrado", response = Problem.class)
    })
    UserResponse find(@ApiParam(value = "ID do usuário", example = "1", required = true) Long userId);

    @ApiOperation("Cadastra um usuário")
    @ApiResponses({
            @ApiResponse(code = 201, message = "Usuário cadastrado"),
    })
    UserResponse create(@ApiParam(name = "corpo", value = "Representação de um novo usuário", required = true) UserWithPasswordRequest userRequest);

    @ApiOperation("Atualiza um usuário por ID")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Usuário atualizado"),
            @ApiResponse(code = 404, message = "Usuário não encontrado", response = Problem.class)
    })
    UserResponse update(@ApiParam(value = "ID do usuário", example = "1", required = true) Long userId,
                        @ApiParam(name = "corpo", value = "Representação de um usuário com os novos dados",
                                required = true) UserRequest userRequest);

    @ApiOperation("Atualiza a senha de um usuário")
    @ApiResponses({
            @ApiResponse(code = 204, message = "Senha alterada com sucesso"),
            @ApiResponse(code = 404, message = "Usuário não encontrado", response = Problem.class)
    })
    void changePassword(@ApiParam(value = "ID do usuário", example = "1", required = true) Long userId,
                        @ApiParam(name = "corpo", value = "Representação de uma nova senha",
                                required = true) PasswordRequest passwordRequest);

    @ApiOperation("Exclui um usuário por ID")
    @ApiResponses({
            @ApiResponse(code = 204, message = "Usuário excluído"),
            @ApiResponse(code = 404, message = "Usuário não encontrado", response = Problem.class)
    })
    void remove(@ApiParam(value = "ID do usuário", example = "1", required = true) Long userId);
}
