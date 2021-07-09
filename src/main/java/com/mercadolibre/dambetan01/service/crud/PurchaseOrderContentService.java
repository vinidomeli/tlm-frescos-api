package com.mercadolibre.dambetan01.service.crud;

import com.mercadolibre.dambetan01.dtos.response.PurchaseOrderResponseDTO;

import java.util.List;
import java.util.UUID;

public interface PurchaseOrderContentService {
    PurchaseOrderResponseDTO createOrder(UUID userId);

    List<PurchaseOrderResponseDTO> listOrders(UUID userId);
}