package com.mercadolibre.dambetan01.service.crud;

import com.mercadolibre.dambetan01.dtos.BatchStockDTO;
import com.mercadolibre.dambetan01.dtos.response.ProductBatchesResponseDTO;
import com.mercadolibre.dambetan01.dtos.response.ProductInWarehousesDTO;
import com.mercadolibre.dambetan01.exceptions.NotFoundException;
import com.mercadolibre.dambetan01.model.Batch;
import com.mercadolibre.dambetan01.model.InboundOrder;
import com.mercadolibre.dambetan01.model.Product;

import java.util.List;

public interface BatchService {

    public Batch convertBatchStockDTOToBatch(BatchStockDTO batchStockDTO, Long inboundOrder);

    public void batchNumbersExist(List<Long> batchNumbers);

    public ProductInWarehousesDTO findProductInWarehousesBy(Long productID);

    Batch createBatch(Product product, InboundOrder inboundOrder);

    public List<ProductBatchesResponseDTO> findBatchesByProductId(Long productId, String order) throws NotFoundException;

    public List<Batch> findBatchesByProductId(Long productId);

}
