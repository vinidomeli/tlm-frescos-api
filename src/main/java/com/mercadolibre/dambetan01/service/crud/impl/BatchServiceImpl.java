package com.mercadolibre.dambetan01.service.crud.impl;

import com.mercadolibre.dambetan01.dtos.BatchStockDTO;
import com.mercadolibre.dambetan01.model.Batch;
import com.mercadolibre.dambetan01.model.InboundOrder;
import com.mercadolibre.dambetan01.model.Product;
import com.mercadolibre.dambetan01.repository.BatchRepository;
import com.mercadolibre.dambetan01.repository.InboundOrderRepository;
import com.mercadolibre.dambetan01.repository.ProductRepository;
import com.mercadolibre.dambetan01.service.crud.BatchService;
import org.springframework.stereotype.Service;

import java.util.List;

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
    public Batch convertBatchStockDTOToBatch(BatchStockDTO batchStockDTO, Long inboundOrderNumber) {

        Product product = productRepository.findByProductId(batchStockDTO.getProductId());
        InboundOrder inboundOrder = inboundOrderRepository.findByOrderNumber(inboundOrderNumber);

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
                .productId(batch.getProduct().getProductId())
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
            boolean batchNumberDoesntExists = !batchRepository.existsByBatchNumber(batchNumber);
            if(batchNumberDoesntExists) {
                throw new RuntimeException("Batch number " + batchNumber + " doesn't exists");
            }
        });
    }
}
