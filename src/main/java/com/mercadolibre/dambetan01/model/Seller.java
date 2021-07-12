package com.mercadolibre.dambetan01.model;

import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "Seller")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Seller {

    @Id
    private String cnpj;

    @OneToOne
    @JoinColumn(name = "fk_user", referencedColumnName = "id")
    private User user;
    private String reputation;

}
