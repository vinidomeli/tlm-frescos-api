package com.mercadolibre.dambetan01.dtos.response;

import com.mercadolibre.dambetan01.dtos.BatchDueDateDTO;
import lombok.*;

import java.util.List;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class BatchStockDueDateDTO {
    List<BatchDueDateDTO> batchStockDueDate;
}
