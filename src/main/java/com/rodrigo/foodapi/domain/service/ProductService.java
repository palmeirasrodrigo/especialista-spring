package com.rodrigo.foodapi.domain.service;

import com.rodrigo.foodapi.domain.exception.EntityInUseException;
import com.rodrigo.foodapi.domain.exception.ProductNotFoundException;
import com.rodrigo.foodapi.domain.model.Product;
import com.rodrigo.foodapi.domain.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ProductService {

    public static final String MSG_PRODUCT_IN_USE = "Produto de código %d não pode ser removida, pois está em uso ";
    @Autowired
    private ProductRepository productRepository;

    public List<Product> listAll() {
        return productRepository.findAll();
    }

    public Product find(Long restaurantId, Long productId) {
        return productRepository.findById(restaurantId, productId).orElseThrow(() -> new ProductNotFoundException(productId));
    }

    @Transactional
    public Product create(Product product) {
        return productRepository.save(product);
    }

    @Transactional
    public void remove(Long productPaymentId) {
        try {
            productRepository.deleteById(productPaymentId);
            productRepository.flush();
        } catch (EmptyResultDataAccessException e) {
            throw new ProductNotFoundException(productPaymentId);
        } catch (DataIntegrityViolationException e) {
            throw new EntityInUseException(
                    String.format(MSG_PRODUCT_IN_USE, productPaymentId)
            );
        }
    }
}
