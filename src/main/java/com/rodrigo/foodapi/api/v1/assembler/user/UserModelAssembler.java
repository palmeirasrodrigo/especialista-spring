package com.rodrigo.foodapi.api.v1.assembler.user;

import com.rodrigo.foodapi.api.v1.AlgaLinks;
import com.rodrigo.foodapi.api.v1.controller.UserController;
import com.rodrigo.foodapi.api.v1.model.response.UserResponse;
import com.rodrigo.foodapi.domain.model.User;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

@Component
public class UserModelAssembler extends RepresentationModelAssemblerSupport<User, UserResponse> {

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private AlgaLinks algaLinks;

    public UserModelAssembler() {
        super(UserController.class, UserResponse.class);
    }

    @Override
    public UserResponse toModel(User user) {
        UserResponse userModel = createModelWithId(user.getId(), user);
        modelMapper.map(user, userModel);

        userModel.add(algaLinks.linkToUsers("users"));

        userModel.add(algaLinks.linkToGroupsUser(user.getId(), "groups-user"));

        return userModel;
    }

    @Override
    public CollectionModel<UserResponse> toCollectionModel(Iterable<? extends User> entities) {
        return super.toCollectionModel(entities)
                .add(algaLinks.linkToUsers());
    }
}
