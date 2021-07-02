package com.mercadolibre.dambetan01.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name="warehouses")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Warehouse {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String warehouseCode;
    private String location;

    @ManyToOne
    @JoinColumn(name = "fk_supervisor", referencedColumnName = "registerNumber")
    private Supervisor supervisor;

}
