package com.mercadolibre.dambetan01.service.crud;

import com.mercadolibre.dambetan01.dtos.response.ProductResponseDTO;

import java.util.List;

public interface PurchaseService {
    public List<ProductResponseDTO> listAllProducts();
}
