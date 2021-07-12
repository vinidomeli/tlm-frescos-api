package com.mercadolibre.dambetan01.dtos.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.mercadolibre.dambetan01.dtos.BatchStockDTO;
import lombok.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BatchStockResponseDTO that = (BatchStockResponseDTO) o;
        return Objects.equals(orderNumber, that.orderNumber);
    }

    @Override
    public int hashCode() {
        return Objects.hash(orderNumber);
    }
}