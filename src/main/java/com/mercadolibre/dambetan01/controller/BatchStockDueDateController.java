package com.mercadolibre.dambetan01.controller;

import com.mercadolibre.dambetan01.dtos.response.BatchStockDueDateDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/fresh-products")
public class BatchStockDueDateController {

    //ml-check-batch-stock-due-date-01
    //Obtenha todos os lotes armazenados em um setor de um armazém ordenados por sua data de vencimento.
    @GetMapping(value = "/due-date")
    public ResponseEntity<BatchStockDueDateDTO> getBatchStockOrderedByDueDate(@RequestParam Integer numberOfDays) {
        BatchStockDueDateDTO response = null;
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    //ml-check-batch-stock-due-date-01
    //Obtenha uma lista de lotes ordenados por data de validade, que pertencem a uma determinada categoria de produto.
    @GetMapping(value = "/due-date/list")
    public ResponseEntity<BatchStockDueDateDTO> getBatchStockOrderedByDueDate(@RequestParam Integer numberOfDays,
                                                                              @RequestParam String productType,
                                                                              @RequestParam String order) {
        BatchStockDueDateDTO response = null;
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
