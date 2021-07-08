package com.mercadolibre.dambetan01.service.crud.impl;

import com.github.javafaker.Faker;
import com.mercadolibre.dambetan01.exceptions.ApiException;
import com.mercadolibre.dambetan01.model.Product;
import com.mercadolibre.dambetan01.model.Section;
import com.mercadolibre.dambetan01.model.Warehouse;
import com.mercadolibre.dambetan01.model.enums.ProductType;
import com.mercadolibre.dambetan01.repository.ProductRepository;
import com.mercadolibre.dambetan01.repository.SectionRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class SectionServiceImplTest {

    private final Faker faker = new Faker();
    private final UUID sectionID = UUID.fromString(this.faker.internet().uuid());
    @Mock
    SectionRepository sectionRepository;
    @Mock
    ProductRepository productRepository;
    @InjectMocks
    SectionServiceImpl sectionService;

    @Test
    void sectionThatDoesntExists() {
        when(this.sectionRepository.existsBySectionCode(any())).thenReturn(false);

        assertThrows(ApiException.class, () -> {
            this.sectionService.sectionExists(this.sectionID);
        });
    }

    @Test
    void sectionExistsTest() {
        when(this.sectionRepository.existsBySectionCode(any())).thenReturn(true);

        assertDoesNotThrow(() -> {
            this.sectionService.sectionExists(this.sectionID);
        });
    }

    @Test
    void sectionBelongsToWarehouseTest() {
        UUID warehouseCode = UUID.fromString(this.faker.internet().uuid());

        Warehouse warehouse = Warehouse.builder()
                .warehouseCode(warehouseCode)
                .build();

        Section section = Section.builder()
                .warehouse(warehouse)
                .build();

        when(this.sectionRepository.findBySectionCode(any())).thenReturn(section);

        assertDoesNotThrow(() -> {
            this.sectionService.sectionBelongsToWarehouse(this.sectionID, warehouseCode);
        });
    }

    @Test
    void sectionThatDoesntBelongsToWarehouseTest() {
        UUID fakeWarehouseCode = UUID.fromString(this.faker.internet().uuid());
        UUID warehouseCode = UUID.fromString(this.faker.internet().uuid());

        Warehouse warehouse = Warehouse.builder()
                .warehouseCode(warehouseCode)
                .build();

        Section section = Section.builder()
                .warehouse(warehouse)
                .build();

        when(this.sectionRepository.findBySectionCode(any())).thenReturn(section);

        assertThrows(ApiException.class, () -> {
            this.sectionService.sectionBelongsToWarehouse(this.sectionID, fakeWarehouseCode);
        });
    }

    @Test
    void sectionMatchesProductType() {
        List<Long> productsIds = new ArrayList<>();
        String productType = ProductType.FROZEN.getDescription();

        productsIds.add(123123L);

        Product product = Product.builder()
                .type(productType)
                .build();

        Section section = Section.builder()
                .productType(productType)
                .build();

        when(this.productRepository.findById(any())).thenReturn(Optional.of(product));

        when(this.sectionRepository.findBySectionCode(any())).thenReturn(section);

        assertDoesNotThrow(() -> {
            this.sectionService.sectionMatchesProductType(this.sectionID, productsIds);
        });
    }

    @Test
    void sectionDoesntMatchesProductType() {
        List<Long> productsIds = new ArrayList<>();
        String productType = ProductType.FROZEN.getDescription();

        productsIds.add(123123L);

        Product product = Product.builder()
                .type(productType)
                .build();

        Section section = Section.builder()
                .productType(ProductType.DRINKS.getDescription())
                .build();

        when(this.productRepository.findById(any())).thenReturn(Optional.of(product));

        when(this.sectionRepository.findBySectionCode(any())).thenReturn(section);

        assertThrows(ApiException.class, () -> {
            this.sectionService.sectionMatchesProductType(this.sectionID, productsIds);
        });
    }


    @Test
    void sectionHasSufficientSpace() {
        final Integer limit = 300;
        final Integer currentSize = 3;
        final Integer batchSize = 100;

        Section section = Section.builder()
                .limitSize(limit)
                .currentSize(currentSize)
                .build();

        when(this.sectionRepository.findBySectionCode(any())).thenReturn(section);

        assertDoesNotThrow(() -> {
            this.sectionService.sectionHasSufficientSpace(batchSize, this.sectionID);
        });

    }

    @Test
    void sectionDoesntHaveSufficientSpace() {
        final Integer limit = 300;
        final Integer currentSize = 3;
        final Integer batchSize = 298;

        Section section = Section.builder()
                .limitSize(limit)
                .currentSize(currentSize)
                .build();

        when(this.sectionRepository.findBySectionCode(any())).thenReturn(section);

        assertThrows(ApiException.class, () -> {
            this.sectionService.sectionHasSufficientSpace(batchSize, this.sectionID);
        });

    }
}