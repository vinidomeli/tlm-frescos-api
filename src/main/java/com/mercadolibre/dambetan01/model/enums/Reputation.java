package com.mercadolibre.dambetan01.model.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum Reputation {

    EXCELLENT(1, "Excellent"),
    GOOD(2, "Good"),
    BAD(3, "Bad");

    private Integer code;
    private String description;

    public static Reputation toEnum(Integer code) {
        if (code == null) {
            return null;
        }

        for (Reputation rep : Reputation.values()) {
            if (code.equals(rep.getCode())) {
                return rep;
            }
        }

        throw new IllegalArgumentException("Reputation not valid: " + code);
    }

}
