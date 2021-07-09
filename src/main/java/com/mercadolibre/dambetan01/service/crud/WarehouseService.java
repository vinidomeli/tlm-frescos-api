package com.mercadolibre.dambetan01.service.crud;

import com.mercadolibre.dambetan01.dtos.response.BatchStockDueDateDTO;

import com.mercadolibre.dambetan01.dtos.WarehouseDTO;

import java.util.List;
import java.util.UUID;

public interface WarehouseService {

    public void warehouseExists(UUID warehouseCode);

    public BatchStockDueDateDTO getAllBatchesWarehouse(Integer quantityDays);
    
    public BatchStockDueDateDTO getAllBatchesWarehouseByCategory(Integer numberOfDays, String productType, String order);

    public List<WarehouseDTO> findAll();
}
