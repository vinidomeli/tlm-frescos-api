package com.mercadolibre.dambetan01.service.crud.impl;

import com.mercadolibre.dambetan01.dtos.BatchStockDTO;
import com.mercadolibre.dambetan01.dtos.request.InboundOrderRequestDTO;
import com.mercadolibre.dambetan01.dtos.request.UpdateInboundOrderRequestDTO;
import com.mercadolibre.dambetan01.dtos.response.BatchStockResponseDTO;
import com.mercadolibre.dambetan01.model.Batch;
import com.mercadolibre.dambetan01.model.InboundOrder;
import com.mercadolibre.dambetan01.model.Section;
import com.mercadolibre.dambetan01.repository.BatchRepository;
import com.mercadolibre.dambetan01.repository.InboundOrderRepository;
import com.mercadolibre.dambetan01.repository.SectionRepository;
import com.mercadolibre.dambetan01.service.crud.InboundOrderService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class InboundOrderServiceImpl implements InboundOrderService {

    SectionRepository sectionRepository;
    BatchRepository batchRepository;
    BatchServiceImpl batchServiceImpl;
    InboundOrderRepository inboundOrderRepository;

    public InboundOrderServiceImpl(SectionRepository sectionRepository, BatchRepository batchRepository,
                                   BatchServiceImpl batchServiceImpl, InboundOrderRepository inboundOrderRepository) {
        this.sectionRepository = sectionRepository;
        this.batchRepository = batchRepository;
        this.batchServiceImpl = batchServiceImpl;
        this.inboundOrderRepository = inboundOrderRepository;
    }

    @Override
    public BatchStockResponseDTO registerNewInboundOrder(InboundOrderRequestDTO inboundOrderRequestDTO) {

        InboundOrder inboundOrder = inboundOrderRequestDTOToInboundOrder(inboundOrderRequestDTO);

        inboundOrderRequestDTO.getBatchStock()
                .forEach(batchStockDTO -> {
                    Batch batch = batchServiceImpl.convertBatchStockDTOToBatch(batchStockDTO, inboundOrder.getOrderNumber());
                    batchRepository.save(batch);
                });

        return inboundOrderToBatchStockResponseDTO(inboundOrder);
    }

    @Override
    public BatchStockResponseDTO updateInboundOrder(UpdateInboundOrderRequestDTO updateInboundOrderRequestDTO) {

        Section section = sectionRepository.findBySectionCode(updateInboundOrderRequestDTO.getSection().getSectionCode());

        InboundOrder inboundOrder = inboundOrderRepository.findByOrderNumber(updateInboundOrderRequestDTO.getOrderNumber());
        inboundOrder.setOrderDate(updateInboundOrderRequestDTO.getOrderDate());
        inboundOrder.setSection(section);

        updateInboundOrderRequestDTO.getBatchStock().forEach(updateBatchStockDTO -> {
            Batch batch = batchRepository.findBatchByBatchNumber(updateBatchStockDTO.getBatchNumber());
            //productId can change?
            batch.setCurrentTemperature(updateBatchStockDTO.getCurrentTemperature());
            batch.setMinimumTemperature(updateBatchStockDTO.getMinimumTemperature());
            //initialQuantity can change?
            batch.setCurrentQuantity(updateBatchStockDTO.getCurrentQuantity());
            batch.setManufacturingDate(updateBatchStockDTO.getManufacturingDate());
            batch.setManufacturingTime(updateBatchStockDTO.getManufacturingTime());
            batch.setDueDate(updateBatchStockDTO.getDueDate());

            batchRepository.save(batch);
        });

        return inboundOrderToBatchStockResponseDTO(inboundOrder);
    }

    public InboundOrder inboundOrderRequestDTOToInboundOrder(InboundOrderRequestDTO inboundOrderRequestDTO) {
        InboundOrder inboundOrder = new InboundOrder();
        UUID sectionCode = inboundOrderRequestDTO.getSection().getSectionCode();
        Section section = sectionRepository.findBySectionCode(sectionCode);

        inboundOrder.setOrderDate(inboundOrderRequestDTO.getOrderDate());
        inboundOrder.setSection(section);
        return inboundOrder;
    }

    public BatchStockResponseDTO inboundOrderToBatchStockResponseDTO(InboundOrder inboundOrder) {

        List<Batch> batches = batchRepository.findBatchesByInboundOrder_OrderNumber(inboundOrder.getOrderNumber());

        List<BatchStockDTO> batchStockDTOList = new ArrayList<>();

        batches.forEach(batch -> {
            BatchStockDTO batchStockDTO = batchServiceImpl.convertBatchToBatchStockDTO(batch);
            batchStockDTOList.add(batchStockDTO);
        });

        BatchStockResponseDTO batchStockResponseDTO = new BatchStockResponseDTO();
        batchStockResponseDTO.setOrderNumber(inboundOrder.getOrderNumber());
        batchStockResponseDTO.setOrderDate(inboundOrder.getOrderDate());
        batchStockResponseDTO.setBatchStock(batchStockDTOList);

        return batchStockResponseDTO;
    }

    @Override
    public void orderNumberExists(Long orderNumber) {
        boolean orderNumberDoesntExists = !inboundOrderRepository.existsByOrderNumber(orderNumber);

        if(orderNumberDoesntExists) {
            throw new RuntimeException("Order number doesn't exists");
        }
    }

    @Override
    public void inboundOrderContainsSectionCode(Long orderNumber, UUID sectionCode) {
        boolean inboundOrderDoesntContainsSectionCode = !inboundOrderRepository.findByOrderNumber(orderNumber)
                .getSection()
                .getSectionCode()
                .equals(sectionCode);

        if(inboundOrderDoesntContainsSectionCode) {
            throw new RuntimeException("Inbound Order doesn't contains Section Code");
        }
    }

    @Override
    public void inboundOrderContainsBatchNumbers(Long orderNumber, List<Long> batchNumbers) {
        batchNumbers.forEach(batchNumber -> {
            boolean inboundOrderDoesntContainsBatchNumber = !batchRepository.findBatchByBatchNumber(batchNumber)
                    .getInboundOrder()
                    .getOrderNumber()
                    .equals(orderNumber);

            if(inboundOrderDoesntContainsBatchNumber) {
                throw new RuntimeException("Inbound Order doesn't contains Batch Number " + batchNumber);
            }
        });
    }

}
