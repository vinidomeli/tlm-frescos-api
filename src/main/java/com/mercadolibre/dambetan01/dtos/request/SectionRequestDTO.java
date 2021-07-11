package com.mercadolibre.dambetan01.dtos.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.mercadolibre.dambetan01.model.Section;
import lombok.*;

import javax.validation.constraints.NotNull;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class SectionRequestDTO {

    @JsonProperty("sectionCode")
    @NotNull(message = "Section code is required.")
    private UUID sectionCode;

    @JsonProperty("warehouseCode")
    @NotNull(message = "Warehouse code is required.")
    private UUID warehouseCode;

    public static SectionRequestDTO fromEntity(Section section) {
        return SectionRequestDTO.builder()
                .sectionCode(section.getSectionCode())
                .warehouseCode(section.getWarehouse().getWarehouseCode())
                .build();
    }
}
