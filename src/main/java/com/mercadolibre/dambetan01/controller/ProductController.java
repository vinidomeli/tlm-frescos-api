package com.mercadolibre.dambetan01.controller;

import com.mercadolibre.dambetan01.dtos.ProductRegisterDTO;
import com.mercadolibre.dambetan01.dtos.response.ProductFromSellerDTO;
import com.mercadolibre.dambetan01.dtos.response.ProductResponseDTO;
import com.mercadolibre.dambetan01.service.crud.*;
import com.mercadolibre.dambetan01.service.impl.SessionServiceImpl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/fresh-products")
public class ProductController {

    ProductService productService;
    UserService userService;

    public ProductController(ProductService productService, UserService userService) {
        this.productService = productService;
        this.userService = userService;
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

    //Only Seller can register a new Product
    @PostMapping("/product")
    public ResponseEntity<ProductRegisterDTO> createProduct(@RequestHeader String token,
                                                            @RequestBody @Valid ProductRegisterDTO productRegisterDTO) {
        String username = SessionServiceImpl.getUsername(token);
        UUID userId = userService.findByLogin(username).getId();

        ProductRegisterDTO response = productService.createProduct(productRegisterDTO, userId);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    //Only Seller can see his products
    @GetMapping("/myproducts")
    public ResponseEntity<List<ProductFromSellerDTO>> listProductsFromSeller(@RequestHeader String token) {
        String username = SessionServiceImpl.getUsername(token);
        UUID userId = userService.findByLogin(username).getId();

        List<ProductFromSellerDTO> response = productService.listProductsFromSeller(userId);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

}
