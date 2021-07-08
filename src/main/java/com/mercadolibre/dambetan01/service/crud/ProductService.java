package com.mercadolibre.dambetan01.service.crud;

import java.util.List;

public interface ProductService {

    public boolean productExists(long productId);

    public void productIdsInsideBatchStockExist(List<Long> productIDs);
}
