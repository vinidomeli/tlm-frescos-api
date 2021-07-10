package com.mercadolibre.dambetan01.service.crud.impl;

import com.mercadolibre.dambetan01.dtos.BatchStockDTO;
import com.mercadolibre.dambetan01.dtos.SectionDTO;
import com.mercadolibre.dambetan01.dtos.request.InboundOrderRequestDTO;
import com.mercadolibre.dambetan01.dtos.request.UpdateInboundOrderRequestDTO;
import com.mercadolibre.dambetan01.dtos.response.BatchStockResponseDTO;
import com.mercadolibre.dambetan01.exceptions.ApiException;
import com.mercadolibre.dambetan01.model.Batch;
import com.mercadolibre.dambetan01.model.InboundOrder;
import com.mercadolibre.dambetan01.model.Section;
import com.mercadolibre.dambetan01.repository.BatchRepository;
import com.mercadolibre.dambetan01.repository.InboundOrderRepository;
import com.mercadolibre.dambetan01.repository.SectionRepository;
import com.mercadolibre.dambetan01.service.crud.InboundOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
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
        InboundOrder registeredInboundOrder = saveInboundOrder(inboundOrderRequestDTO);
        saveBatchStock(inboundOrderRequestDTO, registeredInboundOrder);
        updateSectionCurrentSize(inboundOrderRequestDTO);

        return inboundOrderToBatchStockResponseDTO(registeredInboundOrder);
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
            throw new ApiException("404", "Order number doesn't exists", 404);
        }
    }

    @Override
    public void inboundOrderContainsSectionCode(Long orderNumber, UUID sectionCode) {
        boolean inboundOrderDoesntContainsSectionCode = !inboundOrderRepository.findByOrderNumber(orderNumber)
                .getSection()
                .getSectionCode()
                .equals(sectionCode);

        if(inboundOrderDoesntContainsSectionCode) {
            throw new ApiException("404", "Inbound Order doesn't contains Section Code", 404);
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
                throw new ApiException("404", "Inbound Order doesn't contains Batch Number " + batchNumber, 404);
            }
        });
    }

    public InboundOrder saveInboundOrder(InboundOrderRequestDTO inboundOrderRequestDTO) {
        InboundOrder inboundOrder = inboundOrderRequestDTOToInboundOrder(inboundOrderRequestDTO);
        return inboundOrderRepository.save(inboundOrder);
    }

    public void saveBatchStock(InboundOrderRequestDTO inboundOrderRequestDTO, InboundOrder registeredInboundOrder) {
        inboundOrderRequestDTO.getBatchStock()
                .forEach(batchStockDTO -> {
                    Batch batch = batchServiceImpl.convertBatchStockDTOToBatch(batchStockDTO, registeredInboundOrder.getOrderNumber());
                    batchRepository.save(batch);
                });
    }

    public void updateSectionCurrentSize(InboundOrderRequestDTO inboundOrderRequestDTO) {
        UUID sectionCode = inboundOrderRequestDTO.getSection().getSectionCode();
        Integer totalInboundOrderSize = inboundOrderRequestDTO.getBatchStock().stream()
                .map(BatchStockDTO::getCurrentQuantity)
                .reduce(0, Integer::sum);
        Section section = sectionRepository.findBySectionCode(sectionCode);
        Integer updatedCurrentSize = section.getCurrentSize() + totalInboundOrderSize;
        section.setCurrentSize(updatedCurrentSize);
        sectionRepository.save(section);
    }



}
