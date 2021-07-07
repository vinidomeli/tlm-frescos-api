package com.mercadolibre.dambetan01.service.crud.impl;

import com.mercadolibre.dambetan01.exceptions.ApiException;
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

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.mockito.ArgumentMatchers.any;

@ExtendWith(MockitoExtension.class)
class PurchaseServiceImplTest {

    @Mock
    ProductRepository productRepository;

    @Mock
    OrderRepository orderRepository;

    @Mock
    UserRepository userRepository;

    @InjectMocks
    PurchaseServiceImpl purchaseService;

    @Test
    void listAllProducts() {
        Product product = mock(Product.class);
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
        Product product = mock(Product.class);
        List<Product> productList = Arrays.asList(product);
        when(productRepository.findProductByType(any())).thenReturn(productList);
        assertDoesNotThrow(() -> {
            purchaseService.listProductsByCategory(any());
        });
    }

//    @Test
//    void listProductsCategoryDoesntExist() {
//        String productType = ProductType.DRINKS.getDescription();
//        when(productRepository.findProductByType(any())).thenReturn(null);
//        assertThrows(ApiException.class, () -> {
//            purchaseService.listProductsByCategory(productType);
//        });
//    }

    @Test
    void listPurchaseOrderProducts() {
        PurchaseOrder purchaseOrder = mock(PurchaseOrder.class);
        Long purchaseorderId = 2l;
        List<PurchaseOrder> purchaseOrderList = Arrays.asList(purchaseOrder);
        when(orderRepository.findPurchaseOrderByProducts(any())).thenReturn(purchaseOrderList);
        assertDoesNotThrow(() -> {
            purchaseService.listPurchaseOrderProducts(purchaseorderId);
        });
    }
}