package com.mercadolibre.dambetan01.controller;

import com.mercadolibre.dambetan01.dtos.response.PurchaseOrderResponseDTO;
import com.mercadolibre.dambetan01.service.crud.PurchaseOrderContentService;
import com.mercadolibre.dambetan01.service.crud.UserService;
import com.mercadolibre.dambetan01.service.impl.SessionServiceImpl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
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

    @PostMapping("/create")
    public ResponseEntity<PurchaseOrderResponseDTO> registerOrder(@RequestHeader String token) {
        String username = SessionServiceImpl.getUsername(token);
        UUID userId = userService.findByLogin(username).getId();
        PurchaseOrderResponseDTO response = purchaseOrderContentService.createOrder(userId);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/list")
    public ResponseEntity<List<PurchaseOrderResponseDTO>> listOrders(@RequestHeader String token) {
        String username = SessionServiceImpl.getUsername(token);
        UUID userId = userService.findByLogin(username).getId();
        List<PurchaseOrderResponseDTO> response = purchaseOrderContentService.listOrders(userId);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

}
