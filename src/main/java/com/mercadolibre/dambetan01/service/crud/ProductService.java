package com.mercadolibre.dambetan01.service.crud;

import com.mercadolibre.dambetan01.dtos.BatchStockDTO;
import com.mercadolibre.dambetan01.dtos.request.InboundOrderRequestDTO;
import com.mercadolibre.dambetan01.model.Product;

import java.util.List;

public interface ProductService {

    public boolean productExists(long productId);
    public void productIdsInsideBatchStockExist(List<Long> productIDs);
}
