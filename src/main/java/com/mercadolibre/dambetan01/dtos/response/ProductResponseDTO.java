package com.mercadolibre.dambetan01.dtos.response;

import com.mercadolibre.dambetan01.model.Product;
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
    private String type;
    private Double price;

    public static ProductResponseDTO fromEntity(Product product){
        return ProductResponseDTO.builder()
                .id(product.getId())
                .type(product.getType())
                .price(product.getPrice())
                .build();
    }
}
