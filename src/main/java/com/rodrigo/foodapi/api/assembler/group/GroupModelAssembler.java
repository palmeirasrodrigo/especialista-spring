package com.rodrigo.foodapi.api.assembler.group;

import com.rodrigo.foodapi.api.model.response.GroupResponse;
import com.rodrigo.foodapi.domain.model.Group;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class GroupModelAssembler {

    @Autowired
    private ModelMapper modelMapper;

    public GroupResponse toModel(Group group) {
        return modelMapper.map(group, GroupResponse.class);
    }

    public List<GroupResponse> toCollectionModel(List<Group> groups) {
        return groups.stream()
                .map(this::toModel)
                .toList();
    }
}
