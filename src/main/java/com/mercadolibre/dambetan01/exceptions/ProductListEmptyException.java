package com.mercadolibre.dambetan01.exceptions;

public class ProductListEmptyException extends RuntimeException {

    public ProductListEmptyException() {
        super("Product's list is empty.");
    }

//    public ProductListEmptyException(String msg, Throwable cause) {
//        super(msg, cause);
//    }
}
