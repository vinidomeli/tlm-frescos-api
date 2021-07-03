package com.mercadolibre.dambetan01.service.crud;

import com.mercadolibre.dambetan01.dtos.request.InboundOrderRequestDTO;
import com.mercadolibre.dambetan01.dtos.response.BatchStockResponseDTO;

public interface InboundOrderService {

    public BatchStockResponseDTO registerNewInboundOrder(InboundOrderRequestDTO inboundOrderRequestDTO);
    public BatchStockResponseDTO updateInboundOrder(InboundOrderRequestDTO inboundOrderRequestDTO);

}
