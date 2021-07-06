package com.mercadolibre.dambetan01.unit.service;

import com.mercadolibre.dambetan01.repository.OrderRepository;
import com.mercadolibre.dambetan01.repository.ProductRepository;
import com.mercadolibre.dambetan01.service.crud.impl.PurchaseServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.mockito.Mockito;
import org.modelmapper.ModelMapper;

public class PurchaseServiceImplTest {

//    ProductRepository productRepository;
//    ModelMapper modelMapper;
    ProductRepository productRepository = Mockito.mock(ProductRepository.class);
    OrderRepository orderRepository = Mockito.mock(OrderRepository.class);

    ModelMapper modelMapper = Mockito.mock(ModelMapper.class);
    PurchaseServiceImpl purchaseService;

    //private final PurchaseServiceImpl purchaseService = new PurchaseServiceImpl();

    @BeforeEach
    void setUp() {
        modelMapper = new ModelMapper();
        this.purchaseService = new PurchaseServiceImpl(productRepository, orderRepository);
    }


}
