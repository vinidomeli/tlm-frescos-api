package com.mercadolibre.dambetan01.model;

import lombok.AllArgsConstructor;
// import lombok.Data;
// import lombok.NoArgsConstructor;

// import javax.persistence.*;
// import java.time.LocalDate;
// import java.util.List;

// @Entity
// @Table(name="PurchaseOrder")
// @Data
// @NoArgsConstructor
// @AllArgsConstructor
// public class PurchaseOrder {

    // @Id
    // @GeneratedValue(strategy = GenerationType.IDENTITY)
    // private Long id;
    // private LocalDate date;
    // private String orderStatus;

    // @ManyToOne
    // @JoinColumn(name = "fk_buyer", referencedColumnName = "cpf")
    // private Buyer buyer;

    // @ManyToMany
    // @JoinTable(name = "ProductOrder", joinColumns = {
    //         @JoinColumn(name = "purchaseOrderId")
    // }, inverseJoinColumns = {
    //         @JoinColumn(name = "productId")
    // })
    // private List<Product> products;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "PurchaseOrder")
public class PurchaseOrder {
    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "org.hibernate.id.UUIDGenerator")
    private UUID id;

    private LocalDate date;

    private Double price;

    @OneToOne
    @JoinColumn(name = "fk_user", referencedColumnName = "id")
    private User user;

    //@TODO Revisar
    @ManyToMany
    @JoinTable(name = "ProductOrder", joinColumns = {
            @JoinColumn(name = "purchaseOrderId")
    }, inverseJoinColumns = {
            @JoinColumn(name = "productId")
    })
    private List<Product> products;
}
