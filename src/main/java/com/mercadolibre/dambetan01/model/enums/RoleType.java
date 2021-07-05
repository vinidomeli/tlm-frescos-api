package com.mercadolibre.dambetan01.model.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum RoleType {

    SUPERVISOR(1, "Supervisor"),
    SELLER(2, "Seller"),
    BUYER(3, "Buyer");

    private Integer code;
    private String description;

    public static RoleType toEnum(Integer code) {
        if (code == null) {
            return null;
        }

        for (RoleType profile : RoleType.values()) {
            if (code.equals(profile.getCode())) {
                return profile;
            }
        }

        throw new IllegalArgumentException("Profile not valid: " + code);
    }
}
