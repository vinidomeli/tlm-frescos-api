package com.mercadolibre.dambetan01.service.crud;

import com.mercadolibre.dambetan01.dtos.request.InboundOrderRequestDTO;
import com.mercadolibre.dambetan01.dtos.request.UpdateInboundOrderDTO;
import com.mercadolibre.dambetan01.dtos.response.BatchStockResponseDTO;

import java.util.List;
import java.util.UUID;

public interface InboundOrderService {

    public BatchStockResponseDTO registerNewInboundOrder(InboundOrderRequestDTO inboundOrderRequestDTO);
    public BatchStockResponseDTO updateInboundOrder(UpdateInboundOrderDTO inboundOrderRequestDTO);
    public void orderNumberExists(Long orderNumber);
    public void inboundOrderContainsSectionCode(Long orderNumber, UUID sectionCode);
    public void inboundOrderContainsBatchNumbers(Long orderNumber, List<Long> batchNumbers);
    public Integer batchStockSizeDifferenceAfterUpdate(UpdateInboundOrderDTO updateInboundOrderDTO);
    public List<UpdateInboundOrderDTO> listInboundOrderFromSupervisor(UUID userId);

}
