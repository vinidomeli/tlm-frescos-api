package com.mercadolibre.dambetan01.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name="batches")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
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
    private Float minimumTemperature;
    private Float currentTemperature;
    private LocalDateTime manufacturingTime;
    private LocalDate manufacturingDate;
    private LocalDate dueDate;


}
