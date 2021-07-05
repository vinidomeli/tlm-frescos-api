package com.mercadolibre.dambetan01.service.crud;

import com.mercadolibre.dambetan01.dtos.response.ProductResponseDTO;
import com.mercadolibre.dambetan01.model.enums.ProductType;

import java.util.List;

public interface PurchaseService {
    public List<ProductResponseDTO> listAllProducts();
    public List<ProductResponseDTO> listProductsByCategory(ProductType productType);
}
