package com.mercadolibre.dambetan01.dtos.response;

import com.mercadolibre.dambetan01.model.enums.ProductType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProductResponseDTO {

    private Long id;
    private ProductType type;
    private Double price;
}
