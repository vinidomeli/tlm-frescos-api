package com.mercadolibre.dambetan01.service.crud;

import com.mercadolibre.dambetan01.dtos.BatchStockDTO;
import com.mercadolibre.dambetan01.dtos.response.ProductInWarehousesDTO;
import com.mercadolibre.dambetan01.model.Batch;

import java.util.List;

public interface BatchService {

    public Batch convertBatchStockDTOToBatch(BatchStockDTO batchStockDTO, Long inboundOrder);

    public void batchNumbersExist(List<Long> batchNumbers);

    public ProductInWarehousesDTO findProductInWarehousesBy(Long productID);
}
