package com.rodrigo.foodapi.api.controller;

import com.rodrigo.foodapi.api.assembler.group.GroupModelAssembler;
import com.rodrigo.foodapi.api.model.response.GroupResponse;
import com.rodrigo.foodapi.api.openapi.controller.UserGroupControllerOpenApi;
import com.rodrigo.foodapi.domain.model.User;
import com.rodrigo.foodapi.domain.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users/usersId/groups")
public class UserGroupController implements UserGroupControllerOpenApi {

    @Autowired
    private UserService userService;

    @Autowired
    private GroupModelAssembler groupModelAssembler;

    @Override
    @GetMapping
    public List<GroupResponse> list(@PathVariable Long userId) {
        User user = userService.find(userId);

        return groupModelAssembler.toCollectionModel(user.getGroups());
    }

    @Override
    @DeleteMapping("/{groupId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void disassociate(@PathVariable Long userId, @PathVariable Long groupId) {
        userService.disassociateGroup(userId, groupId);
    }

    @Override
    @PutMapping("/{groupId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void associate(@PathVariable Long userId, @PathVariable Long groupId) {
        userService.associateGroup(userId, groupId);
    }
}
