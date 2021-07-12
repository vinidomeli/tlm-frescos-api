package com.mercadolibre.dambetan01.controller;

import com.mercadolibre.dambetan01.dtos.ProductRegisterDTO;
import com.mercadolibre.dambetan01.dtos.response.ProductFromSellerDTO;
import com.mercadolibre.dambetan01.dtos.response.ProductResponseDTO;
import com.mercadolibre.dambetan01.service.crud.*;
import com.mercadolibre.dambetan01.service.impl.SessionServiceImpl;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/fresh-products")
@Tag(name = "Product Operations")
public class ProductController {

    final ProductService productService;
    final UserService userService;

    public ProductController(ProductService productService, UserService userService) {
        this.productService = productService;
        this.userService = userService;
    }

    @Operation(summary = "Get all products", description = "Complete list of products")
    @GetMapping("/")
    public ResponseEntity<List<ProductResponseDTO>> listAllProducts() {
      List<ProductResponseDTO> productsList = productService.listAllProducts();
      return new ResponseEntity<>(productsList, HttpStatus.OK);
    }

    @Operation(summary = "Create a product", description = "Add a new product - *Only Seller can create a new Product*")
    @PostMapping("/")
    public ResponseEntity<ProductRegisterDTO> createProduct(@RequestHeader String token,
                                                            @RequestBody @Valid ProductRegisterDTO productRegisterDTO) {
        String username = SessionServiceImpl.getUsername(token);
        UUID userId = userService.findByLogin(username).getId();

        ProductRegisterDTO response = productService.createProduct(productRegisterDTO, userId);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @Operation(summary = "Get products by category", description = "Complete list of products by type")
    @GetMapping("/list")
    public ResponseEntity<List<ProductResponseDTO>> listProductsByCategory(@RequestParam String productType) {
        List<ProductResponseDTO> productsType = productService.listProductsByCategory(productType);
        return new ResponseEntity<>(productsType, HttpStatus.OK);
    }

    @Operation(summary = "Get products from seller", description = "Complete list of products from seller")
    @GetMapping("/myproducts")
    public ResponseEntity<List<ProductFromSellerDTO>> listProductsFromSeller(@RequestHeader String token) {
        String username = SessionServiceImpl.getUsername(token);
        UUID userId = userService.findByLogin(username).getId();

        List<ProductFromSellerDTO> response = productService.listProductsFromSeller(userId);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

}
