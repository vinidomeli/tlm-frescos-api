package com.mercadolibre.dambetan01.model;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name="InboundOrder")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class InboundOrder {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long orderNumber;
    private LocalDate orderDate;

    @ManyToOne
    @JoinColumn(name = "fk_section")
    private Section section;

}
