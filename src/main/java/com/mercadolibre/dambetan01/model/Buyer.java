package com.mercadolibre.dambetan01.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name="Buyer")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Buyer {

    @Id
    private String cpf;

    @OneToOne
    @JoinColumn(name = "fk_user", referencedColumnName = "id")
    private User user;
}
