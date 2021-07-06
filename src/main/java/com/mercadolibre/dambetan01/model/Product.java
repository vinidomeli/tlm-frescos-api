package com.mercadolibre.dambetan01.model;

import lombok.*;

import javax.persistence.*;

@Entity
@Table(name="Product")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String type;

    @ManyToOne
    @JoinColumn(name = "fk_seller", referencedColumnName = "CNPJ")
    private Seller seller;

    private Double price;

}
