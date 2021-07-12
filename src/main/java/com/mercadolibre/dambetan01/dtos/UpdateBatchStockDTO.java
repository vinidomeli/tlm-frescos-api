package com.mercadolibre.dambetan01.dtos;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import com.mercadolibre.dambetan01.model.Batch;
import lombok.*;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UpdateBatchStockDTO {

    @JsonProperty("batchNumber")
    @NotNull(message = "Batch number is required.")
    private Long batchNumber;

    @JsonProperty("productId")
    @NotNull(message = "Product ID is required.")
    private Long productId;

    @JsonProperty("currentTemperature")
    @NotNull(message = "Current temperature is required.")
    private Double currentTemperature;

    @JsonProperty("minimumTemperature")
    @NotNull(message = "Minimum temperature is required.")
    private Double minimumTemperature;

    @JsonProperty("initialQuantity")
    @NotNull(message = "Initial quantity is required.")
    @Positive(message = "Initial quantity should be positive.")
    private Integer initialQuantity;

    @JsonProperty("currentQuantity")
    @NotNull(message = "Current quantity is required.")
    @PositiveOrZero(message = "Current quantity should be positive.")
    private Integer currentQuantity;

    @JsonProperty("manufacturingDate")
    @NotNull(message = "Manufacturing date is required.")
    @JsonFormat(pattern = "yyyy-MM-dd")
    @JsonDeserialize(using = LocalDateDeserializer.class)
    @JsonSerialize(using = LocalDateSerializer.class)
    private LocalDate manufacturingDate;

    @JsonProperty("manufacturingTime")
    @NotNull(message = "Manufacturing time is required.")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm")
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    private LocalDateTime manufacturingTime;

    @JsonProperty("dueDate")
    @NotNull(message = "Due date is required.")
    @JsonFormat(pattern = "yyyy-MM-dd")
    @JsonDeserialize(using = LocalDateDeserializer.class)
    @JsonSerialize(using = LocalDateSerializer.class)
    private LocalDate dueDate;

    public static UpdateBatchStockDTO fromEntity(Batch batch) {
        return UpdateBatchStockDTO.builder()
                .batchNumber(batch.getBatchNumber())
                .productId(batch.getProduct().getId())
                .currentQuantity(batch.getCurrentQuantity())
                .minimumTemperature(batch.getMinimumTemperature())
                .initialQuantity(batch.getInitialQuantity())
                .currentQuantity(batch.getCurrentQuantity())
                .manufacturingDate(batch.getManufacturingDate())
                .manufacturingTime(batch.getManufacturingTime())
                .dueDate(batch.getDueDate())
                .build();
    }
}
