package com.mercadolibre.dambetan01.service.crud.impl;

import com.mercadolibre.dambetan01.dtos.BatchStockDTO;
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
import java.util.List;
import java.util.Optional;

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

    public List<Batch> findBatchesByProductId(Long productId) {
        Optional<Product> productOptional = productRepository.findById(productId);

        if(!productOptional.isPresent()) {
            throw new NotFoundException("Produto com id" + productId +  "não encontrado!");
        }

        return batchRepository.findBatchesByProduct_Id(productId);
    }

    public List<Batch> findBatchesByProductId(List<String> query) throws NotFoundException {
        Long productId = Long.parseLong(query.get(0));
        Optional<Product> productOptional = productRepository.findById(productId);
        LocalDate validDueDate = LocalDate.now().plusWeeks(3);


        if(!productOptional.isPresent()) {
            throw new NotFoundException("Product not found");
        }

        List<Batch> batches = null;

        if(query.size() == 2) {
            if(query.get(1).equals("C")) {
                batches = batchRepository.findBatchesByProduct_IdAndDueDateGreaterThanOrderByCurrentQuantity(productId, validDueDate);
            } else if(query.get(1).equals("F")) {
                batches = batchRepository.findBatchesByProduct_IdAndDueDateGreaterThanOrderByDueDateAsc(productId, validDueDate);
            }
        }

        if(batches == null) {
            batches = batchRepository.findBatchesByProduct_IdAndDueDateGreaterThanOrderByDueDateAsc(productId, validDueDate);
        }

        return batches;
    }

    @Override
    public Batch convertBatchStockDTOToBatch(BatchStockDTO batchStockDTO, Long inboundOrderNumber) {

        Product product = productRepository.findById(batchStockDTO.getProductId()).get();
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
            boolean batchNumberDoesntExists = !batchRepository.existsByBatchNumber(batchNumber);
            if(batchNumberDoesntExists) {
                throw new ApiException("404", "Batch number " + batchNumber + " doesn't exists", 404);
            }
        });
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

        return batchRepository.save(batch);
    }

}
