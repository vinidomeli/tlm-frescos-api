package com.mercadolibre.dambetan01.dtos.response;

import com.mercadolibre.dambetan01.dtos.BatchDueDateDTO;
import lombok.*;

import java.util.List;
import java.util.Objects;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class BatchStockDueDateDTO {
    List<BatchDueDateDTO> batchStockDueDate;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BatchStockDueDateDTO that = (BatchStockDueDateDTO) o;
        return Objects.equals(batchStockDueDate, that.batchStockDueDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(batchStockDueDate);
    }
}
