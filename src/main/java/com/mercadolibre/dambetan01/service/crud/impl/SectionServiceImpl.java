package com.mercadolibre.dambetan01.service.crud.impl;

import com.mercadolibre.dambetan01.repository.ProductRepository;
import com.mercadolibre.dambetan01.repository.SectionRepository;
import com.mercadolibre.dambetan01.service.crud.SectionService;

import java.util.List;
import java.util.UUID;

public class SectionServiceImpl implements SectionService {

    SectionRepository sectionRepository;
    ProductRepository productRepository;

    public SectionServiceImpl(final SectionRepository sectionRepository, final ProductRepository productRepository) {
        this.sectionRepository = sectionRepository;
        this.productRepository = productRepository;
    }

    @Override
    public void sectionExists(UUID sectionCode) {
        boolean sectionDoesntExists = !sectionRepository.existsBySectionCode(sectionCode);
        if(sectionDoesntExists) {
            throw new RuntimeException("Section doesn't exists");
        }
    }

    @Override
    public void sectionBelongsToWarehouse(UUID sectionCode, UUID warehouseCode) {
        boolean sectionDoesntBelongsToWarehouse = sectionRepository.findBySectionCode(sectionCode)
                .getWarehouse()
                .getWarehouseCode()
                .equals(warehouseCode);
        if(sectionDoesntBelongsToWarehouse) {
            throw new RuntimeException("Section doesn't belongs to warehouse");
        }
    }

    @Override
    public void sectionMatchesProductType(UUID sectionCode, List<Long> productIds) {

        productIds.stream()
                .map(productId -> productRepository.findByProductId(productId).getType())
                .forEach(productType -> {
                    String sectionType = sectionRepository.findBySectionCode(sectionCode).getProductType();
                    boolean sectionDoesntMatchesProductType = sectionType.equals(productType);
                    if(sectionDoesntMatchesProductType) {
                        throw new RuntimeException("Section doesn't matches product type");
                    }
                });
    }

    @Override
    public void sectionHasSuficientSpace(Integer totalInboundOrderSize, UUID sectionCode) {
        Integer sectionLimitSize = sectionRepository.findBySectionCode(sectionCode).getLimitSize();
        Integer sectionCurrentSize = sectionRepository.findBySectionCode(sectionCode).getCurrentSize();
        Integer remainingSize = sectionLimitSize - sectionCurrentSize;
        boolean sectionHasntSuficientSpace = remainingSize < totalInboundOrderSize;
        if(sectionHasntSuficientSpace) {
            throw new RuntimeException("Section hasn't suficient space");
        }
    }
}
