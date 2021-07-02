package com.mercadolibre.dambetan01.service.crud.impl;

import com.mercadolibre.dambetan01.dtos.BatchStockDTO;
import com.mercadolibre.dambetan01.dtos.request.InboundOrderRequestDTO;
import com.mercadolibre.dambetan01.model.Product;
import com.mercadolibre.dambetan01.repository.ProductRepository;
import com.mercadolibre.dambetan01.service.crud.ProductService;

import java.util.List;

public class ProductServiceImpl implements ProductService {

    ProductRepository productRepository;

    public ProductServiceImpl(final ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Override
    public boolean productExists(long productId) {
        return productRepository.existsByProductId(productId);
    }

    @Override
    public void checkProductIdInsideBatchStock(List<Long> productIds) {
        productIds.stream()
                .forEach(productId -> {
                    boolean productDoesntExists = !productExists(productId);
                    if(productDoesntExists) {
                        throw new RuntimeException("Product Doesn't exists");
                    }
                });
    }

}
