package com.mercadolibre.dambetan01.dtos.response;

import com.mercadolibre.dambetan01.dtos.CountryHouseDTO;
import lombok.Data;
import org.springframework.validation.annotation.Validated;

@Validated
@Data
public class CountryHouseResponseDTO {
    private String message;
    private CountryHouseDTO countryHouseDTO;
}
