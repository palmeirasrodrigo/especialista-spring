package com.rodrigo.foodapi.api.controller;

import com.rodrigo.foodapi.api.assembler.photo.PhotoProductModelAssembler;
import com.rodrigo.foodapi.api.model.request.PhotoProductRequest;
import com.rodrigo.foodapi.api.model.response.PhotoProductResponse;
import com.rodrigo.foodapi.api.openapi.controller.RestaurantProductPhotoControllerOpenApi;
import com.rodrigo.foodapi.domain.exception.EntityNotFoundException;
import com.rodrigo.foodapi.domain.model.PhotoProduct;
import com.rodrigo.foodapi.domain.model.Product;
import com.rodrigo.foodapi.domain.service.PhotoStorageService;
import com.rodrigo.foodapi.domain.service.ProductCatalogService;
import com.rodrigo.foodapi.domain.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.HttpMediaTypeNotAcceptableException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/restaurants/{restaurantId}/products/{productId}/photo")
public class RestaurantProductPhotoController implements RestaurantProductPhotoControllerOpenApi {

    @Autowired
    private ProductCatalogService catalogService;

    @Autowired
    private ProductService productService;

    @Autowired
    private PhotoStorageService photoStorageService;

    @Autowired
    private PhotoProductModelAssembler productModelAssembler;

    @Override
    @PutMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public PhotoProductResponse updatePhoto(@PathVariable Long restaurantId, @PathVariable Long productId,
                                            @Valid PhotoProductRequest photoProductRequest) throws IOException {
        Product product = productService.find(restaurantId, productId);

        MultipartFile file = photoProductRequest.getFile();

        PhotoProduct photo = new PhotoProduct();
        photo.setProduct(product);
        photo.setDescription(photoProductRequest.getDescription());
        photo.setContentType(file.getContentType());
        photo.setBulk(file.getSize());
        photo.setFileName(file.getOriginalFilename());

        PhotoProduct photoSave = catalogService.save(photo, file.getInputStream());
        return productModelAssembler.toModel(photoSave);
    }

    @Override
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public PhotoProductResponse find(@PathVariable Long restaurantId,
                                     @PathVariable Long productId) {
        PhotoProduct photoProduct = catalogService.find(restaurantId, productId);

        return productModelAssembler.toModel(photoProduct);
    }

    @Override
    @GetMapping
    public ResponseEntity<?> showPhoto(@PathVariable Long restaurantId,
                                       @PathVariable Long productId,
                                       @RequestHeader(name = "accept") String acceptHeader)
            throws HttpMediaTypeNotAcceptableException {
        try {
            PhotoProduct photoProduct = catalogService.find(restaurantId, productId);

            MediaType mediaType = MediaType.parseMediaType(photoProduct.getContentType());
            List<MediaType> mediaTypesAccept = MediaType.parseMediaTypes(acceptHeader);
            checkMediaTypeCompatibility(mediaType, mediaTypesAccept);

            PhotoStorageService.RecoveredPhoto recovered = photoStorageService.recovered(photoProduct.getFileName());

            if(recovered.hasUrl()){
                return ResponseEntity
                        .status(HttpStatus.FOUND)
                        .header(HttpHeaders.LOCATION, recovered.getUrl())
                        .build();
            }else{
                return ResponseEntity.ok()
                        .contentType(MediaType.IMAGE_JPEG)
                        .body(new InputStreamResource(recovered.getInputStream()));
            }

        }catch (EntityNotFoundException e){
            return ResponseEntity.notFound().build();
        }
    }

    @Override
    @DeleteMapping
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long restaurantId,
                       @PathVariable Long productId) {
        catalogService.delete(restaurantId, productId);
    }

    private void checkMediaTypeCompatibility(MediaType mediaTypeFoto,
                                                   List<MediaType> mediaTypesAceitas) throws HttpMediaTypeNotAcceptableException {

        boolean compativel = mediaTypesAceitas.stream()
                .anyMatch(mediaTypeAceita -> mediaTypeAceita.isCompatibleWith(mediaTypeFoto));

        if (!compativel) {
            throw new HttpMediaTypeNotAcceptableException(mediaTypesAceitas);
        }
    }

}
