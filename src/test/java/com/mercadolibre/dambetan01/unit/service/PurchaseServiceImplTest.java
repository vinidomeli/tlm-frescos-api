package com.mercadolibre.dambetan01.unit.service;

import com.mercadolibre.dambetan01.dtos.request.OrderRequestDTO;
import com.mercadolibre.dambetan01.dtos.response.ProductResponseDTO;
import com.mercadolibre.dambetan01.model.Product;
import com.mercadolibre.dambetan01.model.Seller;
import com.mercadolibre.dambetan01.model.enums.ProductType;
import com.mercadolibre.dambetan01.repository.OrderRepository;
import com.mercadolibre.dambetan01.repository.ProductRepository;
import com.mercadolibre.dambetan01.service.crud.impl.PurchaseServiceImpl;
import org.junit.Test;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.mockito.Mockito;
import org.modelmapper.ModelMapper;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

public class PurchaseServiceImplTest {

    ProductRepository productRepository = Mockito.mock(ProductRepository.class);
    OrderRepository orderRepository = Mockito.mock(OrderRepository.class);

    ModelMapper modelMapper = Mockito.mock(ModelMapper.class);
    PurchaseServiceImpl purchaseService;

    static OrderRequestDTO orderRequestDTO;

    @BeforeEach
    void setUp() {
        modelMapper = new ModelMapper();
        this.purchaseService = new PurchaseServiceImpl(productRepository, orderRepository);
    }

//    @BeforeAll
//    static void setupProduct() {
//        List<Product> productList = new ArrayList<>();
//        Seller seller = new Seller();
//        seller.setCnpj("25.928.839/0001-15");
//        productList.add(new Product(1L, ProductType.REFRIGERATE, seller, 232.00));
//        productList.add(new Product(2L, ProductType.DRINKS, seller, 100.00));
//
//        orderRequestDTO = new OrderRequestDTO(null, "444.444.444-44", "Carrinho", productList);
//    }

    @Test
    public void shouldReturnListOfProducts() {
        List<ProductResponseDTO> productList = new ArrayList<>();
        //Seller seller = new Seller();
        //seller.setCnpj("25.928.839/0001-15");
        productList.add(new ProductResponseDTO(1L, ProductType.REFRIGERATE, 232.00));
        productList.add(new ProductResponseDTO(2L, ProductType.DRINKS, 100.00));
        //when(productRepository.findAll()).thenReturn(productList);
        List<ProductResponseDTO> productResponseDTO = purchaseService.listAllProducts();
        assertEquals(productList, productResponseDTO);
    }

}
