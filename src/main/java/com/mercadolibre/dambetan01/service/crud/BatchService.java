package com.mercadolibre.dambetan01.service.crud;

import com.mercadolibre.dambetan01.dtos.request.InboundOrderRequestDTO;
import com.mercadolibre.dambetan01.dtos.response.BatchStockResponseDTO;

public interface BatchService {

    public BatchStockResponseDTO registerNewBatch(InboundOrderRequestDTO inboundOrderRequestDTO);
    public BatchStockResponseDTO updateBatch(InboundOrderRequestDTO inboundOrderRequestDTO);

}
