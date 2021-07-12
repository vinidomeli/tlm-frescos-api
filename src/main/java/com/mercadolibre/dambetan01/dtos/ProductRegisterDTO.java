package com.mercadolibre.dambetan01.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.mercadolibre.dambetan01.model.Product;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ProductRegisterDTO {

    @Valid
    @JsonProperty("type")
    @NotNull
    private String type;

    @Valid
    @JsonProperty("price")
    @NotNull
    private Double price;

    public static ProductRegisterDTO fromEntity(Product product) {
        return ProductRegisterDTO.builder()
                .type(product.getType())
                .price(product.getPrice())
                .build();
    }
}
