package com.mercadolibre.dambetan01.dtos.response;

import com.mercadolibre.dambetan01.model.CartContent;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CartContentResponseDTO {
    private Long contentId;
    private String productType;
    private Integer productQuantity;

    public static CartContentResponseDTO toDto(CartContent cartContent) {
        return CartContentResponseDTO.builder()
                .contentId(cartContent.getId())
                .productType(cartContent.getProduct().getType())
                .productQuantity(cartContent.getQuantity())
                .build();
    }
}
