package com.rodrigo.foodapi.domain.service;

import com.rodrigo.foodapi.domain.exception.PhotoProductNotFoundException;
import com.rodrigo.foodapi.domain.model.PhotoProduct;
import com.rodrigo.foodapi.domain.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.InputStream;
import java.util.Optional;


@Service
public class ProductCatalogService {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private PhotoStorageService photoStorageService;

    @Transactional
    public PhotoProduct save(PhotoProduct photo, InputStream inputStream) {
        String fileName = photoStorageService.createFileName(photo.getFileName());
        String fileNameExist = null;

        Optional<PhotoProduct> photoExist = productRepository.findPhotoById(photo.getRestaurantId(), photo.getProduct().getId());

        if (photoExist.isPresent()) {
            fileNameExist = photoExist.get().getFileName();
            productRepository.delete(photoExist.get());
        }


        photo.setFileName(fileName);
        photo = productRepository.save(photo);
        productRepository.flush();

        PhotoStorageService.NewPhoto newPhoto = PhotoStorageService.NewPhoto.builder()
                .fileName(fileName)
                .contentType(photo.getContentType())
                .inputStream(inputStream)
                .build();

        photoStorageService.replace(fileNameExist, newPhoto);
        return photo;
    }

    public PhotoProduct find(Long restaurantId, Long productId) {
        return productRepository.findPhotoById(restaurantId, productId)
                .orElseThrow(() -> new PhotoProductNotFoundException(restaurantId, productId));
    }

    @Transactional
    public void delete(Long restaurantId, Long productId) {
        PhotoProduct photo = find(restaurantId, productId);

        productRepository.delete(photo);
        productRepository.flush();

        photoStorageService.remove(photo.getFileName());
    }
}
