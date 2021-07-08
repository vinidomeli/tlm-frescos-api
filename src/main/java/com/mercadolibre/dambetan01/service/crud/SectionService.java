package com.mercadolibre.dambetan01.service.crud;

import com.mercadolibre.dambetan01.dtos.FullSectionDTO;

import java.util.List;
import java.util.UUID;

public interface SectionService {

    public void sectionExists(UUID sectionCode);

    public void sectionBelongsToWarehouse(UUID sectionCode, UUID warehouseCode);

    public void sectionMatchesProductType(UUID sectionCode, List<Long> productIds);

    void sectionHasSufficientSpace(Integer totalInboundOrderSize, UUID sectionCode);

    public List<FullSectionDTO> findAllSections();
}
