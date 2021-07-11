package com.mercadolibre.dambetan01.service.crud.impl;

import com.mercadolibre.dambetan01.dtos.ProductRegisterDTO;
import com.mercadolibre.dambetan01.dtos.response.ProductFromSellerDTO;
import com.mercadolibre.dambetan01.dtos.response.ProductResponseDTO;
import com.mercadolibre.dambetan01.exceptions.ApiException;
import com.mercadolibre.dambetan01.model.Product;
import com.mercadolibre.dambetan01.repository.ProductRepository;
import com.mercadolibre.dambetan01.repository.SellerRepository;
import com.mercadolibre.dambetan01.service.crud.ProductService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class ProductServiceImpl implements ProductService {

    ProductRepository productRepository;
    SellerRepository sellerRepository;
    private ModelMapper modelMapper;

    public ProductServiceImpl(ProductRepository productRepository, SellerRepository sellerRepository,
                              ModelMapper modelMapper) {
        this.productRepository = productRepository;
        this.sellerRepository = sellerRepository;
        this.modelMapper = new ModelMapper();
    }

    public Product findProductById(Long productId) {
        return this.productRepository.findById(productId).get();
    }

    @Override
    public boolean productExists(long productId) {
        return this.productRepository.findById(productId).isPresent();
    }

    @Override
    public void productIdsInsideBatchStockExist(List<Long> productIds) {
        productIds.stream()
                .forEach(productId -> {
                    boolean productDoesntExists = !productExists(productId);
                    if (productDoesntExists) {
                        throw new ApiException("404", "Product Doesn't exists", 404);
                    }
                });
    }

    //[REQ-02] GET: Complete list of products
    @Override
    public List<ProductResponseDTO> listAllProducts() {
        List<ProductResponseDTO> productList = productRepository.findAll()
                .stream()
                .map(product -> modelMapper.map(product, ProductResponseDTO.class))
                .collect(Collectors.toList());

        // @TODO: Customizar exception
        if (productList.size() == 0) {
            throw new ApiException("404", "Product's list is empty.", 404);
        }

        return productList;
    }

    //[REQ-02] GET: Complete list of products by type
    @Override
    public List<ProductResponseDTO> listProductsByCategory(String productType) {
        List<ProductResponseDTO> productsTypeList = productRepository.findProductByType(productType)
                .stream()
                .map(product -> modelMapper.map(product, ProductResponseDTO.class))
                .collect(Collectors.toList());

        if (productsTypeList.size() == 0) {
            throw new ApiException("404", "There is not any product registered for this type.", 404);
        }
        return productsTypeList;
    }

    @Override
    public ProductRegisterDTO createProduct(ProductRegisterDTO productRegisterDTO, UUID userId) {
        Product product = Product.builder()
                .type(productRegisterDTO.getType())
                .seller(sellerRepository.findByUser_Id(userId).get())
                .price(productRegisterDTO.getPrice())
                .build();
        Product savedProduct = productRepository.save(product);

        return ProductRegisterDTO.fromEntity(savedProduct);
    }

    @Override
    public List<ProductFromSellerDTO> listProductsFromSeller(UUID userId) {
        List<Product> productFromSellerList = productRepository.findProductsBySeller_User_Id(userId)
                .orElseThrow(() -> new ApiException("404", "No products for this seller", 404));
        return productFromSellerList.stream()
                .map(ProductFromSellerDTO::fromEntity)
                .collect(Collectors.toList());
    }

}
