package com.mercadolibre.dambetan01.util;

import com.github.javafaker.Faker;
import com.mercadolibre.dambetan01.model.Product;
import com.mercadolibre.dambetan01.model.enums.ProductType;
import com.mercadolibre.dambetan01.repository.ProductRepository;
import com.mercadolibre.dambetan01.repository.SellerRepository;

import static com.fasterxml.jackson.databind.jsonFormatVisitors.JsonValueFormat.UUID;

public class ProductDB {

    Faker faker = new Faker();

    SellerRepository sellerRepository;
    ProductRepository productRepository;

    public ProductDB(SellerRepository sellerRepository, ProductRepository productRepository) {
        this.sellerRepository = sellerRepository;
        this.productRepository = productRepository;
    }

    public Long insertProduct(String cnpj, ProductType type) {

        Product product = Product.builder()
                .seller(sellerRepository.findByCnpj(cnpj).get())
                .price(faker.number().randomDouble(2,100, 1000))
                .type(type.getDescription())
                .build();

        return productRepository.save(product).getId();
    }

}
