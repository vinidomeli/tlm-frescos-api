package com.mercadolibre.dambetan01.service.crud.impl;

import com.mercadolibre.dambetan01.dtos.CountryHouseDTO;
import com.mercadolibre.dambetan01.dtos.response.ProductResponseDTO;
import com.mercadolibre.dambetan01.exceptions.ProductListEmptyException;
import com.mercadolibre.dambetan01.model.Product;
import com.mercadolibre.dambetan01.model.enums.ProductType;
import com.mercadolibre.dambetan01.repository.ProductRepository;
import com.mercadolibre.dambetan01.service.crud.PurchaseService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
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
        //List<ProductResponseDTO> productList = new ArrayList<>();

        List<ProductResponseDTO> productList = productRepository.findAll()
                .stream()
                .map(product -> modelMapper.map(product, ProductResponseDTO.class))
                .collect(Collectors.toList());

//        products.stream().forEach(productEntity -> {
//            productList.add(ProductResponseDTO.builder()
//                .id(productEntity.getId())
//                .type(productEntity.getType())
//                .price(productEntity.getPrice())
//                .build());
//        });

        // @TODO: Customizar exception
        if (productList.size() == 0) {
            throw new ProductListEmptyException();
            //throw new Exception("Product's list is empty.");
        }

        return productList;
    }

    @Override
    public List<ProductResponseDTO> listProductsByCategory(ProductType productType) {
        Optional<List<Product>> productsTypeList = productRepository.findProductByType(productType);

        return null;
    }
}
