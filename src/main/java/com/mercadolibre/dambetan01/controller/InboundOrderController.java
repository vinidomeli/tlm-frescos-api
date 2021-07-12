package com.mercadolibre.dambetan01.controller;

import com.mercadolibre.dambetan01.dtos.BatchStockDTO;
import com.mercadolibre.dambetan01.dtos.UpdateBatchStockDTO;
import com.mercadolibre.dambetan01.dtos.request.InboundOrderRequestDTO;
import com.mercadolibre.dambetan01.dtos.request.UpdateInboundOrderDTO;
import com.mercadolibre.dambetan01.dtos.response.BatchStockResponseDTO;
import com.mercadolibre.dambetan01.service.crud.*;
import com.mercadolibre.dambetan01.service.impl.SessionServiceImpl;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/fresh-products/inboundorder")
@Tag(name = "Inbound Order Operations")
public class InboundOrderController {

    final InboundOrderService inboundOrderService;
    final ProductService productService;
    final WarehouseService warehouseService;
    final SectionService sectionService;
    final BatchService batchService;
    final UserService userService;

    public InboundOrderController(InboundOrderService inboundOrderService, ProductService productService, WarehouseService warehouseService, SectionService sectionService, BatchService batchService, UserService userService) {
        this.inboundOrderService = inboundOrderService;
        this.productService = productService;
        this.warehouseService = warehouseService;
        this.sectionService = sectionService;
        this.batchService = batchService;
        this.userService = userService;
    }

    @Operation(summary = "Register A New Inbound Order", description = "Register A New Inbound Order")
    @PostMapping(value = "/")
    public ResponseEntity<BatchStockResponseDTO> registerNewInboundOrder(@RequestHeader String token,
                                                                         @RequestBody @Valid InboundOrderRequestDTO inboundOrderRequestDTO) {
        String username = SessionServiceImpl.getUsername(token);
        UUID userId = userService.findByLogin(username).getId();
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
        warehouseService.warehouseContainsSupervisor(userId, warehouseCode);
        sectionService.sectionExists(sectionCode);
        sectionService.sectionBelongsToWarehouse(sectionCode, warehouseCode);
        sectionService.sectionMatchesProductType(sectionCode, productIds);
        sectionService.sectionHasSufficientSpace(totalInboundOrderSize, sectionCode);

        BatchStockResponseDTO response = inboundOrderService.registerNewInboundOrder(inboundOrderRequestDTO);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @Operation(summary = "Update Inbound Order", description = "Update Inbound Order")
    @PutMapping("/")
    public ResponseEntity<BatchStockResponseDTO> updateInboundOrder(@RequestHeader String token,
                                                                    @RequestBody @Valid UpdateInboundOrderDTO updateInboundOrderDTO) {
        String username = SessionServiceImpl.getUsername(token);
        UUID userId = userService.findByLogin(username).getId();
        List<Long> productIds = updateInboundOrderDTO.getBatchStock().stream()
                .map(UpdateBatchStockDTO::getProductId)
                .collect(Collectors.toList());

        List<Long> batchNumbers = updateInboundOrderDTO.getBatchStock().stream()
                .map(UpdateBatchStockDTO::getBatchNumber)
                .collect(Collectors.toList());

        Integer batchStockSizeDifferenceAfterUpdate = inboundOrderService
                .batchStockSizeDifferenceAfterUpdate(updateInboundOrderDTO);

        UUID warehouseCode = updateInboundOrderDTO.getSection().getWarehouseCode();
        UUID sectionCode = updateInboundOrderDTO.getSection().getSectionCode();
        Long orderNumber = updateInboundOrderDTO.getOrderNumber();

        inboundOrderService.orderNumberExists(orderNumber);
        inboundOrderService.inboundOrderContainsSectionCode(orderNumber, sectionCode);
        sectionService.sectionExists(sectionCode);
        warehouseService.warehouseExists(warehouseCode);
        warehouseService.warehouseContainsSupervisor(userId, warehouseCode);
        sectionService.sectionBelongsToWarehouse(sectionCode, warehouseCode);
        productService.productIdsInsideBatchStockExist(productIds);
        sectionService.sectionMatchesProductType(sectionCode, productIds);
        sectionService.sectionHasSufficientSpace(batchStockSizeDifferenceAfterUpdate, sectionCode);
        batchService.batchNumbersExist(batchNumbers);
        inboundOrderService.inboundOrderContainsBatchNumbers(orderNumber, batchNumbers);

        BatchStockResponseDTO response = inboundOrderService.updateInboundOrder(updateInboundOrderDTO);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @Operation(summary = "List Inbound Orders From A Supervisor", description = "Using the token, list all inbound orders of a specific supervisor")
    @GetMapping("/list")
    public ResponseEntity<List<UpdateInboundOrderDTO>> listInboundOrderFromSupervisor(@RequestHeader String token) {
        String username = SessionServiceImpl.getUsername(token);
        UUID userId = userService.findByLogin(username).getId();
        List<UpdateInboundOrderDTO> response = inboundOrderService.listInboundOrderFromSupervisor(userId);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
