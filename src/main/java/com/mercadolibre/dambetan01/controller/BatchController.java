package com.mercadolibre.dambetan01.controller;

import com.mercadolibre.dambetan01.dtos.BatchStockDTO;
import com.mercadolibre.dambetan01.dtos.response.ProductBatchesResponseDTO;
import com.mercadolibre.dambetan01.model.Batch;
import com.mercadolibre.dambetan01.model.Product;
import com.mercadolibre.dambetan01.service.crud.impl.BatchServiceImpl;
import com.mercadolibre.dambetan01.service.crud.impl.ProductServiceImpl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/fresh-products")
public class BatchController {

    private final BatchServiceImpl batchService;
    private final ProductServiceImpl productService;

    public BatchController(BatchServiceImpl batchService, ProductServiceImpl productService) {
        this.batchService = batchService;
        this.productService = productService;
    }


    @GetMapping(value = "/{productId}")
    public ResponseEntity<ProductBatchesResponseDTO> findBatchesByProductId(@PathVariable Long productId) {

        ProductBatchesResponseDTO productBatchesResponseDTO = new ProductBatchesResponseDTO();

        List<BatchStockDTO> batches = batchService.findBatchesByProductId(productId).stream().map(batch -> new BatchStockDTO(batch)).collect(Collectors.toList());

        productBatchesResponseDTO.setSectionDTO(null);
        productBatchesResponseDTO.setProductId(productId);
        productBatchesResponseDTO.setBatchStock(batches);

        return new ResponseEntity<>(productBatchesResponseDTO, HttpStatus.OK);
    }

    @PostMapping(value = "/batch/{productId}-{inboundOrder}")
    public void createBatch(@PathVariable("productId") Long productId, @PathVariable("inboundOrder") Long inboundOrder) {

        Product product = productService.findProductById(productId);

        batchService.createBatch(product, null);
    }

}
