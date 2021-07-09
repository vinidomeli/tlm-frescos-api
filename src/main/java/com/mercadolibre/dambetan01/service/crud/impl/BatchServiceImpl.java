package com.mercadolibre.dambetan01.service.crud.impl;

import com.mercadolibre.dambetan01.dtos.BatchStockDTO;
import com.mercadolibre.dambetan01.dtos.SectionDTO;
import com.mercadolibre.dambetan01.dtos.WarehouseProductQuantityDTO;
import com.mercadolibre.dambetan01.dtos.response.ProductBatchesResponseDTO;
import com.mercadolibre.dambetan01.dtos.response.ProductInWarehousesDTO;
import com.mercadolibre.dambetan01.exceptions.ApiException;
import com.mercadolibre.dambetan01.exceptions.NotFoundException;
import com.mercadolibre.dambetan01.model.Batch;
import com.mercadolibre.dambetan01.model.InboundOrder;
import com.mercadolibre.dambetan01.model.Product;
import com.mercadolibre.dambetan01.repository.BatchRepository;
import com.mercadolibre.dambetan01.repository.InboundOrderRepository;
import com.mercadolibre.dambetan01.repository.ProductRepository;
import com.mercadolibre.dambetan01.service.crud.BatchService;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class BatchServiceImpl implements BatchService {

    ProductRepository productRepository;
    InboundOrderRepository inboundOrderRepository;
    BatchRepository batchRepository;

    public BatchServiceImpl(ProductRepository productRepository, InboundOrderRepository inboundOrderRepository,
                            BatchRepository batchRepository) {
        this.productRepository = productRepository;
        this.inboundOrderRepository = inboundOrderRepository;
        this.batchRepository = batchRepository;
    }

    @Override
    public List<Batch> findBatchesByProductId(Long productId) {
        Optional<Product> productOptional = this.productRepository.findById(productId);

        if (!productOptional.isPresent()) {
            throw new NotFoundException("Product " + productId + "not found!");
        }

        return this.batchRepository.findBatchesByProduct_Id(productId);
    }

    @Override
    public List<ProductBatchesResponseDTO> findBatchesByProductId(Long productId, String order) throws NotFoundException {
        Optional<Product> productOptional = this.productRepository.findById(productId);
        LocalDate validDueDate = LocalDate.now().plusWeeks(3);
        HashMap<UUID, ProductBatchesResponseDTO> mapBatches = new HashMap<>();

        if (!productOptional.isPresent()) {
            throw new ApiException("404", "Product number " + productId + " doesn't exists", 404);
        }

        List<Batch> batches = null;

        if (order != null && order.equals("C")) {
            batches = this.batchRepository.findBatchesByProduct_IdAndDueDateGreaterThanOrderByCurrentQuantity(productId, validDueDate);
        } else {
            batches = this.batchRepository.findBatchesByProduct_IdAndDueDateGreaterThanOrderByDueDateAsc(productId, validDueDate);
        }

        batches.forEach(batch -> {
            UUID sectionCode = batch.getInboundOrder().getSection().getSectionCode();
            if (mapBatches.containsKey(sectionCode)) {
                mapBatches.get(sectionCode).getBatchStock().add(new BatchStockDTO(batch));
            } else {
                ProductBatchesResponseDTO productBatchesResponseDTO = new ProductBatchesResponseDTO();
                productBatchesResponseDTO.setProductId(productId);
                productBatchesResponseDTO.setSectionDTO(new SectionDTO(sectionCode, batch.getInboundOrder().getSection().getWarehouse().getWarehouseCode()));
                productBatchesResponseDTO.getBatchStock().add(new BatchStockDTO(batch));

                mapBatches.put(sectionCode, productBatchesResponseDTO);
            }
        });

        return mapBatches.values().stream().collect(Collectors.toList());
    }

    @Override
    public Batch convertBatchStockDTOToBatch(BatchStockDTO batchStockDTO, Long inboundOrderNumber) {

        Product product = this.productRepository.findById(batchStockDTO.getProductId()).get();
        InboundOrder inboundOrder = this.inboundOrderRepository.findByOrderNumber(inboundOrderNumber);

        return Batch.builder()
                .productType(product.getType())
                .product(product)
                .inboundOrder(inboundOrder)
                .initialQuantity(batchStockDTO.getInitialQuantity())
                .currentQuantity(batchStockDTO.getCurrentQuantity())
                .minimumTemperature(batchStockDTO.getMinimumTemperature())
                .currentTemperature(batchStockDTO.getCurrentTemperature())
                .manufacturingTime(batchStockDTO.getManufacturingTime())
                .manufacturingDate(batchStockDTO.getManufacturingDate())
                .dueDate(batchStockDTO.getDueDate())
                .build();
    }

    public BatchStockDTO convertBatchToBatchStockDTO(Batch batch) {

        return BatchStockDTO.builder()
                .productId(batch.getProduct().getId())
                .currentTemperature(batch.getCurrentTemperature())
                .minimumTemperature(batch.getMinimumTemperature())
                .initialQuantity(batch.getInitialQuantity())
                .currentQuantity(batch.getCurrentQuantity())
                .manufacturingDate(batch.getManufacturingDate())
                .manufacturingTime(batch.getManufacturingTime())
                .dueDate(batch.getDueDate())
                .build();

    }

    @Override
    public void batchNumbersExist(List<Long> batchNumbers) {
        batchNumbers.forEach(batchNumber -> {
            boolean batchNumberDoesntExists = !this.batchRepository.existsByBatchNumber(batchNumber);
            if (batchNumberDoesntExists) {
                throw new ApiException("404", "Batch number " + batchNumber + " doesn't exists", 404);
            }
        });
    }

    @Override
    public ProductInWarehousesDTO findProductInWarehousesBy(Long productID) {

        List<Batch> productBatches = this.batchRepository.findBatchesByProduct_Id(productID);

        List<WarehouseProductQuantityDTO> productsQuantity = buildProductsQuantityBy(productBatches);

        return ProductInWarehousesDTO.builder()
                .productId(productID)
                .warehouses(productsQuantity)
                .build();
    }

    protected List<WarehouseProductQuantityDTO> buildProductsQuantityBy(List<Batch> productBatches) {
        Map<UUID, Integer> productsMap = new HashMap<>();
        List<WarehouseProductQuantityDTO> productsQuantity = new ArrayList<>();

        productBatches.forEach(batch -> {
            UUID warehouseCode = batch.getInboundOrder().getSection().getWarehouse().getWarehouseCode();
            Integer currentQuantity = batch.getCurrentQuantity();
            boolean warehouseCodeAlreadyExists = productsMap.containsKey(warehouseCode);

            if (warehouseCodeAlreadyExists) {
                Integer actualQuantity = productsMap.get(warehouseCode);
                productsMap.put(warehouseCode, actualQuantity + currentQuantity);
            } else {
                productsMap.put(warehouseCode, currentQuantity);
            }
        });

        productsMap.forEach((key, value) -> productsQuantity.add(WarehouseProductQuantityDTO.builder()
                .warehouseCode(key)
                .totalQuantity(value)
                .build()));

        return productsQuantity;
    }

    @Override
    public Batch createBatch(Product product, InboundOrder inboundOrder) {

        Batch batch = new Batch();
        batch.setProduct(product);
        batch.setCurrentQuantity(1);
        batch.setDueDate(LocalDate.now());
        batch.setCurrentTemperature(1.0);
        batch.setInboundOrder(inboundOrder);
        batch.setInitialQuantity(1);
        batch.setCurrentQuantity(1);

        return this.batchRepository.save(batch);
    }

}
