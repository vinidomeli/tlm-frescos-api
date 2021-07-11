package com.mercadolibre.dambetan01.service.crud.impl;

import com.mercadolibre.dambetan01.dtos.BatchStockDTO;
import com.mercadolibre.dambetan01.dtos.UpdateBatchStockDTO;
import com.mercadolibre.dambetan01.dtos.request.InboundOrderRequestDTO;
import com.mercadolibre.dambetan01.dtos.request.SectionRequestDTO;
import com.mercadolibre.dambetan01.dtos.request.UpdateInboundOrderDTO;
import com.mercadolibre.dambetan01.dtos.response.BatchStockResponseDTO;
import com.mercadolibre.dambetan01.exceptions.ApiException;
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
import java.util.stream.Collectors;

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
    public BatchStockResponseDTO updateInboundOrder(UpdateInboundOrderDTO updateInboundOrderDTO) {

        InboundOrder inboundOrder = inboundOrderRepository.findByOrderNumber(updateInboundOrderDTO.getOrderNumber());
        inboundOrder.setOrderDate(updateInboundOrderDTO.getOrderDate());
        inboundOrderRepository.save(inboundOrder);

        Integer sizeDifferenceAfterUpdate = batchStockSizeDifferenceAfterUpdate(updateInboundOrderDTO);
        saveBatchStock(updateInboundOrderDTO);
        updateSectionCurrentSize(updateInboundOrderDTO, sizeDifferenceAfterUpdate);

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

    public void saveBatchStock(UpdateInboundOrderDTO updateInboundOrderDTO) {
        updateInboundOrderDTO.getBatchStock().forEach(updateBatchStockDTO -> {
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

    public void updateSectionCurrentSize(UpdateInboundOrderDTO updateInboundOrderDTO,
                                         Integer sizeDifferenceAfterUpdate) {
        UUID sectionCode = updateInboundOrderDTO.getSection().getSectionCode();
        Section section = sectionRepository.findBySectionCode(sectionCode);
        Integer updatedCurrentSize = section.getCurrentSize() + sizeDifferenceAfterUpdate;
        section.setCurrentSize(updatedCurrentSize);
        sectionRepository.save(section);
    }

    @Override
    public Integer batchStockSizeDifferenceAfterUpdate(UpdateInboundOrderDTO updateInboundOrderDTO) {
        return updateInboundOrderDTO.getBatchStock().stream()
                .map(updateBatchStockDTO -> updateBatchStockDTO.getCurrentQuantity() -
                        batchRepository.findBatchByBatchNumber(updateBatchStockDTO.getBatchNumber()).getCurrentQuantity())
                .reduce(0, Integer::sum);
    }

    @Override
    public List<UpdateInboundOrderDTO> listInboundOrderFromSupervisor(UUID userId) {
        List<InboundOrder> inboundOrderList = inboundOrderRepository
                .findAllBySection_Warehouse_Supervisor_User_Id(userId)
                .orElseThrow(() -> new ApiException("404", "There are no inbound orders for this supervisor", 404));

        List<UpdateInboundOrderDTO> inboundOrderDTOList = new ArrayList<>();
        inboundOrderList.forEach(inboundOrder -> {
            UpdateInboundOrderDTO updateInboundOrderDTO = UpdateInboundOrderDTO.builder()
                    .orderDate(inboundOrder.getOrderDate())
                    .orderNumber(inboundOrder.getOrderNumber())
                    .section(SectionRequestDTO.fromEntity(inboundOrder.getSection()))
                    .batchStock(batchRepository
                            .findBatchesByInboundOrder_OrderNumber(inboundOrder.getOrderNumber())
                            .stream()
                            .map(UpdateBatchStockDTO::fromEntity)
                            .collect(Collectors.toList()))
                    .build();
            inboundOrderDTOList.add(updateInboundOrderDTO);
        });
        return inboundOrderDTOList;
    }

}
