package com.mercadolibre.dambetan01.service.crud;

import com.mercadolibre.dambetan01.dtos.response.ProductResponseDTO;
import com.mercadolibre.dambetan01.dtos.response.PurchaseOrderDetailDTO;
import com.mercadolibre.dambetan01.exceptions.ApiException;
import com.mercadolibre.dambetan01.model.Batch;
import com.mercadolibre.dambetan01.model.User;

import java.util.List;
import java.util.Optional;

public interface PurchaseService {
    public List<ProductResponseDTO> listAllProducts();
    public List<ProductResponseDTO> listProductsByCategory(String productType);
    public List<PurchaseOrderDetailDTO> listOrderProducts(Long idOrder);
    public Optional<User> validateUser(String role) throws ApiException;
    public boolean productStock(List<Batch> batchList, Integer quantityNeeded);
}
