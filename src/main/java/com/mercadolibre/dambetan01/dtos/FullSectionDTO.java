package com.mercadolibre.dambetan01.dtos;

import com.mercadolibre.dambetan01.model.Section;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FullSectionDTO {

    private UUID sectionCode;

    private String productType;

    private Integer limitSize;

    private Double temperature;

    private Integer currentSize;

    private UUID warehouseCode;

    public static FullSectionDTO toDTO(Section section) {
        return FullSectionDTO.builder()
                .sectionCode(section.getSectionCode())
                .productType(section.getProductType())
                .limitSize(section.getLimitSize())
                .temperature(section.getTemperature())
                .currentSize(section.getCurrentSize())
                .warehouseCode(section.getWarehouse().getWarehouseCode())
                .build();
    }
}
