package com.mercadolibre.dambetan01.service.crud.impl;

import com.mercadolibre.dambetan01.dtos.response.PurchaseOrderResponseDTO;
import com.mercadolibre.dambetan01.exceptions.ApiException;
import com.mercadolibre.dambetan01.model.*;
import com.mercadolibre.dambetan01.repository.*;
import com.mercadolibre.dambetan01.service.crud.PurchaseOrderContentService;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class PurchaseOrderContentServiceImpl implements PurchaseOrderContentService {

    PurchaseOrderRepository purchaseOrderRepository;
    PurchaseOrderContentRepository purchaseOrderContentRepository;
    CartRepository cartRepository;
    CartContentRepository cartContentRepository;
    CartContentServiceImpl cartContentServiceImpl;
    BatchRepository batchRepository;
    UserRepository userRepository;

    public PurchaseOrderContentServiceImpl(PurchaseOrderRepository purchaseOrderRepository,
                                           PurchaseOrderContentRepository purchaseOrderContentRepository,
                                           CartRepository cartRepository,
                                           CartContentRepository cartContentRepository,
                                           CartContentServiceImpl cartContentServiceImpl,
                                           BatchRepository batchRepository,
                                           UserRepository userRepository) {
        this.purchaseOrderRepository = purchaseOrderRepository;
        this.purchaseOrderContentRepository = purchaseOrderContentRepository;
        this.cartRepository = cartRepository;
        this.cartContentRepository = cartContentRepository;
        this.cartContentServiceImpl = cartContentServiceImpl;
        this.batchRepository = batchRepository;
        this.userRepository = userRepository;
    }

    @Override
    public PurchaseOrderResponseDTO createOrder(UUID userId) {
        List<CartContent> cartContentList = getCartContentList(userId);
        PurchaseOrder purchaseOrder = new PurchaseOrder();

        List<PurchaseOrderContent> purchaseOrderContentList = getPurchaseOrderContentList(cartContentList,
                purchaseOrder);

        Double totalPrice = getTotalPrice(purchaseOrderContentList);
        purchaseOrder.setDate(LocalDate.now());
        purchaseOrder.setPrice(totalPrice);
        // TODO : settar o user
        purchaseOrderRepository.save(purchaseOrder);
        purchaseOrderContentRepository.saveAll(purchaseOrderContentList);

        subtractBatchesQuantity(cartContentList);
        clearCart(cartContentList);

        return makeResponseDto(purchaseOrder);
    }

    public List<CartContent> getCartContentList(UUID userId) {
        Cart cart = getCart(userId);

        List<CartContent> cartContentList = cartContentRepository.findAllByCart(cart);
        boolean cartContentNullOrEmpty = cartContentList == null || cartContentList.isEmpty();
        if (cartContentNullOrEmpty) {
            throw new ApiException("404", "cart dont have content", 404);
        }
        return cartContentList;
    }

    public List<PurchaseOrderContent> getPurchaseOrderContentList(List<CartContent> cartContentList,
                                                                  PurchaseOrder purchaseOrder) {
        List<String> insufficientProductList = new ArrayList<>();
        List<PurchaseOrderContent> purchaseOrderContentList = new ArrayList<>();
        for (CartContent cartContent : cartContentList) {
            Product product = cartContentServiceImpl.getProduct(cartContent.getProduct().getId());
            List<Batch> batchList = findBatchesByProduct(product);
            batchList = filterBatchesByDueDate(batchList);
            Integer actualQuantity = getActualQuantityInBatch(batchList);
            boolean insufficientQuantity = cartContent.getQuantity() > actualQuantity;
            if (insufficientQuantity) {
                insufficientProductList.add("quantidade do produto: " + product.getType() + " insuficiente "
                        + " quantidade pedida: " + cartContent.getQuantity() + " em estoque: " + actualQuantity);
            } else {
                purchaseOrderContentList.add(buildPurchaseOrderContent(cartContent, purchaseOrder));
            }
        }
        checkForErros(insufficientProductList);

        return purchaseOrderContentList;
    }

    public Double getTotalPrice(List<PurchaseOrderContent> purchaseOrderContentList) {
        Double totalPrice = 0.0;

        for (PurchaseOrderContent purchaseOrderContent : purchaseOrderContentList) {
            totalPrice += purchaseOrderContent.getProductPrice() * purchaseOrderContent.getProductQuantity();
        }
        return totalPrice;
    }

    public void subtractBatchesQuantity(List<CartContent> cartContentList) {
        for (CartContent cartContent : cartContentList) {
            Product product = cartContentServiceImpl.getProduct(cartContent.getProduct().getId());

            List<Batch> batchList = findBatchesByProduct(product);

            runBatchesByProduct(batchList, cartContent.getQuantity());

            batchRepository.saveAll(batchList);
        }
    }

    public void clearCart(List<CartContent> cartContentList) {
        boolean cartContentIsNullOrEmpty = cartContentList == null || cartContentList.isEmpty();
        if (cartContentIsNullOrEmpty) {
            throw new ApiException("404", "cart not found", 404);
        }
        UUID cartId = cartContentList.get(0).getCart().getId();
        Optional<Cart> cart = cartRepository.findByUserId(cartId);
      //  cart.get().setPrice(0.0);

        cartContentRepository.deleteAll(cartContentList);
       // cartRepository.save(cart.get());
    }

    public PurchaseOrderResponseDTO makeResponseDto(PurchaseOrder purchaseOrder) {
        return PurchaseOrderResponseDTO.builder()
                .user(purchaseOrder.getUser())
                .date(purchaseOrder.getDate())
                .price(purchaseOrder.getPrice())
                .build();
    }

    public PurchaseOrderContent buildPurchaseOrderContent(CartContent cartContent, PurchaseOrder purchaseOrder) {
        Product product = cartContent.getProduct();

        return PurchaseOrderContent.builder()
                .productName(product.getType())
                .productPrice(product.getPrice())
                .productQuantity(cartContent.getQuantity())
                .purchaseOrder(purchaseOrder)
                .build();
    }

    public void runBatchesByProduct(List<Batch> batchList, Integer necessaryQuantity) {
        // TODO: o que é melhor? isso abaixo ou
        //  criar um if dentro do for para verificar se a quantidade necessária ja foi atingida (a cada iteração)
        while (necessaryQuantity != 0) {
            for (Batch batch : batchList) {
                Integer batchQuantity = batch.getCurrentQuantity();
                if (batchQuantity > 0) {
                    if (batchQuantity >= necessaryQuantity) {
                        batch.setCurrentQuantity(batchQuantity - necessaryQuantity);
                        necessaryQuantity = 0;
                    } else {
                        batch.setCurrentQuantity(0);
                        necessaryQuantity = necessaryQuantity - batchQuantity;
                    }
                }
            }
        }
    }

    public void checkForErros(List<String> insufficientProductList) {
        boolean haveErrors = !insufficientProductList.isEmpty();
        if (haveErrors) {
            throw new ApiException("404", insufficientProductList.toString(), 404);
        }
    }

    public Integer getActualQuantityInBatch(List<Batch> batchList) {
        Integer actualQuantity = 0;

        for (Batch batch : batchList) {
            actualQuantity += batch.getCurrentQuantity();
        }

        return actualQuantity;
    }

    public List<Batch> filterBatchesByDueDate(List<Batch> batchList) {
        LocalDate unexpiredDate = LocalDate.now().minusWeeks(3);
        return batchList.stream()
                .filter(batch -> batch.getDueDate().isAfter(unexpiredDate))
                .collect(Collectors.toList());
    }

    public List<Batch> findBatchesByProduct(Product product) {
        List<Batch> batchList = batchRepository.findBatchesByProduct(product);
        if (batchList == null || batchList.isEmpty()) {
            throw new ApiException("404", "not found batches for this product", 404);
        }
        return batchList;
    }


    public Cart getCart(UUID userId) {
        Optional<Cart> cart = cartRepository.findByUserId(userId);
        boolean cartExists = cart.isPresent();
        if (!cartExists) {
            throw new ApiException("404", "cart not exists", 404);
        }
        return cart.get();
    }

}
