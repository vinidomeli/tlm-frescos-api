package com.mercadolibre.dambetan01.controller;

import com.mercadolibre.dambetan01.dtos.BatchStockDTO;
import com.mercadolibre.dambetan01.dtos.SectionDTO;
import com.mercadolibre.dambetan01.dtos.response.ProductBatchesResponseDTO;
import com.mercadolibre.dambetan01.dtos.response.ProductInWarehousesDTO;
import com.mercadolibre.dambetan01.exceptions.NotFoundException;
import com.mercadolibre.dambetan01.model.Batch;
import com.mercadolibre.dambetan01.service.crud.BatchService;
import com.mercadolibre.dambetan01.service.crud.ProductService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/fresh-products")
public class BatchController {

    private final BatchService batchService;
    private final ProductService productService;

    public BatchController(BatchService batchService, ProductService productService) {
        this.batchService = batchService;
        this.productService = productService;
    }

    @GetMapping(value = "/list")
    public ResponseEntity<ProductBatchesResponseDTO> findBatchesByProductId(@RequestParam List<String> querytype) {

        HashMap<UUID, ProductBatchesResponseDTO> mapBatches = new HashMap<>();

        try {
            List<Batch> batches = this.batchService.findBatchesByProductId(querytype);
            batches.forEach(batch -> {
                UUID sectionCode = batch.getInboundOrder().getSection().getSectionCode();
                if (mapBatches.containsKey(sectionCode)) {
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

    @GetMapping("/warehouse")
    public ResponseEntity<ProductInWarehousesDTO> findProductInWarehouses(@RequestParam Long productId) {
        ProductInWarehousesDTO response = this.batchService.findProductInWarehousesBy(productId);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
