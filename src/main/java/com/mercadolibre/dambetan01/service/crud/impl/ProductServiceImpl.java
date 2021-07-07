package com.mercadolibre.dambetan01.service.crud.impl;

import com.mercadolibre.dambetan01.exceptions.ApiException;
import com.mercadolibre.dambetan01.repository.ProductRepository;
import com.mercadolibre.dambetan01.service.crud.ProductService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductServiceImpl implements ProductService {

    ProductRepository productRepository;

    public ProductServiceImpl(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Override
    public boolean productExists(long productId) {
        return this.productRepository.findById(productId).isPresent();
    }

    @Override
    public void productIdsInsideBatchStockExist(List<Long> productIds) {
        productIds.stream()
                .forEach(productId -> {
                    boolean productDoesntExists = !productExists(productId);
                    if (productDoesntExists) {
                        throw new ApiException("404", "Product Doesn't exists", 404);
                    }
                });
    }

}
