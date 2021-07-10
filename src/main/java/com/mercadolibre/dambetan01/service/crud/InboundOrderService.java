package com.mercadolibre.dambetan01.service.crud;

import com.mercadolibre.dambetan01.dtos.request.InboundOrderRequestDTO;
import com.mercadolibre.dambetan01.dtos.request.UpdateInboundOrderRequestDTO;
import com.mercadolibre.dambetan01.dtos.response.BatchStockResponseDTO;
import com.mercadolibre.dambetan01.model.User;

import java.util.List;
import java.util.UUID;

public interface InboundOrderService {

    public BatchStockResponseDTO registerNewInboundOrder(InboundOrderRequestDTO inboundOrderRequestDTO);
    public BatchStockResponseDTO updateInboundOrder(UpdateInboundOrderRequestDTO inboundOrderRequestDTO);
    public void orderNumberExists(Long orderNumber);
    public void inboundOrderContainsSectionCode(Long orderNumber, UUID sectionCode);
    public void inboundOrderContainsBatchNumbers(Long orderNumber, List<Long> batchNumbers);
    public Integer batchStockSizeDifferenceAfterUpdate(UpdateInboundOrderRequestDTO updateInboundOrderRequestDTO);

}
