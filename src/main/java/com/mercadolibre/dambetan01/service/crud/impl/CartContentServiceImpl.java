package com.mercadolibre.dambetan01.service.crud.impl;

import com.mercadolibre.dambetan01.dtos.request.CartContentRequestDTO;
import com.mercadolibre.dambetan01.dtos.response.CartContentResponseDTO;
import com.mercadolibre.dambetan01.dtos.response.CartResponseDTO;
import com.mercadolibre.dambetan01.exceptions.ApiException;
import com.mercadolibre.dambetan01.model.Cart;
import com.mercadolibre.dambetan01.model.CartContent;
import com.mercadolibre.dambetan01.model.Product;
import com.mercadolibre.dambetan01.model.User;
import com.mercadolibre.dambetan01.repository.CartContentRepository;
import com.mercadolibre.dambetan01.repository.CartRepository;
import com.mercadolibre.dambetan01.repository.ProductRepository;
import com.mercadolibre.dambetan01.service.crud.CartContentService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class CartContentServiceImpl implements CartContentService {

    CartRepository cartRepository;
    CartContentRepository cartContentRepository;
    ProductRepository productRepository;

    public CartContentServiceImpl(CartRepository cartRepository,
                                  CartContentRepository cartContentRepository,
                                  ProductRepository productRepository) {
        this.cartRepository = cartRepository;
        this.cartContentRepository = cartContentRepository;
        this.productRepository = productRepository;
    }

    @Override
    public CartResponseDTO addToCart(UUID userId, CartContentRequestDTO cartContentRequestDTO) {
        Product product = getProduct(cartContentRequestDTO.getProductId());
        Cart cart = getCart(userId);
        Integer quantity = cartContentRequestDTO.getQuantity();
        quantityEqualToZero(quantity);

        CartContent cartContent = getCartContent(quantity, cart, product);
        cartContentRepository.save(cartContent);
        Double updatedPrice = getTotalPrice(cart);
        cart.setPrice(updatedPrice);
        cartRepository.save(cart);

        CartResponseDTO cartResponseDTO = toDto(cart);

        return cartResponseDTO;
    }

    @Override
    public CartResponseDTO removeToCart(UUID userId, Long cartContentId) {
        Cart cart = getCartIfExists(userId);

        Optional<CartContent> cartContent = cartContentRepository.findById(cartContentId);
        boolean cartContentExists = cartContent.isPresent();
        if (!cartContentExists) {
            throw new ApiException("404", "cart content not found", 404);
        }
        boolean cartContentBelongCart = cartContent.get().getCart().equals(cart);
        if (!cartContentBelongCart) {
            throw new ApiException("403", "this cart doesn't contains this product", 403);
        }
        cartContentRepository.delete(cartContent.get());
        Double updatedPrice = getTotalPrice(cart);
        cart.setPrice(updatedPrice);
        cartRepository.save(cart);

        CartResponseDTO cartResponseDTO = toDto(cart);

        return cartResponseDTO;
    }

    @Override
    public CartResponseDTO clearCart(UUID userId) {
        Cart cart = getCartIfExists(userId);
        List<CartContent> cartContentList = cartContentRepository.findAllByCart(cart);
        cart = clearCartByContentList(cartContentList);

        CartResponseDTO cartResponseDTO = toDto(cart);

        return cartResponseDTO;
    }

    @Override
    public CartResponseDTO viewCart(UUID userId) {
        Cart cart = getCart(userId);
        List<CartContent> cartContentList = cartContentRepository.findAllByCart(cart);
        List<CartContentResponseDTO> cartContentResponseDTOList = cartContentList.stream()
                .map(CartContentResponseDTO::toDto).collect(Collectors.toList());

        return CartResponseDTO.builder()
                .id(cart.getId())
                .price(cart.getPrice())
                .content(cartContentResponseDTOList)
                .build();
    }

    public Double getTotalPrice(Cart cart) {
        Double totalPrice = 0.0;

        List<CartContent> cartContentList = cartContentRepository.findAllByCart(cart);
        for (CartContent cartContent : cartContentList) {
            totalPrice += cartContent.getQuantity() * cartContent.getProduct().getPrice();
        }
        return totalPrice;
    }

    public CartContent getCartContent(Integer quantity, Cart cart, Product product) {
        CartContent cartContent = cartContentRepository.findByProductAndCart(product, cart);
        if (cartContent == null) {
            if (quantity < 0) {
                throw new ApiException("400", "the initial quantity cannot be less than one", 400);
            }
            return createCartContent(quantity, cart, product);
        }
        Integer newQuantity = cartContent.getQuantity() + quantity;
        if (newQuantity < 1) {
            throw new ApiException("400", "the new quantity cannot be less than one", 400);
        }
        cartContent.setQuantity(newQuantity);
        return cartContent;
    }

    public CartContent createCartContent(Integer quantity, Cart cart, Product product) {
        return CartContent.builder()
                .quantity(quantity)
                .cart(cart)
                .product(product)
                .build();
    }

    public Product getProduct(Long productId) {
        if (productId == null) {
            throw new ApiException("404", "product not found", 404);
        }
        Optional<Product> product = productRepository.findById(productId);
        boolean productExists = product.isPresent();
        if (!productExists) {
            throw new ApiException("404", "product not found", 404);
        }
        return product.get();
    }

    public Cart getCart(UUID userId) {
        User user = User.builder()
                .id(userId)
                .build();

        Cart cart = Cart.builder()
                .user(user)
                .price(0.0)
                .build();

        return cartRepository.findByUserId(userId)
                .orElseGet(() -> cartRepository.save(cart));
    }

    public void quantityEqualToZero(Integer quantity) {
        if (quantity == 0) {
            throw new ApiException("400", "quantity could be not equal to zero", 400);
        }
    }

    public Cart getCartIfExists(UUID userId) {
        return cartRepository.findByUserId(userId)
                .orElseThrow(() -> new ApiException("404", "cart not found", 404));
    }

    public Cart clearCartByContentList(List<CartContent> cartContentList) {
        boolean cartContentIsNullOrEmpty = cartContentList == null || cartContentList.isEmpty();
        if (cartContentIsNullOrEmpty) {
            throw new ApiException("404", "cart not found", 404);
        }
        UUID cartId = cartContentList.get(0).getCart().getId();
        Optional<Cart> cart = cartRepository.findById(cartId);
        cart.get().setPrice(0.0);

        cartContentRepository.deleteAll(cartContentList);
        return cartRepository.save(cart.get());
    }

    /*
     * transform cart in a response dto
     */
    public CartResponseDTO toDto(Cart cart) {
        List<CartContent> cartContentList = getCartContentList(cart);

        List<CartContentResponseDTO> cartContentResponseDTOList = getCartContentListToDtoList(cartContentList);

        return CartResponseDTO.builder()
                .id(cart.getId())
                .price(cart.getPrice())
                .content(cartContentResponseDTOList)
                .build();
    }

    /*
     * returns a list of all cart contents
     */
    public List<CartContent> getCartContentList(Cart cart) {
        List<CartContent> cartContentList = cartContentRepository.findAllByCart(cart);
        if (cartContentList == null) {
            throw new ApiException("404", "wtf", 404);
        }
        return cartContentList;
    }

    /*
     * turns a list of contents into a list of dto
     */
    public List<CartContentResponseDTO> getCartContentListToDtoList(List<CartContent> cartContentList) {
        List<CartContentResponseDTO> cartContentResponseDTOList = new ArrayList<>();

        cartContentList.forEach(cartContent -> {
            cartContentResponseDTOList.add(new CartContentResponseDTO(cartContent.getId(),
                    cartContent.getProduct().getType(),
                    cartContent.getQuantity()));
        });

        return cartContentResponseDTOList;
    }

}
