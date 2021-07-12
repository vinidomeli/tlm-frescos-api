package com.mercadolibre.dambetan01.controller;

import com.mercadolibre.dambetan01.dtos.response.BatchStockDueDateDTO;
import com.mercadolibre.dambetan01.dtos.response.ProductBatchesResponseDTO;
import com.mercadolibre.dambetan01.service.crud.BatchService;
import com.mercadolibre.dambetan01.service.crud.ProductService;
import com.mercadolibre.dambetan01.service.crud.WarehouseService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.Min;
import java.util.List;

@RestController
@RequestMapping("/api/v1/fresh-products/batch")
@Validated
@Tag(name = "Batch Operations")
public class BatchController {

    private final BatchService batchService;
    private final ProductService productService;
    private final WarehouseService warehouseService;

    public BatchController(BatchService batchService, ProductService productService, WarehouseService warehouseService) {
        this.batchService = batchService;
        this.productService = productService;
        this.warehouseService = warehouseService;
    }

    @Operation(summary = "Get batches by product id", description = "Get all batches where the product is valid")
    @GetMapping(value = "/list")
    public ResponseEntity<List<ProductBatchesResponseDTO>> findBatchesByProductId(@RequestParam Long productId, @RequestParam(required = false) String order) {

        List<ProductBatchesResponseDTO> productBatchesResponseDTOS = batchService.findBatchesByProductId(productId, order);

        return new ResponseEntity<>(productBatchesResponseDTOS, HttpStatus.OK);
    }

    @Operation(summary = "Get batches by ordered by due date", description = "Get all batches stored in a sector of a warehouse sorted by their expiration date.")
    @GetMapping(value = "/due-date")
    public ResponseEntity<BatchStockDueDateDTO> getBatchStockOrderedByDueDate(@RequestParam Integer numberOfDays) {
        BatchStockDueDateDTO response = warehouseService.getAllBatchesWarehouse(numberOfDays);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @Operation(summary = "Get batches by productType ordered by due date", description = "Get a list of batches sorted by expiration date that belong to a particular product category.")
    @GetMapping(value = "/due-date/list")
    public ResponseEntity<BatchStockDueDateDTO> getBatchStockOrderedByDueDate(@RequestParam @Min(value = 0, message = "Number of days must be greater than or equals zero") Integer numberOfDays,
                                                                              @RequestParam String productType,
                                                                              @RequestParam String order) {
        BatchStockDueDateDTO response = warehouseService.getAllBatchesWarehouseByCategory(numberOfDays, productType, order);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

}
