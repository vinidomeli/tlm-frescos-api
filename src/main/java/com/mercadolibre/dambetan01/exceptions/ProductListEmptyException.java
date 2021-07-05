package com.mercadolibre.dambetan01.exceptions;

public class ProductListEmptyException extends RuntimeException {

    public ProductListEmptyException(String msg) {
        super(msg);
    }

//    public ProductListEmptyException(String msg, Throwable cause) {
//        super(msg, cause);
//    }
}
