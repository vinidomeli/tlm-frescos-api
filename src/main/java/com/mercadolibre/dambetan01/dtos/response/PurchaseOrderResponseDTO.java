package com.mercadolibre.dambetan01.dtos.response;

import com.mercadolibre.dambetan01.model.PurchaseOrder;
import com.mercadolibre.dambetan01.model.User;
import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PurchaseOrderResponseDTO {
   // private User user;
    private String userName;
    private LocalDate date;
    private Double price;

    public static PurchaseOrderResponseDTO toDto(PurchaseOrder purchaseOrder) {
        return builder()
     //           .user(purchaseOrder.getUser())
                .userName(purchaseOrder.getUser().getName())
                .date(purchaseOrder.getDate())
                .price(purchaseOrder.getPrice())
                .build();
    }
}
