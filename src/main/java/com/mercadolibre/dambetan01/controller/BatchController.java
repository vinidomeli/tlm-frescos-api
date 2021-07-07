package com.mercadolibre.dambetan01.controller;

import com.mercadolibre.dambetan01.dtos.BatchStockDTO;
import com.mercadolibre.dambetan01.dtos.SectionDTO;
import com.mercadolibre.dambetan01.dtos.response.BatchStockDueDateDTO;
import com.mercadolibre.dambetan01.dtos.response.ProductBatchesResponseDTO;
import com.mercadolibre.dambetan01.exceptions.NotFoundException;
import com.mercadolibre.dambetan01.model.Batch;
import com.mercadolibre.dambetan01.model.Product;
import com.mercadolibre.dambetan01.service.crud.impl.BatchServiceImpl;
import com.mercadolibre.dambetan01.service.crud.impl.ProductServiceImpl;
import com.mercadolibre.dambetan01.service.crud.impl.WarehouseServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.Min;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/fresh-products")
@Validated
public class BatchController {

    private final BatchServiceImpl batchService;
    private final ProductServiceImpl productService;
    private final WarehouseServiceImpl warehouseService;

    @Autowired
    public BatchController(BatchServiceImpl batchService, ProductServiceImpl productService, WarehouseServiceImpl warehouseService) {
        this.batchService = batchService;
        this.productService = productService;
        this.warehouseService = warehouseService;
    }

    @GetMapping(value = "/list")
    public ResponseEntity<ProductBatchesResponseDTO> findBatchesByProductId(@RequestParam List<String> querytype) {

        HashMap<UUID, ProductBatchesResponseDTO> mapBatches = new HashMap<>();

        try {
            List<Batch> batches = batchService.findBatchesByProductId(querytype);
            batches.forEach(batch -> {
                UUID sectionCode = batch.getInboundOrder().getSection().getSectionCode();
                if(mapBatches.containsKey(sectionCode)) {
                    mapBatches.get(sectionCode).getBatchStock().add(new BatchStockDTO(batch));
                } else {
                    ProductBatchesResponseDTO productBatchesResponseDTO = new ProductBatchesResponseDTO();
                    productBatchesResponseDTO.setProductId(Long.parseLong(querytype.get(0)));
                    productBatchesResponseDTO.setSectionDTO(new SectionDTO(sectionCode, batch.getInboundOrder().getSection().getWarehouse().getWarehouseCode()));
                    productBatchesResponseDTO.getBatchStock().add(new BatchStockDTO(batch));

                    mapBatches.put(sectionCode, productBatchesResponseDTO);
                }
            });
        } catch (NotFoundException e) {

        }

        return new ResponseEntity(mapBatches.values(), HttpStatus.OK);
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

}
