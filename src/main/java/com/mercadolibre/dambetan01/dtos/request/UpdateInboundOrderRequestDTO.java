package com.mercadolibre.dambetan01.dtos.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.mercadolibre.dambetan01.dtos.BatchStockDTO;
import com.mercadolibre.dambetan01.dtos.UpdateBatchStockDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UpdateInboundOrderRequestDTO {

    @NotNull(message = "Order number is required.")
    @JsonProperty("orderNumber")
    private Long orderNumber;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd", locale = "pt-BR", timezone = "UTC")
    @JsonProperty("orderDate")
    private LocalDate orderDate = LocalDate.now();

    @NotNull(message = "Section is required.")
    @JsonProperty("section")
    private SectionRequestDTO section;

    @NotNull(message = "BatchStock is required.")
    @Size(min = 1, message = "BatchStock should contain at least 1 batch.")
    @JsonProperty("batchStock")
    private List<UpdateBatchStockDTO> batchStock;
}
