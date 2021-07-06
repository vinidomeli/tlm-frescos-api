package com.mercadolibre.dambetan01.service.crud.impl;

import com.mercadolibre.dambetan01.exceptions.ApiException;
import com.mercadolibre.dambetan01.repository.ProductRepository;
import com.mercadolibre.dambetan01.repository.SectionRepository;
import com.mercadolibre.dambetan01.service.crud.SectionService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
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
            throw new ApiException("404", "Section doesn't exists", 404);
        }
    }

    @Override
    public void sectionBelongsToWarehouse(UUID sectionCode, UUID warehouseCode) {
        boolean sectionDoesntBelongsToWarehouse = !sectionRepository.findBySectionCode(sectionCode)
                .getWarehouse()
                .getWarehouseCode()
                .equals(warehouseCode);
        if(sectionDoesntBelongsToWarehouse) {
            throw new ApiException("404", "Section doesn't belongs to warehouse", 404);
        }
    }

    @Override
    public void sectionMatchesProductType(UUID sectionCode, List<Long> productIds) {

        productIds.stream()
                .map(productId -> productRepository.findById(productId).get().getType())
                .forEach(productType -> {
                    String sectionType = sectionRepository.findBySectionCode(sectionCode).getProductType();
                    boolean sectionDoesntMatchesProductType = !sectionType.equals(productType);
                    if(sectionDoesntMatchesProductType) {
                        throw new ApiException("404", "Section doesn't matches product type", 404);
                    }
                });
    }

    @Override
    public void sectionHasSufficientSpace(Integer totalInboundOrderSize, UUID sectionCode) {
        Integer sectionLimitSize = sectionRepository.findBySectionCode(sectionCode).getLimitSize();
        Integer sectionCurrentSize = sectionRepository.findBySectionCode(sectionCode).getCurrentSize();
        int remainingSize = sectionLimitSize - sectionCurrentSize;
        boolean sectionHasntSufficientSpace = remainingSize < totalInboundOrderSize;
        if(sectionHasntSufficientSpace) {
            throw new ApiException("404", "Section hasn't sufficient space", 404);
        }
    }
}
