package com.rodrigo.foodapi.api.assembler.photo;

import com.rodrigo.foodapi.api.model.response.PhotoProductResponse;
import com.rodrigo.foodapi.domain.model.PhotoProduct;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class PhotoProductModelAssembler {

    @Autowired
    private ModelMapper modelMapper;

    public PhotoProductResponse toModel(PhotoProduct photoProduct) {
        return modelMapper.map(photoProduct, PhotoProductResponse.class);
    }
}
