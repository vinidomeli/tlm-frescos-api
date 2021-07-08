package com.mercadolibre.dambetan01.model;

import lombok.*;

import javax.persistence.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "PurchaseOrderContent")
@Builder
public class PurchaseOrderContent {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String productName;

    private Double productPrice;

    private Integer productQuantity;

    @ManyToOne
    @JoinColumn(name = "fk_purchaseOrder")
    private PurchaseOrder purchaseOrder;
}
