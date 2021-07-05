package com.mercadolibre.dambetan01.controller;

import com.mercadolibre.dambetan01.dtos.response.ProductResponseDTO;
import com.mercadolibre.dambetan01.model.enums.ProductType;
import com.mercadolibre.dambetan01.service.crud.PurchaseService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/fresh-products")
public class PurchaseController {

    PurchaseService purchaseService;

    public PurchaseController(PurchaseService purchaseService) {
        this.purchaseService = purchaseService;
    }

    //ml-add-product ts-to-cart-01
    //[REQ-02] GET: Complete list of products

    //@PreAuthorize("hasAnyRole('BUYER')")
    @GetMapping("/")
    public ResponseEntity<List<ProductResponseDTO>> listAllProducts() {
        List<ProductResponseDTO> productsList = purchaseService.listAllProducts();
        return new ResponseEntity<>(productsList, HttpStatus.OK);
    }

    // ml-add-product ts-to-cart-01

    //@PreAuthorize("hasAnyRole('BUYER')")
    @GetMapping("/")
    public ResponseEntity<List<ProductResponseDTO>> listProductsByCategory(@RequestParam(required = true) ProductType productType) {
        List<ProductResponseDTO> productsType = purchaseService.listProductsByCategory(productType);
        return new ResponseEntity<>(productsType, HttpStatus.OK);
    }
}
