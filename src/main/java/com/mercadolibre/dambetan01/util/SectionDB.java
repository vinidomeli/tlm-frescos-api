package com.mercadolibre.dambetan01.util;

import com.github.javafaker.Faker;
import com.mercadolibre.dambetan01.model.Section;
import com.mercadolibre.dambetan01.model.enums.ProductType;
import com.mercadolibre.dambetan01.repository.SectionRepository;
import com.mercadolibre.dambetan01.repository.WarehouseRepository;

import java.util.UUID;

public class SectionDB {

    WarehouseRepository warehouseRepository;
    SectionRepository sectionRepository;

    public SectionDB(WarehouseRepository warehouseRepository, SectionRepository sectionRepository) {
        this.warehouseRepository = warehouseRepository;
        this.sectionRepository = sectionRepository;
    }

    Faker faker = new Faker();

    public UUID insertSection(ProductType type, UUID warehouseCode) {
        Section section = Section.builder()
                .limitSize(faker.number().numberBetween(300, 500))
                .currentSize(0)
                .productType(type.getDescription())
                .warehouse(warehouseRepository.findByWarehouseCode(warehouseCode))
                .temperature(10.0)
                .build();

        return sectionRepository.save(section).getSectionCode();
    }

}
