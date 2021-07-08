package com.mercadolibre.dambetan01.service.crud.impl;

import com.mercadolibre.dambetan01.exceptions.ApiException;
import com.mercadolibre.dambetan01.exceptions.NotFoundException;
import com.mercadolibre.dambetan01.model.Product;
import com.mercadolibre.dambetan01.model.PurchaseOrder;
import com.mercadolibre.dambetan01.model.enums.ProductType;
import com.mercadolibre.dambetan01.repository.OrderRepository;
import com.mercadolibre.dambetan01.repository.ProductRepository;
import com.mercadolibre.dambetan01.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;
import static org.mockito.ArgumentMatchers.any;

@ExtendWith(MockitoExtension.class)
class PurchaseServiceImplTest {

    @Mock
    ProductRepository productRepository;

    @Mock
    OrderRepository orderRepository;

    @InjectMocks
    PurchaseServiceImpl purchaseService;

    @Test
    void listAllProducts() {
        Product product = new Product();
        List<Product> productList = Arrays.asList(product);

        when(productRepository.findAll()).thenReturn(productList);
        assertDoesNotThrow(() -> {
            purchaseService.listAllProducts();
        });
    }

    @Test
    void listAllProductsIsEmpty() {
        List<Product> productList = new ArrayList<>();

        when(productRepository.findAll()).thenReturn(productList);
        assertThrows(ApiException.class, () -> {
            purchaseService.listAllProducts();
        });
    }

    @Test
    void listProductsByCategory() {
        Product product = new Product();
        List<Product> productList = Arrays.asList(product);
        when(productRepository.findProductByType(any())).thenReturn(productList);
        assertDoesNotThrow(() -> {
            purchaseService.listProductsByCategory(any());
        });
    }

    @Test
    void listProductsCategoryDoesntExist() {
        List<Product> productList = new ArrayList<>();
        when(productRepository.findProductByType(any())).thenReturn(productList);
        assertThrows(ApiException.class, () -> {
            purchaseService.listProductsByCategory(ProductType.DRINKS.getDescription());
        });
    }

    @Test
    void listPurchaseOrderProductsWithAnInvalidOrderId() {
        when(orderRepository.findById(any())).thenReturn(Optional.empty());
        assertThrows(NotFoundException.class, () -> {
            purchaseService.listPurchaseOrderProducts(123L);
        });
    }

    @Test
    void listPurchaseOrderProductsWithAValidOrderId() {
        when(orderRepository.findById(any())).thenReturn(Optional.of(new PurchaseOrder()));
        when(orderRepository.findPurchaseOrderByProducts(any())).thenReturn(Collections.singletonList(new PurchaseOrder()));
        assertDoesNotThrow(() -> {
            purchaseService.listPurchaseOrderProducts(222L);
        });
    }
}