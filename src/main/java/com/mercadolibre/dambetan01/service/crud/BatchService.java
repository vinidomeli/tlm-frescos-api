package com.mercadolibre.dambetan01.service.crud;

import com.mercadolibre.dambetan01.dtos.BatchStockDTO;
import com.mercadolibre.dambetan01.model.Batch;
import com.mercadolibre.dambetan01.model.InboundOrder;
import com.mercadolibre.dambetan01.model.Product;

import java.util.List;

public interface BatchService {

    Batch convertBatchStockDTOToBatch(BatchStockDTO batchStockDTO, Long inboundOrder);
    void batchNumbersExist(List<Long> batchNumbers);
    Batch createBatch(Product product, InboundOrder inboundOrder);

}
