package com.mercadolibre.dambetan01.service.crud;

import com.mercadolibre.dambetan01.dtos.response.ProductResponseDTO;
import com.mercadolibre.dambetan01.dtos.response.PurchaseOrderDetailDTO;

import java.util.List;

public interface PurchaseService {
    public List<ProductResponseDTO> listAllProducts();
    public List<ProductResponseDTO> listProductsByCategory(String productType);
    public List<PurchaseOrderDetailDTO> listOrderProducts(Long idOrder);
}
