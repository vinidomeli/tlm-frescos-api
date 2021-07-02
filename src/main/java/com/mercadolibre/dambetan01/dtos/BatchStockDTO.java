package com.mercadolibre.dambetan01.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class BatchStockDTO {

    @NotNull(message = "Product ID is required.")
    private Long productId;

    @NotNull(message = "Current temperature is required.")
    private Double currentTemperature;

    @NotNull(message = "Minimum temperature is required.")
    private Double minimumTemperature;

    @NotNull(message = "Initial quantity is required.")
    @Positive(message = "Initial quantity should be positive.")
    private Integer initialQuantity;

    @NotNull(message = "Current quantity is required.")
    @PositiveOrZero(message = "Current quantity should be positive.")
    private Integer currentQuantity;

    @NotNull(message = "Manufacturing date is required.")
    private LocalDate manufacturingDate;

    @NotNull(message = "Manufacturing time is required.")
    private LocalDateTime manufacturingTime;

    @NotNull(message = "Due date is required.")
    private LocalDate dueDate;
}
