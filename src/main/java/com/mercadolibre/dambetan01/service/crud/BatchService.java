package com.mercadolibre.dambetan01.service.crud;

public interface BatchService {

    public BatchStockResponseDTO registerNewBatch(InboundOrderRequestDTO inboundOrderRequestDTO);
    public BatchStockResponseDTO updateBatch(InboundOrderRequestDTO inboundOrderRequestDTO);

}
