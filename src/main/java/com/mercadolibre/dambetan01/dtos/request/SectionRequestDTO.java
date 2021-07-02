package com.mercadolibre.dambetan01.dtos.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class SectionDTO {

    @NotBlank(message = "Section code is required.")
    private String sectionCode;

    @NotBlank(message = "Warehouse code is required.")
    private String warehouseCode;


}
