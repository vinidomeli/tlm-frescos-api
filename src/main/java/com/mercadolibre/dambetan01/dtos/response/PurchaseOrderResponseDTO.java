package com.mercadolibre.dambetan01.dtos.response;

import com.mercadolibre.dambetan01.model.PurchaseOrder;
import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PurchaseOrderResponseDTO {
    private String userName;
    private LocalDate date;
    private Double price;

    public static PurchaseOrderResponseDTO toDto(PurchaseOrder purchaseOrder) {
        return builder()
                .userName(purchaseOrder.getUser().getName())
                .date(purchaseOrder.getDate())
                .price(purchaseOrder.getPrice())
                .build();
    }
}
