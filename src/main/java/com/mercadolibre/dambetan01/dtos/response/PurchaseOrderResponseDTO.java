package com.mercadolibre.dambetan01.dtos.response;

import com.mercadolibre.dambetan01.model.User;
import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PurchaseOrderResponseDTO {
    private User user;
    private LocalDate date;
    private Double price;
}
