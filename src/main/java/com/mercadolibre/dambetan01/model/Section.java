package com.mercadolibre.dambetan01.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.UUID;

@Entity
@Table(name="sections")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Section {

    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "org.hibernate.id.UUIDGenerator")
    private UUID sectionCode;
    private String productType;
    private Integer limitSize;
    private Double temperature;
    private Integer currentSize;

    @ManyToOne
    @JoinColumn(name = "fk_warehouse", referencedColumnName = "warehouseCode")
    private Warehouse warehouse;

}
