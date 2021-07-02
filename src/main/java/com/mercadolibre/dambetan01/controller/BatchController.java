package com.mercadolibre.dambetan01.controller;

import com.mercadolibre.dambetan01.service.crud.BatchService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/fresh-products")
public class BatchController {

    BatchService batchService;

    public BatchController(BatchService batchService) {
        this.batchService = batchService;
    }

    //    ml-insert-batch-in-fulfillment-warehouse-01
    @PostMapping("/inboundorder")
    public ResponseEntity<BatchStockResponseDTO> registerNewBatch(@RequestBody InboundOrderRequestDTO inboundOrderRequestDTO) {
        BatchStockResponseDTO response = batchService.registerNewBatch(inboundOrderRequestDTO);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    //    ml-insert-batch-in-fulfillment-warehouse-01
    @PutMapping("/inboundorder")
    public ResponseEntity<BatchStockResponseDTO> updateBatch(@RequestBody InboundOrderRequestDTO inboundOrderRequestDTO) {
        BatchStockResponseDTO response = batchService.updateBatch(inboundOrderRequestDTO);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }
}
