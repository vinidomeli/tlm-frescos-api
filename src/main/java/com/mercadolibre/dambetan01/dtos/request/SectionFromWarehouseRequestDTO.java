package com.mercadolibre.dambetan01.dtos.request;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.mercadolibre.dambetan01.model.Section;
import lombok.*;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class SectionFromWarehouseRequestDTO {

    @JsonIgnore
    @JsonProperty("sectionCode")
    private UUID sectionCode;

    @Valid
    @JsonProperty("productType")
    @NotNull(message = "Product type is required.")
    private String productType;

    @Valid
    @JsonProperty("limitSize")
    @NotNull(message = "Limit size is required.")
    private Integer limitSize;

    @JsonIgnore
    @JsonProperty("currentSize")
    private Integer currentSize;

    @Valid
    @JsonProperty("temperature")
    @NotNull(message = "Temperature is required.")
    private Double temperature;

    public static SectionFromWarehouseRequestDTO fromSection(Section section) {
        return SectionFromWarehouseRequestDTO.builder()
                .sectionCode(section.getSectionCode())
                .productType(section.getProductType())
                .limitSize(section.getLimitSize())
                .currentSize(section.getCurrentSize())
                .temperature(section.getTemperature())
                .build();
    }
}
