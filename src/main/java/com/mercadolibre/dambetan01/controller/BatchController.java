package com.mercadolibre.dambetan01.controller;

import com.mercadolibre.dambetan01.dtos.BatchStockDTO;
import com.mercadolibre.dambetan01.dtos.request.InboundOrderRequestDTO;
import com.mercadolibre.dambetan01.dtos.response.BatchStockResponseDTO;
import com.mercadolibre.dambetan01.service.crud.BatchService;
import com.mercadolibre.dambetan01.service.crud.ProductService;
import com.mercadolibre.dambetan01.service.crud.SectionService;
import com.mercadolibre.dambetan01.service.crud.WarehouseService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/fresh-products")
public class BatchController {

    BatchService batchService;
    ProductService productService;
    WarehouseService warehouseService;
    SectionService sectionService;

    public BatchController(final BatchService batchService, final ProductService productService,
                           final WarehouseService warehouseService, final SectionService sectionService) {
        this.batchService = batchService;
        this.productService = productService;
        this.warehouseService = warehouseService;
        this.sectionService = sectionService;
    }

    //    ml-insert-batch-in-fulfillment-warehouse-01
    @PostMapping("/inboundorder")
    public ResponseEntity<BatchStockResponseDTO> registerNewBatch(@RequestBody InboundOrderRequestDTO inboundOrderRequestDTO) {
        List<Long> productIds = inboundOrderRequestDTO.getBatchStock().stream()
                .map(BatchStockDTO::getProductId)
                .collect(Collectors.toList());

        Integer totalInboundOrderSize = inboundOrderRequestDTO.getBatchStock().stream()
                .map(BatchStockDTO::getInitialQuantity)
                .reduce(0, Integer::sum);

        UUID warehouseCode = inboundOrderRequestDTO.getSection().getWarehouseCode();
        UUID sectionCode = inboundOrderRequestDTO.getSection().getSectionCode();

        productService.checkProductIdInsideBatchStock(productIds);
        warehouseService.warehouseExists(warehouseCode);
        sectionService.sectionExists(sectionCode);
        sectionService.sectionBelongsToWarehouse(sectionCode, warehouseCode);
        sectionService.sectionMatchesProductType(sectionCode, productIds);
        sectionService.sectionHasSuficientSpace(totalInboundOrderSize, sectionCode);
        //validar se o representante pertence ao armazem -> validacao de usuario

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
