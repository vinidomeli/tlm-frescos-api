package com.mercadolibre.dambetan01.service.crud;

import com.mercadolibre.dambetan01.dtos.BatchStockDTO;
import com.mercadolibre.dambetan01.model.Batch;
import com.mercadolibre.dambetan01.model.InboundOrder;
import com.mercadolibre.dambetan01.model.Product;

import java.util.List;

public interface BatchService {

    public Batch convertBatchStockDTOToBatch(BatchStockDTO batchStockDTO, Long inboundOrder);
    public void batchNumbersExist(List<Long> batchNumbers);
    public Batch createBatch(Product product, InboundOrder inboundOrder);
}
