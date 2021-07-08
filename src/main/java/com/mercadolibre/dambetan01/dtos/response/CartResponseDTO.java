package com.mercadolibre.dambetan01.dtos.response;

import com.mercadolibre.dambetan01.model.Cart;
import lombok.*;

import java.util.List;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CartResponseDTO {
    private UUID id;
    private Double price;
    private List<CartContentResponseDTO> content;

    public static CartResponseDTO toDto(Cart cart) {
        return CartResponseDTO.builder()
                .id(cart.getId())
                .price(cart.getPrice())
                .build();
    }
}
