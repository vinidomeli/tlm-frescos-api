package com.mercadolibre.dambetan01.controller;

import com.mercadolibre.dambetan01.dtos.request.CartContentRequestDTO;
import com.mercadolibre.dambetan01.dtos.response.CartResponseDTO;
import com.mercadolibre.dambetan01.service.crud.CartContentService;
import com.mercadolibre.dambetan01.service.crud.UserService;
import com.mercadolibre.dambetan01.service.impl.SessionServiceImpl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.UUID;

@RestController
@RequestMapping("/cart")
public class CartController {

    CartContentService cartContentService;
    UserService userService;

    public CartController(CartContentService cartContentService, UserService userService) {
        this.cartContentService = cartContentService;
        this.userService = userService;
    }

    @PostMapping("/add")
    public ResponseEntity<CartResponseDTO> addToCart(@RequestHeader String token,
                                                     @RequestBody @Valid CartContentRequestDTO cartContentRequestDTO) {
        String username = SessionServiceImpl.getUsername(token);
        UUID userId = userService.findByLogin(username).getId();
        CartResponseDTO response = cartContentService.addToCart(userId, cartContentRequestDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @DeleteMapping("/remove/{cartContentId}")
    public ResponseEntity<CartResponseDTO> removeToCart(@RequestHeader String token, @PathVariable Long cartContentId) {
        String username = SessionServiceImpl.getUsername(token);
        UUID userId = userService.findByLogin(username).getId();
        CartResponseDTO response = cartContentService.removeToCart(userId, cartContentId);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @GetMapping
    public ResponseEntity<CartResponseDTO> listAllCarts(@RequestHeader String token) {
        String username = SessionServiceImpl.getUsername(token);
        UUID userId = userService.findByLogin(username).getId();
        CartResponseDTO response = cartContentService.listAllCarts(userId);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

}
