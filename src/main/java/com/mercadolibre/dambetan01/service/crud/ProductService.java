package com.mercadolibre.dambetan01.service.crud;

import com.mercadolibre.dambetan01.dtos.response.ProductResponseDTO;

import java.util.List;

public interface ProductService {

    public boolean productExists(long productId);

    public void productIdsInsideBatchStockExist(List<Long> productIDs);

    public List<ProductResponseDTO> listAllProducts();

    public List<ProductResponseDTO> listProductsByCategory(String productType);
}
