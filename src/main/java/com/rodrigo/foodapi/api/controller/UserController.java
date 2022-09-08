package com.rodrigo.foodapi.api.controller;

import com.rodrigo.foodapi.api.assembler.user.UserModelAssembler;
import com.rodrigo.foodapi.api.assembler.user.UserRequestDisassembler;
import com.rodrigo.foodapi.api.model.request.PasswordRequest;
import com.rodrigo.foodapi.api.model.request.UserRequest;
import com.rodrigo.foodapi.api.model.request.UserWithPasswordRequest;
import com.rodrigo.foodapi.api.model.response.UserResponse;
import com.rodrigo.foodapi.api.openapi.controller.UserControllerOpenApi;
import com.rodrigo.foodapi.domain.model.User;
import com.rodrigo.foodapi.domain.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController implements UserControllerOpenApi {

    @Autowired
    private UserService userService;

    @Autowired
    private UserModelAssembler userModelAssembler;

    @Autowired
    private UserRequestDisassembler userRequestDisassembler;

    @Override
    @GetMapping
    public List<UserResponse> listAll() {
        return userModelAssembler.toCollectionModel(userService.listAll());
    }

    @Override
    @GetMapping("/user/{userId}")
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
    @PutMapping("/user/{userId}")
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
    @DeleteMapping("/user/{userId}")
    public void remove(@PathVariable Long userId) {
        userService.remove(userId);
    }
}
