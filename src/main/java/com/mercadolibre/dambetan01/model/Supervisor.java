package com.mercadolibre.dambetan01.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name="Supervisor")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Supervisor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long registerNumber;

    @OneToOne
    @JoinColumn(name = "fk_user", referencedColumnName = "id")
    private User user;


}
