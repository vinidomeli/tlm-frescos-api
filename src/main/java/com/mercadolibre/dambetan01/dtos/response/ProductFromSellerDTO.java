package com.mercadolibre.dambetan01.dtos.response;

import com.mercadolibre.dambetan01.model.Product;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ProductFromSellerDTO {
    private Long productId;
    private String type;
    private Double price;

    public static ProductFromSellerDTO fromEntity(Product product) {
        return ProductFromSellerDTO.builder()
                .productId(product.getId())
                .type(product.getType())
                .price(product.getPrice())
                .build();
    }
}
