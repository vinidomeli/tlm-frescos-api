package com.mercadolibre.dambetan01.model;

import lombok.*;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.UUID;

@Builder
@Entity
@Table(name="Warehouse")
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
