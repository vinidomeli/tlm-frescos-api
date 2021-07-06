package com.mercadolibre.dambetan01.dtos.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.mercadolibre.dambetan01.dtos.BatchStockDTO;
import lombok.*;

import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class BatchStockResponseDTO {

    private Long orderNumber;
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate orderDate;
    private List<BatchStockDTO> batchStock;
}