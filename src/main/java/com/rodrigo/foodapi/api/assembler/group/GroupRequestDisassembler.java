package com.rodrigo.foodapi.api.assembler.group;

import com.rodrigo.foodapi.api.model.request.GroupRequest;
import com.rodrigo.foodapi.domain.model.Group;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class GroupRequestDisassembler {

    @Autowired
    private ModelMapper modelMapper;

    public Group toDomainObject(GroupRequest groupRequest) {
        return modelMapper.map(groupRequest, Group.class);
    }

    public void copyToDomainObject(GroupRequest groupRequest, Group group) {
        modelMapper.map(groupRequest, group);
    }
}
