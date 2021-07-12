package com.mercadolibre.dambetan01.service.crud;

import java.util.List;

public interface ProductService {

    boolean productExists(long productId);

    void productIdsInsideBatchStockExist(List<Long> productIDs);
}
