package com.mercadolibre.dambetan01.dtos.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.mercadolibre.dambetan01.dtos.UpdateBatchStockDTO;
import lombok.*;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UpdateInboundOrderDTO {

    @Valid
    @NotNull(message = "Order number is required.")
    @JsonProperty("orderNumber")
    private Long orderNumber;

    @Valid
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd", locale = "pt-BR", timezone = "UTC")
    @JsonProperty("orderDate")
    private LocalDate orderDate = LocalDate.now();

    @Valid
    @NotNull(message = "Section is required.")
    @JsonProperty("section")
    private SectionRequestDTO section;

    @Valid
    @NotNull(message = "BatchStock is required.")
    @Size(min = 1, message = "BatchStock should contain at least 1 batch.")
    @JsonProperty("batchStock")
    private List<UpdateBatchStockDTO> batchStock;
}
