package com.mercadolibre.dambetan01.service.crud.impl;

import com.github.javafaker.Faker;
import com.mercadolibre.dambetan01.exceptions.ApiException;
import com.mercadolibre.dambetan01.repository.WarehouseRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import org.mockito.junit.jupiter.MockitoExtension;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class WarehouseServiceImplTest {

    @Mock
    WarehouseRepository warehouseRepository;

    @InjectMocks
    WarehouseServiceImpl warehouseService;

    private final Faker faker = new Faker();

    @Test
    void warehouseCodeThatExistsTest() {
        UUID newWarehouseCode = UUID.fromString(faker.internet().uuid());

        when(warehouseRepository.existsByWarehouseCode(any())).thenReturn(true);
        assertDoesNotThrow(() -> {
            warehouseService.warehouseExists(newWarehouseCode);
        });
    }

    @Test
    void warehouseCodeDoesntExistsTest() {
        UUID registeredWarehouseCode = UUID.fromString(faker.internet().uuid());

        when(warehouseRepository.existsByWarehouseCode(any())).thenReturn(false);
        assertThrows(ApiException.class, () -> {
            warehouseService.warehouseExists(registeredWarehouseCode);
        });
    }
}