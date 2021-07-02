package com.mercadolibre.dambetan01.dtos.response;

import com.mercadolibre.dambetan01.dtos.request.BatchStockRequestDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class BatchStockResponseDTO {

    @NotNull(message = "BatchStock is required.")
    @Size(min = 1, message = "BatchStock should contain at least 1 batch.")
    private List<BatchStockRequestDTO> batchStock;
}