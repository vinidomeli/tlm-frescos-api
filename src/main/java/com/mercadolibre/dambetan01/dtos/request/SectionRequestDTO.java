package com.mercadolibre.dambetan01.dtos.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
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
}
