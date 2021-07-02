package com.mercadolibre.dambetan01.dtos.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class SectionRequestDTO {

    @NotNull(message = "Section code is required.")
    private UUID sectionCode;

    @NotNull(message = "Warehouse code is required.")
    private UUID warehouseCode;
}
