package com.rodrigo.foodapi.api.v1.controller;

import com.rodrigo.foodapi.api.v1.AlgaLinks;
import com.rodrigo.foodapi.api.v1.assembler.group.GroupModelAssembler;
import com.rodrigo.foodapi.api.v1.model.response.GroupResponse;
import com.rodrigo.foodapi.api.v1.openapi.controller.UserGroupControllerOpenApi;
import com.rodrigo.foodapi.domain.model.User;
import com.rodrigo.foodapi.domain.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/v1/users/usersId/groups", produces = MediaType.APPLICATION_JSON_VALUE)
public class UserGroupController implements UserGroupControllerOpenApi {

    @Autowired
    private UserService userService;

    @Autowired
    private GroupModelAssembler groupModelAssembler;

    @Autowired
    private AlgaLinks algaLinks;

    @Override
    @GetMapping
    public CollectionModel<GroupResponse> list(@PathVariable Long userId) {
        User user = userService.find(userId);

        CollectionModel<GroupResponse> groupResponse = groupModelAssembler.toCollectionModel(user.getGroups())
                .removeLinks()
                .add(algaLinks.linkToUserGroupAssociation(userId, "associate"));

        groupResponse.getContent().forEach(grupoModel -> {
            grupoModel.add(algaLinks.linkToUserGroupDisassociate(
                    userId, grupoModel.getId(), "disassociate"));
        });

        return groupResponse;
    }

    @Override
    @DeleteMapping("/{groupId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ResponseEntity<Void>  disassociate(@PathVariable Long userId, @PathVariable Long groupId) {
        userService.disassociateGroup(userId, groupId);
        return ResponseEntity.noContent().build();
    }

    @Override
    @PutMapping("/{groupId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ResponseEntity<Void> associate(@PathVariable Long userId, @PathVariable Long groupId) {
        userService.associateGroup(userId, groupId);
        return ResponseEntity.noContent().build();
    }
}
