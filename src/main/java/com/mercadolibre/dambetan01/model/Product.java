package com.mercadolibre.dambetan01.model;

import com.mercadolibre.dambetan01.model.enums.ProductType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name="Product")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long productId;
    private ProductType type;

    @ManyToOne
    @JoinColumn(name = "fk_seller", referencedColumnName = "cnpj")
    private Seller seller;

}
