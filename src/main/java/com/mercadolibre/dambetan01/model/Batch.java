package com.mercadolibre.dambetan01.model;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name="Batch")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Batch {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long batchNumber;
    private String productType;

    @ManyToOne
    @JoinColumn(name = "fk_product", referencedColumnName = "productId")
    private Product product;

    @ManyToOne
    @JoinColumn(name = "fk_inboundOrder", referencedColumnName = "orderNumber")
    private InboundOrder inboundOrder;

    private Integer initialQuantity;
    private Integer currentQuantity;
    private Double minimumTemperature;
    private Double currentTemperature;
    private LocalDateTime manufacturingTime;
    private LocalDate manufacturingDate;
    private LocalDate dueDate;


}
