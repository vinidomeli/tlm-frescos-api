package com.mercadolibre.dambetan01.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name="Order")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Order {

    @Id
    private Long orderId;
    private LocalDate date;
    private String orderStatus;

    @ManyToOne
    @JoinColumn(name = "fk_buyer", referencedColumnName = "cpf")
    private Buyer buyer;
}
