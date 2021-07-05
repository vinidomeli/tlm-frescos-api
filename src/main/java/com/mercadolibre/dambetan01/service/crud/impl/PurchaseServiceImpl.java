package com.mercadolibre.dambetan01.service.crud.impl;

import com.mercadolibre.dambetan01.dtos.response.ProductResponseDTO;
import com.mercadolibre.dambetan01.exceptions.ProductListEmptyException;
import com.mercadolibre.dambetan01.model.enums.ProductType;
import com.mercadolibre.dambetan01.repository.ProductRepository;
import com.mercadolibre.dambetan01.service.crud.PurchaseService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PurchaseServiceImpl implements PurchaseService {

    ProductRepository productRepository;

    private ModelMapper modelMapper;

    public PurchaseServiceImpl(ProductRepository productRepository, ModelMapper modelMapper) {
        this.productRepository = productRepository;
        this.modelMapper = modelMapper;
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
            throw new ProductListEmptyException();
            //throw new Exception("Product's list is empty.");
        }

        return productList;
    }

    //[REQ-02] GET: Complete list of products by type
    @Override
    public List<ProductResponseDTO> listProductsByCategory(ProductType productType) {
        List<ProductResponseDTO> productsTypeList = productRepository.findProductByType(productType)
                .stream()
                .map(product -> modelMapper.map(product, ProductResponseDTO.class))
                .collect(Collectors.toList());

        if (productsTypeList.size() == 0) {
            throw new ProductListEmptyException();
        }
        return productsTypeList;
    }
}
