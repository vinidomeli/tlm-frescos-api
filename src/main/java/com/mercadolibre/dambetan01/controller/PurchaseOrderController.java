package com.mercadolibre.dambetan01.controller;

import com.mercadolibre.dambetan01.dtos.response.PurchaseOrderResponseDTO;
import com.mercadolibre.dambetan01.service.crud.PurchaseOrderContentService;
import com.mercadolibre.dambetan01.service.crud.UserService;
import com.mercadolibre.dambetan01.service.impl.SessionServiceImpl;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/purchase-order")
@Tag(name = "Purchase Order Operations")
public class PurchaseOrderController {

    final PurchaseOrderContentService purchaseOrderContentService;
    final UserService userService;

    public PurchaseOrderController(PurchaseOrderContentService purchaseOrderContentService, UserService userService) {
        this.purchaseOrderContentService = purchaseOrderContentService;
        this.userService = userService;
    }

    @Operation(summary = "Register Order", description = "Register Order")
    @PostMapping("/create")
    public ResponseEntity<PurchaseOrderResponseDTO> registerOrder(@RequestHeader String token) {
        String username = SessionServiceImpl.getUsername(token);
        UUID userId = userService.findByLogin(username).getId();
        PurchaseOrderResponseDTO response = purchaseOrderContentService.createOrder(userId);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @Operation(summary = "List orders", description = "List user orders")
    @GetMapping("/list")
    public ResponseEntity<List<PurchaseOrderResponseDTO>> listOrders(@RequestHeader String token) {
        String username = SessionServiceImpl.getUsername(token);
        UUID userId = userService.findByLogin(username).getId();
        List<PurchaseOrderResponseDTO> response = purchaseOrderContentService.listOrders(userId);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

}
