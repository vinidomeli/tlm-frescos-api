package com.mercadolibre.dambetan01.service.crud.impl;

import com.mercadolibre.dambetan01.dtos.BatchStockDTO;
import com.mercadolibre.dambetan01.dtos.request.InboundOrderRequestDTO;
import com.mercadolibre.dambetan01.model.Product;
import com.mercadolibre.dambetan01.repository.ProductRepository;
import com.mercadolibre.dambetan01.service.crud.ProductService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductServiceImpl implements ProductService {

    ProductRepository productRepository;

    public ProductServiceImpl(final ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Override
    public boolean productExists(long productId) {
        return productRepository.findById(productId).isPresent();
    }

    @Override
    public void productIdsInsideBatchStockExist(List<Long> productIds) {
        productIds.stream()
                .forEach(productId -> {
                    boolean productDoesntExists = !productExists(productId);
                    if(productDoesntExists) {
                        throw new RuntimeException("Product Doesn't exists");
                    }
                });
    }

}
