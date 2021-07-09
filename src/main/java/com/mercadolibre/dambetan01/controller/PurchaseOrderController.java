package com.mercadolibre.dambetan01.controller;

import com.mercadolibre.dambetan01.dtos.response.PurchaseOrderResponseDTO;
import com.mercadolibre.dambetan01.service.crud.PurchaseOrderContentService;
import com.mercadolibre.dambetan01.service.crud.UserService;
import com.mercadolibre.dambetan01.service.impl.SessionServiceImpl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping("/purchase-order")
public class PurchaseOrderController {

    PurchaseOrderContentService purchaseOrderContentService;
    UserService userService;

    public PurchaseOrderController(PurchaseOrderContentService purchaseOrderContentService, UserService userService) {
        this.purchaseOrderContentService = purchaseOrderContentService;
        this.userService = userService;
    }

    @PostMapping
    public ResponseEntity<PurchaseOrderResponseDTO> registerOrder(@RequestHeader String token) {
        String username = SessionServiceImpl.getUsername(token);
        UUID userId = userService.findByLogin(username).getId();
        PurchaseOrderResponseDTO response = purchaseOrderContentService.createOrder(userId);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

}
