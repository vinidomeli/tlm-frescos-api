package com.mercadolibre.dambetan01.controller;

import com.mercadolibre.dambetan01.dtos.response.ProductResponseDTO;
import com.mercadolibre.dambetan01.service.crud.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/fresh-products")
public class ProductController {

    ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    //ml-add-product ts-to-cart-01
    //[REQ-02] GET: Complete list of products
    //@PreAuthorize("hasAnyRole('BUYER')")
    @GetMapping("/")
    public ResponseEntity<List<ProductResponseDTO>> listAllProducts() {
      List<ProductResponseDTO> productsList = productService.listAllProducts();
      return new ResponseEntity<>(productsList, HttpStatus.OK);
    }

    // ml-add-product ts-to-cart-01
    //[REQ-02] GET: Complete list of products by type
    //@PreAuthorize("hasAnyRole('BUYER')")
    @GetMapping("/list")
    public ResponseEntity<List<ProductResponseDTO>> listProductsByCategory(@RequestParam String productType) {
        List<ProductResponseDTO> productsType = productService.listProductsByCategory(productType);
        return new ResponseEntity<>(productsType, HttpStatus.OK);
    }

}
