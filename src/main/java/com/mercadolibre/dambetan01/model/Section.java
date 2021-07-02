package com.mercadolibre.dambetan01.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name="sections")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Section {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long sectionCode;
    private String productType;
    private Integer limitSize;
    private Double temperature;
    private Integer currentSize;

    @ManyToOne
    @JoinColumn(name = "fk_warehouse", referencedColumnName = "warehouseCode")
    private Warehouse warehouse;

}
