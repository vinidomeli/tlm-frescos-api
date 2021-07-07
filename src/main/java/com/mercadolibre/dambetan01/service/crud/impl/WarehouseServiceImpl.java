package com.mercadolibre.dambetan01.service.crud.impl;

import com.mercadolibre.dambetan01.dtos.BatchDueDateDTO;
import com.mercadolibre.dambetan01.dtos.response.BatchStockDueDateDTO;
import com.mercadolibre.dambetan01.exceptions.ApiException;
import com.mercadolibre.dambetan01.model.Batch;
import com.mercadolibre.dambetan01.repository.BatchRepository;
import com.mercadolibre.dambetan01.repository.WarehouseRepository;
import com.mercadolibre.dambetan01.service.crud.WarehouseService;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class WarehouseServiceImpl implements WarehouseService {

    WarehouseRepository warehouseRepository;
    BatchRepository batchRepository;

    public WarehouseServiceImpl(final WarehouseRepository warehouseRepository,
                                final BatchRepository batchRepository) {
        this.warehouseRepository = warehouseRepository;
        this.batchRepository = batchRepository;
    }

    @Override
    public void warehouseExists(UUID warehouseCode) {
        boolean warehouseCodeDoesntExists = !warehouseRepository.existsByWarehouseCode(warehouseCode);

        if(warehouseCodeDoesntExists) {
            throw new ApiException("404", "Warehouse Code doesn't exists", 404);
        }
    }

    @Override
    public BatchStockDueDateDTO getAllBatchesWarehouse(Integer quantityDays) {
        LocalDate todayPlusQuantityDays = LocalDate.now().plusDays(quantityDays);
        List<Batch> batchList = batchRepository.findBatchesByDueDateLessThanEqualOrderByDueDateDesc(todayPlusQuantityDays);
        
        List<BatchDueDateDTO> batchDueDateDTOList = new ArrayList<>();
        batchList.forEach(batch -> {
            BatchDueDateDTO batchDueDateDTO = BatchDueDateDTO.builder()
                    .batchNumber(batch.getBatchNumber())
                    .productId(batch.getProduct().getId())
                    .productTypeId(batch.getProductType())
                    .dueDate(batch.getDueDate())
                    .quantity(batch.getCurrentQuantity())
                    .build();
            batchDueDateDTOList.add(batchDueDateDTO);
        });

        return new BatchStockDueDateDTO(batchDueDateDTOList);
    }
}
