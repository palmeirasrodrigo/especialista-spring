package com.rodrigo.foodapi.api.v1.assembler.user;

import com.rodrigo.foodapi.api.v1.model.request.UserRequest;
import com.rodrigo.foodapi.domain.model.User;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class UserRequestDisassembler {

    @Autowired
    private ModelMapper modelMapper;

    public User toDomainObject(UserRequest userRequest) {
        return modelMapper.map(userRequest, User.class);
    }

    public void copyToDomainObject(UserRequest userRequest, User state) {
        modelMapper.map(userRequest, state);
    }
}
