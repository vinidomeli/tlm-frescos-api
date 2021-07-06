package com.mercadolibre.dambetan01.service.crud.impl;

import com.mercadolibre.dambetan01.dtos.response.ProductResponseDTO;
import com.mercadolibre.dambetan01.dtos.response.PurchaseOrderDetailDTO;
import com.mercadolibre.dambetan01.exceptions.NotFoundException;
import com.mercadolibre.dambetan01.exceptions.ProductListEmptyException;
import com.mercadolibre.dambetan01.model.Batch;
import com.mercadolibre.dambetan01.model.Order;
import com.mercadolibre.dambetan01.model.enums.ProductType;
import com.mercadolibre.dambetan01.repository.OrderRepository;
import com.mercadolibre.dambetan01.repository.ProductRepository;
import com.mercadolibre.dambetan01.service.crud.PurchaseService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class PurchaseServiceImpl implements PurchaseService {

    ProductRepository productRepository;
    OrderRepository orderRepository;

    private ModelMapper modelMapper;

    public PurchaseServiceImpl(ProductRepository productRepository, OrderRepository orderRepository) {
        this.productRepository = productRepository;
        this.modelMapper = new ModelMapper();
        this.orderRepository = orderRepository;
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
    public List<ProductResponseDTO> listProductsByCategory(String productType) {
        List<ProductResponseDTO> productsTypeList = productRepository.findProductByType(productType)
                .stream()
                .map(product -> modelMapper.map(product, ProductResponseDTO.class))
                .collect(Collectors.toList());

        if (productsTypeList.size() == 0) {
            throw new ProductListEmptyException();
        }
        return productsTypeList;
    }

    //[REQ-02] GET: List of purchase order products
    @Override
    public List<PurchaseOrderDetailDTO> listOrderProducts(Long orderId) {
        Order order = orderRepository.findById(orderId).orElseThrow(() -> new NotFoundException("Order doesn't exist."));
        List<PurchaseOrderDetailDTO> purchaseOrderList = orderRepository.findOrdersByProducts(order)
                .stream()
                .map(product -> modelMapper.map(product, PurchaseOrderDetailDTO.class))
                .collect(Collectors.toList());

        return purchaseOrderList;
    }

    private boolean orderExist(Long idOrder) {
        Optional<Order> orderId = orderRepository.findById(idOrder);
        return orderId.isPresent();
    }

    private boolean productStock(List<Batch> batchList, Integer quantityNeeded) {
        Integer quantity = 0;
        for (Batch batch : batchList) {
            quantity += batch.getCurrentQuantity();
            if (quantity >= quantityNeeded) {
                return true;
            }
        }
        return false;
    }

}
