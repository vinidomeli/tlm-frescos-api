package com.mercadolibre.dambetan01.controller;

import com.mercadolibre.dambetan01.dtos.BatchStockDTO;
import com.mercadolibre.dambetan01.dtos.SectionDTO;
import com.mercadolibre.dambetan01.dtos.response.ProductBatchesResponseDTO;
import com.mercadolibre.dambetan01.exceptions.NotFoundException;
import com.mercadolibre.dambetan01.model.Batch;
import com.mercadolibre.dambetan01.model.Product;
import com.mercadolibre.dambetan01.service.crud.impl.BatchServiceImpl;
import com.mercadolibre.dambetan01.service.crud.impl.ProductServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/fresh-products")
public class BatchController {

    private final BatchServiceImpl batchService;
    private final ProductServiceImpl productService;

    @Autowired
    public BatchController(BatchServiceImpl batchService, ProductServiceImpl productService) {
        this.batchService = batchService;
        this.productService = productService;
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

}
