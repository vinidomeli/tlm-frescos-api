package com.mercadolibre.dambetan01.service.crud.impl;

import com.mercadolibre.dambetan01.exceptions.ApiException;
import com.mercadolibre.dambetan01.model.Product;
import com.mercadolibre.dambetan01.repository.ProductRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ProductServiceImplTest {

    private final Long productId = 125125L;
    @Mock
    ProductRepository productRepository;
    @InjectMocks
    ProductServiceImpl productService;

    @Test
    void productExistsTest() {
        when(this.productRepository.findById(any())).thenReturn(Optional.of(new Product()));

        assertTrue(this.productService.productExists(this.productId));
    }

    @Test
    void productNotExistsTest() {
        when(this.productRepository.findById(any())).thenReturn(Optional.empty());

        assertFalse(this.productService.productExists(this.productId));
    }

    @Test
    void productIdsInsideBatchStockExistTest() {
        List<Long> productsIds = new ArrayList<>();
        productsIds.add(this.productId);

        when(this.productRepository.findById(any())).thenReturn(Optional.of(new Product()));

        assertDoesNotThrow(() -> {
            this.productService.productIdsInsideBatchStockExist(productsIds);
        });
    }

    @Test
    void productIdsInsideBatchStockNotExistsTest() {
        List<Long> productsIds = new ArrayList<>();
        productsIds.add(this.productId);

        when(this.productRepository.findById(any())).thenReturn(Optional.empty());

        assertThrows(ApiException.class, () -> {
            this.productService.productIdsInsideBatchStockExist(productsIds);
        });
    }
}