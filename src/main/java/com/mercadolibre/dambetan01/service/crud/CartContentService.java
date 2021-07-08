package com.mercadolibre.dambetan01.service.crud;

import com.mercadolibre.dambetan01.dtos.request.CartContentRequestDTO;
import com.mercadolibre.dambetan01.dtos.response.CartResponseDTO;

import java.util.UUID;

public interface CartContentService {
    CartResponseDTO addToCart(UUID userId, CartContentRequestDTO cartContentRequestDTO);

    CartResponseDTO removeToCart(UUID userId, Long cartContentId);

    CartResponseDTO listAllCarts(UUID userId);
}
