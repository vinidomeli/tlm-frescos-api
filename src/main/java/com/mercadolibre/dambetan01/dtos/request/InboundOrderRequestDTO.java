package com.mercadolibre.dambetan01.dtos.request;

import com.fasterxml.jackson.annotation.JsonFormat;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDate;
import java.util.List;

public class InboundOrderRequestDTO {

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd", locale = "pt-BR", timezone = "UTC")
    private LocalDate orderDate = LocalDate.now();

    @NotNull(message = "Section is required.")
    private SectionRequestDTO section;

    @NotNull(message = "BatchStock is required.")
    @Size(min = 1, message = "BatchStock should contain at least 1 batch.")
    private List<BatchStockRequestDTO> batchStock;
}
