package com.mercadolibre.dambetan01.dtos.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CartContentRequestDTO {
    @NotNull(message = "Product Id is required.")
    @JsonProperty("productId")
    private Long productId;

    @NotNull(message = "Quantity is required.")
    @JsonProperty("quantity")
    private Integer quantity;
}
