package com.mercadolibre.dambetan01.model.enums;

import lombok.Getter;

@Getter
public enum Profile {

    SUPERVISOR(1, "Supervisor"),
    SELLER(2, "Seller"),
    BUYER(3, "Buyer");

    private Integer code;
    private String description;

    private Profile(Integer code, String description) {
        this.code = code;
        this.description = description;
    }

    public static Profile toEnum(Integer code) {
        if (code == null) {
            return null;
        }

        for (Profile profile : Profile.values()) {
            if (code.equals(profile.getCode())) {
                return profile;
            }
        }

        throw new IllegalArgumentException("Profile not valid: " + code);
    }
}
