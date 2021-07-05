package com.mercadolibre.dambetan01.model;

import com.mercadolibre.dambetan01.model.enums.ProductType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name="Batch")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Batch {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long batchNumber;
    private ProductType productType;

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
