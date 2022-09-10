package com.rodrigo.foodapi.api.v1.assembler.group;

import com.rodrigo.foodapi.api.v1.AlgaLinks;
import com.rodrigo.foodapi.api.v1.controller.GroupController;
import com.rodrigo.foodapi.api.v1.model.response.GroupResponse;
import com.rodrigo.foodapi.domain.model.Group;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

@Component
public class GroupModelAssembler extends RepresentationModelAssemblerSupport<Group, GroupResponse> {

    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private AlgaLinks algaLinks;

    public GroupModelAssembler() {
        super(GroupController.class, GroupResponse.class);
    }

    @Override
    public GroupResponse toModel(Group Group) {
        GroupResponse groupResponse = createModelWithId(Group.getId(), Group);
        modelMapper.map(Group, groupResponse);

        groupResponse.add(algaLinks.linkToGroups("groups"));

        groupResponse.add(algaLinks.linkToGroupPermissions(Group.getId(), "permissions"));

        return groupResponse;
    }

    @Override
    public CollectionModel<GroupResponse> toCollectionModel(Iterable<? extends Group> entities) {
        return super.toCollectionModel(entities)
                .add(algaLinks.linkToGroups());
    }
}
