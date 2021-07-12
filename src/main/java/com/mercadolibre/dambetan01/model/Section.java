package com.mercadolibre.dambetan01.model;

import com.mercadolibre.dambetan01.dtos.SectionDTO;
import com.mercadolibre.dambetan01.repository.SectionRepository;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;
import org.springframework.beans.factory.annotation.Autowired;

import javax.persistence.*;
import java.util.UUID;

@Entity
@Table(name="Section")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
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
