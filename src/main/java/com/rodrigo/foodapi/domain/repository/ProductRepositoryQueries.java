package com.rodrigo.foodapi.domain.repository;

import com.rodrigo.foodapi.domain.model.PhotoProduct;

public interface ProductRepositoryQueries {

    PhotoProduct save(PhotoProduct photo);

    void delete(PhotoProduct photo);
}
