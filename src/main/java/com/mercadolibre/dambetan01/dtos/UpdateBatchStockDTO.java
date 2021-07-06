package com.mercadolibre.dambetan01.dtos;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
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

    @NotNull(message = "Batch number is required.")
    @JsonProperty("batchNumber")
    private Long batchNumber;

    @NotNull(message = "Product ID is required.")
    @JsonProperty("productId")
    private Long productId;

    @NotNull(message = "Current temperature is required.")
    @JsonProperty("currentTemperature")
    private Double currentTemperature;

    @NotNull(message = "Minimum temperature is required.")
    @JsonProperty("minimumTemperature")
    private Double minimumTemperature;

    @NotNull(message = "Initial quantity is required.")
    @Positive(message = "Initial quantity should be positive.")
    @JsonProperty("initialQuantity")
    private Integer initialQuantity;

    @NotNull(message = "Current quantity is required.")
    @PositiveOrZero(message = "Current quantity should be positive.")
    @JsonProperty("currentQuantity")
    private Integer currentQuantity;

    @NotNull(message = "Manufacturing date is required.")
    @JsonProperty("manufacturingDate")
    @JsonFormat(pattern = "yyyy-MM-dd")
    @JsonDeserialize(using = LocalDateDeserializer.class)
    @JsonSerialize(using = LocalDateSerializer.class)
    private LocalDate manufacturingDate;

    @NotNull(message = "Manufacturing time is required.")
    @JsonProperty("manufacturingTime")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm")
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    private LocalDateTime manufacturingTime;

    @NotNull(message = "Due date is required.")
    @JsonProperty("dueDate")
    @JsonFormat(pattern = "yyyy-MM-dd")
    @JsonDeserialize(using = LocalDateDeserializer.class)
    @JsonSerialize(using = LocalDateSerializer.class)
    private LocalDate dueDate;
}
