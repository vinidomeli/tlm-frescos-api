package com.mercadolibre.dambetan01.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name="Order")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Order {

    @Id
    private Long id;
    private LocalDate date;
    private String orderStatus;

    @ManyToOne
    @JoinColumn(name = "fk_buyer", referencedColumnName = "cpf")
    private Buyer buyer;

    @ManyToMany
    @JoinTable(name = "product_order", joinColumns = {
            @JoinColumn(name = "order_id")
    }, inverseJoinColumns = {
            @JoinColumn(name = "product_id")
    })
    private List<Product> products;
}
