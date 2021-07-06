package com.mercadolibre.dambetan01.dtos.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.mercadolibre.dambetan01.dtos.BatchStockDTO;
import lombok.*;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDate;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class InboundOrderRequestDTO {

    @JsonIgnore
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd", locale = "pt-BR", timezone = "UTC")
    private LocalDate orderDate = LocalDate.now();

    @Valid
    @JsonProperty("section")
    @NotNull(message = "Section is required.")
    private SectionRequestDTO section;

    @Valid
    @JsonProperty("batchStock")
    @NotNull(message = "BatchStock is required.")
    @Size(min = 1, message = "BatchStock should contain at least 1 batch.")
    private List<BatchStockDTO> batchStock;
}
