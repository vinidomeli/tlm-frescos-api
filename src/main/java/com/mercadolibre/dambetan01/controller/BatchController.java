package com.mercadolibre.dambetan01.controller;

import com.mercadolibre.dambetan01.dtos.BatchStockDTO;
import com.mercadolibre.dambetan01.dtos.SectionDTO;
import com.mercadolibre.dambetan01.dtos.response.BatchStockDueDateDTO;
import com.mercadolibre.dambetan01.dtos.response.ProductBatchesResponseDTO;
import com.mercadolibre.dambetan01.dtos.response.ProductInWarehousesDTO;
import com.mercadolibre.dambetan01.exceptions.NotFoundException;
import com.mercadolibre.dambetan01.model.Batch;
import com.mercadolibre.dambetan01.service.crud.BatchService;
import com.mercadolibre.dambetan01.service.crud.ProductService;
import com.mercadolibre.dambetan01.service.crud.WarehouseService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.Min;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/fresh-products")
@Validated
public class BatchController {

    private final BatchService batchService;
    private final ProductService productService;
    private final WarehouseService warehouseService;

    public BatchController(BatchService batchService, ProductService productService, WarehouseService warehouseService) {
        this.batchService = batchService;
        this.productService = productService;
        this.warehouseService = warehouseService;
    }

    @GetMapping(value = "/list")
    public ResponseEntity<ProductBatchesResponseDTO> findBatchesByProductId(@RequestParam Long productId, @RequestParam(required = false) String order) {

        List<ProductBatchesResponseDTO> productBatchesResponseDTOS = batchService.findBatchesByProductId(productId, order);

        return new ResponseEntity(productBatchesResponseDTOS, HttpStatus.OK);
    }

    //ml-check-batch-stock-due-date-01
    //Obtenha todos os lotes armazenados em um setor de um armazém ordenados por sua data de vencimento.
    @GetMapping(value = "/due-date")
    public ResponseEntity<BatchStockDueDateDTO> getBatchStockOrderedByDueDate(@RequestParam Integer numberOfDays) {
        BatchStockDueDateDTO response = warehouseService.getAllBatchesWarehouse(numberOfDays);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    //ml-check-batch-stock-due-date-01
    //Obtenha uma lista de lotes ordenados por data de validade, que pertencem a uma determinada categoria de produto.
    @GetMapping(value = "/due-date/list")
    public ResponseEntity<BatchStockDueDateDTO> getBatchStockOrderedByDueDate(@RequestParam @Min(value = 0, message = "Number of days must be greater than or equals zero") Integer numberOfDays,
                                                                              @RequestParam String productType,
                                                                              @RequestParam String order) {
        BatchStockDueDateDTO response = warehouseService.getAllBatchesWarehouseByCategory(numberOfDays, productType, order);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/warehouse")
    public ResponseEntity<ProductInWarehousesDTO> findProductInWarehouses(@RequestParam Long productId) {
        ProductInWarehousesDTO response = this.batchService.findProductInWarehousesBy(productId);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
