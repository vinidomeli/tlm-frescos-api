package com.mercadolibre.dambetan01.dtos.request;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class BatchStockDTO {

    private String batchNumber;
    private String productId;
    private Double currentTemperature;
    private Double minimumTemperature;
    private Integer initialQuantity;
    private Integer currentQuantity;
    private LocalDate manufacturingDate;
    private LocalDateTime manufacturingTime;
    private LocalDate dueDate;
}
