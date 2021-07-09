package com.mercadolibre.dambetan01.dtos;

import lombok.*;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class BatchDueDateDTO {
    private Long batchNumber;
    private Long productId;
    private String productTypeId;
    private LocalDate dueDate;
    private Integer quantity;
}
