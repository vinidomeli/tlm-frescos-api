package com.mercadolibre.dambetan01.model;

import lombok.*;

import javax.persistence.*;

@Entity
@Table(name="Supervisor")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Supervisor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long registerNumber;

    @OneToOne
    @JoinColumn(name = "fk_user", referencedColumnName = "id")
    private User user;


}
