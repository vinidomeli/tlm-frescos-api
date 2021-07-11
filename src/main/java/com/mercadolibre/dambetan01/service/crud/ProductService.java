package com.mercadolibre.dambetan01.service.crud;

import com.mercadolibre.dambetan01.dtos.ProductRegisterDTO;
import com.mercadolibre.dambetan01.dtos.response.ProductFromSellerDTO;
import com.mercadolibre.dambetan01.dtos.response.ProductResponseDTO;

import java.util.List;
import java.util.UUID;

public interface ProductService {

    public boolean productExists(long productId);

    public void productIdsInsideBatchStockExist(List<Long> productIDs);

    public List<ProductResponseDTO> listAllProducts();

    public List<ProductResponseDTO> listProductsByCategory(String productType);

    public ProductRegisterDTO createProduct(ProductRegisterDTO productRegisterDTO, UUID userId);

    public List<ProductFromSellerDTO> listProductsFromSeller(UUID userId);
}
