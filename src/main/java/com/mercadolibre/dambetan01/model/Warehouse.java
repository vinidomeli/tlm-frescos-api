package com.mercadolibre.dambetan01.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.UUID;

@Entity
@Table(name="warehouses")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Warehouse {

    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "org.hibernate.id.UUIDGenerator")
    private UUID warehouseCode;
    private String location;

    @ManyToOne
    @JoinColumn(name = "fk_supervisor", referencedColumnName = "registerNumber")
    private Supervisor supervisor;

}
