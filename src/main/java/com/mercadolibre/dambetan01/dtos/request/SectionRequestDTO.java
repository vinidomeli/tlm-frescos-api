package com.mercadolibre.dambetan01.dtos.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class SectionRequestDTO {

    private UUID sectionCode;
    private UUID warehouseCode;
}
