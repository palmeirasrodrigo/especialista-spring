package com.rodrigo.foodapi.domain.exception;

public class ProductNotFoundException extends EntityNotFoundException{

    public static final String MSG_PRODUCT_NOT_FOUND = "Não existe um cadastro de produto com código %d ";

    public ProductNotFoundException(String message) {
        super(message);
    }

    public ProductNotFoundException(Long productId) {
        this(String.format(MSG_PRODUCT_NOT_FOUND, productId));
    }
}
