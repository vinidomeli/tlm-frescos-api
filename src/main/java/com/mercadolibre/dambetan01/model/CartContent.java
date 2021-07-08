package com.mercadolibre.dambetan01.model;

import lombok.*;

import javax.persistence.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "CartContent")
@Builder
public class CartContent {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Integer quantity;

    @ManyToOne
    @JoinColumn(name = "fk_cart")
    private Cart cart;

    @ManyToOne
    @JoinColumn(name = "fk_product")
    private Product product;
}
