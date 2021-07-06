package com.mercadolibre.dambetan01.model.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum Roles {

    SUPERVISOR(1, "Supervisor"),
    SELLER(2, "Seller"),
    BUYER(3, "Buyer");

    private Integer code;
    private String description;

    public static Roles toEnum(Integer code) {
        if (code == null) {
            return null;
        }

        for (Roles profile : Roles.values()) {
            if (code.equals(profile.getCode())) {
                return profile;
            }
        }

        throw new IllegalArgumentException("Invalid role: " + code);
    }

    public static Roles toEnum(String description) {
        for (Roles profile : Roles.values()) {
            if (description.equals(profile.getDescription())) {
                return profile;
            }
        }

        throw new IllegalArgumentException("Invalid role: " + description);
    }
}
