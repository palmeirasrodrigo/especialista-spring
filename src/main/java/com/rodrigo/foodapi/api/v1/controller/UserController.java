package com.rodrigo.foodapi.api.v1.controller;

import com.rodrigo.foodapi.api.v1.assembler.user.UserModelAssembler;
import com.rodrigo.foodapi.api.v1.assembler.user.UserRequestDisassembler;
import com.rodrigo.foodapi.api.v1.model.request.PasswordRequest;
import com.rodrigo.foodapi.api.v1.model.request.UserRequest;
import com.rodrigo.foodapi.api.v1.model.request.UserWithPasswordRequest;
import com.rodrigo.foodapi.api.v1.model.response.UserResponse;
import com.rodrigo.foodapi.api.v1.openapi.controller.UserControllerOpenApi;
import com.rodrigo.foodapi.domain.model.User;
import com.rodrigo.foodapi.domain.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping(path = "/v1/users", produces = MediaType.APPLICATION_JSON_VALUE)
public class UserController implements UserControllerOpenApi {

    @Autowired
    private UserService userService;

    @Autowired
    private UserModelAssembler userModelAssembler;

    @Autowired
    private UserRequestDisassembler userRequestDisassembler;

    @Override
    @GetMapping
    public CollectionModel<UserResponse> listAll() {
        return userModelAssembler.toCollectionModel(userService.listAll());
    }

    @Override
    @GetMapping("/{userId}")
    public UserResponse find(@PathVariable Long userId) {
        return userModelAssembler.toModel(userService.find(userId));
    }

    @Override
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public UserResponse create(@RequestBody @Valid UserWithPasswordRequest userRequest) {
        User user = userRequestDisassembler.toDomainObject(userRequest);

        return userModelAssembler.toModel(userService.create(user));
    }

    @Override
    @PutMapping("/{userId}")
    public UserResponse update(@PathVariable Long userId, @RequestBody @Valid UserRequest userRequest) {
        User actualUser = userService.find(userId);
        userRequestDisassembler.copyToDomainObject(userRequest, actualUser);
        return userModelAssembler.toModel(userService.create(actualUser));
    }

    @Override
    @PutMapping("/{userId}/password")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void changePassword(@PathVariable Long userId, @RequestBody @Valid PasswordRequest passwordRequest) {
        userService.changePassword(userId, passwordRequest.getCurrentPassword(), passwordRequest.getNewPassword());
    }

    @Override
    @DeleteMapping("/{userId}")
    public void remove(@PathVariable Long userId) {
        userService.remove(userId);
    }
}
