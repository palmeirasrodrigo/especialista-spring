package com.rodrigo.foodapi.api.v1.assembler.photo;

import com.rodrigo.foodapi.api.v1.AlgaLinks;
import com.rodrigo.foodapi.api.v1.controller.RestaurantProductPhotoController;
import com.rodrigo.foodapi.api.v1.model.response.PhotoProductResponse;
import com.rodrigo.foodapi.domain.model.PhotoProduct;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

@Component
public class PhotoProductModelAssembler extends RepresentationModelAssemblerSupport<PhotoProduct, PhotoProductResponse> {


    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private AlgaLinks algaLinks;

    public PhotoProductModelAssembler() {
        super(RestaurantProductPhotoController.class, PhotoProductResponse.class);
    }

    @Override
    public PhotoProductResponse toModel(PhotoProduct photo) {
        PhotoProductResponse photoProductResponse = modelMapper.map(photo, PhotoProductResponse.class);

        photoProductResponse.add(algaLinks.linkToPhotoProduct(
                photo.getRestaurantId(), photo.getProduct().getId()));

        photoProductResponse.add(algaLinks.linkToProduct(
                photo.getRestaurantId(), photo.getProduct().getId(), "product"));

        return photoProductResponse;
    }

}
