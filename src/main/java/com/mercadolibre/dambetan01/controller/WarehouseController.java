package com.mercadolibre.dambetan01.controller;

import com.mercadolibre.dambetan01.dtos.WarehouseDTO;
import com.mercadolibre.dambetan01.service.crud.WarehouseService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/warehouse")
public class WarehouseController {

    WarehouseService warehouseService;

    public WarehouseController(WarehouseService warehouseService) {
        this.warehouseService = warehouseService;
    }

    @GetMapping("/list")
    public ResponseEntity<List<WarehouseDTO>> findAllWarehouses() {
        List<WarehouseDTO> response = this.warehouseService.findAll();

        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}