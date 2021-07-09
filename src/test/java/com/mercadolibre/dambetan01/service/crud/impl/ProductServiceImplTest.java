package com.mercadolibre.dambetan01.service.crud.impl;

import com.mercadolibre.dambetan01.exceptions.ApiException;
import com.mercadolibre.dambetan01.exceptions.NotFoundException;
import com.mercadolibre.dambetan01.model.Product;
import com.mercadolibre.dambetan01.model.PurchaseOrder;
import com.mercadolibre.dambetan01.model.enums.ProductType;
import com.mercadolibre.dambetan01.repository.ProductRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.*;

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

    @Test
    void listAllProducts() {
        Product product = new Product();
        List<Product> productList = Arrays.asList(product);

        when(productRepository.findAll()).thenReturn(productList);
        assertDoesNotThrow(() -> {
            productService.listAllProducts();
        });
    }

    @Test
    void listAllProductsIsEmpty() {
        List<Product> productList = new ArrayList<>();

        when(productRepository.findAll()).thenReturn(productList);
        assertThrows(ApiException.class, () -> {
            productService.listAllProducts();
        });
    }

    @Test
    void listProductsByCategory() {
        Product product = new Product();
        List<Product> productList = Arrays.asList(product);
        when(productRepository.findProductByType(any())).thenReturn(productList);
        assertDoesNotThrow(() -> {
            productService.listProductsByCategory(any());
        });
    }

    @Test
    void listProductsCategoryDoesntExist() {
        List<Product> productList = new ArrayList<>();
        when(productRepository.findProductByType(any())).thenReturn(productList);
        assertThrows(ApiException.class, () -> {
            productService.listProductsByCategory(ProductType.DRINKS.getDescription());
        });
    }

//    @Test
//    void listPurchaseOrderProductsWithAnInvalidOrderId() {
//        when(orderRepository.findById(any())).thenReturn(Optional.empty());
//        assertThrows(NotFoundException.class, () -> {
//            purchaseService.listPurchaseOrderProducts(123L);
//        });
//    }
//
//    @Test
//    void listPurchaseOrderProductsWithAValidOrderId() {
//        when(orderRepository.findById(any())).thenReturn(Optional.of(new PurchaseOrder()));
//        when(orderRepository.findPurchaseOrderByProducts(any())).thenReturn(Collections.singletonList(new PurchaseOrder()));
//        assertDoesNotThrow(() -> {
//            purchaseService.listPurchaseOrderProducts(222L);
//        });
//    }
}