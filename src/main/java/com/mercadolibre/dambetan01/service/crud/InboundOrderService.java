package com.mercadolibre.dambetan01.service.crud;

import com.mercadolibre.dambetan01.dtos.request.InboundOrderRequestDTO;
import com.mercadolibre.dambetan01.dtos.request.UpdateInboundOrderRequestDTO;
import com.mercadolibre.dambetan01.dtos.response.BatchStockResponseDTO;
import com.mercadolibre.dambetan01.model.User;

import java.util.List;
import java.util.UUID;

public interface InboundOrderService {

    BatchStockResponseDTO registerNewInboundOrder(InboundOrderRequestDTO inboundOrderRequestDTO);
    BatchStockResponseDTO updateInboundOrder(UpdateInboundOrderRequestDTO inboundOrderRequestDTO);
    void orderNumberExists(Long orderNumber);
    void inboundOrderContainsSectionCode(Long orderNumber, UUID sectionCode);
    void inboundOrderContainsBatchNumbers(Long orderNumber, List<Long> batchNumbers);

}
