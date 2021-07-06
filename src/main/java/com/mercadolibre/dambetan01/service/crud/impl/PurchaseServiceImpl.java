package com.mercadolibre.dambetan01.service.crud.impl;

import com.mercadolibre.dambetan01.dtos.response.ProductResponseDTO;
import com.mercadolibre.dambetan01.dtos.response.PurchaseOrderDetailDTO;
import com.mercadolibre.dambetan01.exceptions.ApiException;
import com.mercadolibre.dambetan01.exceptions.NotFoundException;
import com.mercadolibre.dambetan01.exceptions.ProductListEmptyException;
import com.mercadolibre.dambetan01.model.Batch;
import com.mercadolibre.dambetan01.model.PurchaseOrder;
import com.mercadolibre.dambetan01.model.User;
import com.mercadolibre.dambetan01.repository.OrderRepository;
import com.mercadolibre.dambetan01.repository.ProductRepository;
import com.mercadolibre.dambetan01.repository.UserRepository;
import com.mercadolibre.dambetan01.service.crud.PurchaseService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class PurchaseServiceImpl implements PurchaseService {

    ProductRepository productRepository;
    OrderRepository orderRepository;
    UserRepository userRepository;

    private ModelMapper modelMapper;

    public PurchaseServiceImpl(ProductRepository productRepository, OrderRepository orderRepository, UserRepository userRepository) {
        this.modelMapper = new ModelMapper();
        this.productRepository = productRepository;
        this.orderRepository = orderRepository;
        this.userRepository = userRepository;
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

    //[REQ-02] GET: List of purchase order products
    @Override
    public List<PurchaseOrderDetailDTO> listOrderProducts(Long orderId) {
        PurchaseOrder purchaseOrder = orderRepository.findById(orderId).orElseThrow(() -> new NotFoundException("Order doesn't exist."));
        List<PurchaseOrderDetailDTO> purchaseOrderList = orderRepository.findOrdersByProducts(purchaseOrder)
                .stream()
                .map(product -> modelMapper.map(product, PurchaseOrderDetailDTO.class))
                .collect(Collectors.toList());

        return purchaseOrderList;
    }

    private boolean orderExist(Long idOrder) {
        Optional<PurchaseOrder> orderId = orderRepository.findById(idOrder);
        return orderId.isPresent();
    }

    //o produto tem estoque
    @Override
    public boolean productStock(List<Batch> batchList, Integer quantityNeeded) {
        Integer quantity = 0;
        for (Batch batch : batchList) {
            quantity += batch.getCurrentQuantity();
            if (quantity >= quantityNeeded) {
                return true;
            }
        }
        return false;
    }

    // o comprador está registrado
    @Override
    public Optional<User> validateUser(String role) throws ApiException {
        Optional<User> user = userRepository.findByRole(role);
        if (!user.isPresent()) {
            throw new ApiException("404", "User doesn't exists", 404);
        }
         if (!user.get().getRole().contains("Buyer")) {
             throw new ApiException("401", "User Doesn't have access", 401);
         }
        return user;
    }

}
