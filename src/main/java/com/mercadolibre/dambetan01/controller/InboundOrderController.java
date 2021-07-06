package com.mercadolibre.dambetan01.controller;

import com.mercadolibre.dambetan01.dtos.BatchStockDTO;
import com.mercadolibre.dambetan01.dtos.UpdateBatchStockDTO;
import com.mercadolibre.dambetan01.dtos.request.InboundOrderRequestDTO;
import com.mercadolibre.dambetan01.dtos.request.UpdateInboundOrderRequestDTO;
import com.mercadolibre.dambetan01.dtos.response.BatchStockResponseDTO;
import com.mercadolibre.dambetan01.model.User;
import com.mercadolibre.dambetan01.service.crud.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/fresh-products")
public class InboundOrderController {

    InboundOrderService inboundOrderService;
    ProductService productService;
    WarehouseService warehouseService;
    SectionService sectionService;
    BatchService batchService;

    public InboundOrderController(final InboundOrderService inboundOrderService, final ProductService productService,
                                  final WarehouseService warehouseService, final SectionService sectionService,
                                  final BatchService batchService) {
        this.inboundOrderService = inboundOrderService;
        this.productService = productService;
        this.warehouseService = warehouseService;
        this.sectionService = sectionService;
        this.batchService = batchService;
    }

    //    ml-insert-batch-in-fulfillment-warehouse-01
    @PostMapping(value = "/inboundorder")
    public ResponseEntity<BatchStockResponseDTO> registerNewInboundOrder(@RequestBody @Valid InboundOrderRequestDTO inboundOrderRequestDTO) {

        List<Long> productIds = inboundOrderRequestDTO.getBatchStock().stream()
                .map(BatchStockDTO::getProductId)
                .collect(Collectors.toList());

        Integer totalInboundOrderSize = inboundOrderRequestDTO.getBatchStock().stream()
                .map(BatchStockDTO::getCurrentQuantity)
                .reduce(0, Integer::sum);

        UUID warehouseCode = inboundOrderRequestDTO.getSection().getWarehouseCode();
        UUID sectionCode = inboundOrderRequestDTO.getSection().getSectionCode();

        productService.productIdsInsideBatchStockExist(productIds);
        warehouseService.warehouseExists(warehouseCode);
        sectionService.sectionExists(sectionCode);
        sectionService.sectionBelongsToWarehouse(sectionCode, warehouseCode);
        sectionService.sectionMatchesProductType(sectionCode, productIds);
        sectionService.sectionHasSufficientSpace(totalInboundOrderSize, sectionCode);
        //validar se o representante pertence ao armazem -> validacao de usuario

        BatchStockResponseDTO response = inboundOrderService.registerNewInboundOrder(inboundOrderRequestDTO);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    //    ml-insert-batch-in-fulfillment-warehouse-01
    @PutMapping("/inboundorder")
    public ResponseEntity<BatchStockResponseDTO> updateInboundOrder(@RequestBody UpdateInboundOrderRequestDTO updateInboundOrderRequestDTO) {

        List<Long> productIds = updateInboundOrderRequestDTO.getBatchStock().stream()
                .map(UpdateBatchStockDTO::getProductId)
                .collect(Collectors.toList());

        List<Long> batchNumbers = updateInboundOrderRequestDTO.getBatchStock().stream()
                .map(UpdateBatchStockDTO::getBatchNumber)
                .collect(Collectors.toList());

        Integer totalInboundOrderSize = updateInboundOrderRequestDTO.getBatchStock().stream()
                .map(UpdateBatchStockDTO::getCurrentQuantity)
                .reduce(0, Integer::sum);

        UUID warehouseCode = updateInboundOrderRequestDTO.getSection().getWarehouseCode();
        UUID sectionCode = updateInboundOrderRequestDTO.getSection().getSectionCode();
        Long orderNumber = updateInboundOrderRequestDTO.getOrderNumber();


        inboundOrderService.orderNumberExists(orderNumber);
        inboundOrderService.inboundOrderContainsSectionCode(orderNumber, sectionCode);
        sectionService.sectionExists(sectionCode);
        warehouseService.warehouseExists(warehouseCode);
        sectionService.sectionBelongsToWarehouse(sectionCode, warehouseCode);
        sectionService.sectionMatchesProductType(sectionCode, productIds);
        sectionService.sectionHasSufficientSpace(totalInboundOrderSize, sectionCode);
        batchService.batchNumbersExist(batchNumbers);
        inboundOrderService.inboundOrderContainsBatchNumbers(orderNumber, batchNumbers);

        BatchStockResponseDTO response = inboundOrderService.updateInboundOrder(updateInboundOrderRequestDTO);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }
}
