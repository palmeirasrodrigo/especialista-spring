package com.rodrigo.foodapi.api.assembler.user;

import com.rodrigo.foodapi.api.model.response.UserResponse;
import com.rodrigo.foodapi.domain.model.User;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.List;

@Component
public class UserModelAssembler {

    @Autowired
    private ModelMapper modelMapper;

    public UserResponse toModel(User user) {
        return modelMapper.map(user, UserResponse.class);
    }

    public List<UserResponse> toCollectionModel(Collection<User> users) {
        return users.stream()
                .map(this::toModel)
                .toList();
    }
}
