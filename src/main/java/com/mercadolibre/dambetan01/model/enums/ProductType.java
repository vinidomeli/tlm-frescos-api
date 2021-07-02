package com.mercadolibre.dambetan01.model.enums;

import lombok.Getter;

@Getter
public enum ProductType {

    REFRIGERATE(1, "Refrigerate"),
    DRINKS(2, "Drinks"),
    FRUITS(3, "Fruits"),
    FROZEN(4, "Frozen");

    private Integer code;
    private String description;

    private ProductType(Integer code, String description) {
        this.code = code;
        this.description = description;
    }

    public static ProductType toEnum(Integer code) {
        if (code == null) {
            return null;
        }

        for (ProductType productType : ProductType.values()) {
            if (code.equals(productType.getCode())) {
                return productType;
            }
        }

        throw new IllegalArgumentException("Product type not valid: " + code);
    }
}
