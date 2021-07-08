package com.mercadolibre.dambetan01.service.crud.impl;

import com.github.javafaker.Faker;
import com.mercadolibre.dambetan01.dtos.BatchStockDTO;
import com.mercadolibre.dambetan01.dtos.request.InboundOrderRequestDTO;
import com.mercadolibre.dambetan01.dtos.request.SectionRequestDTO;
import com.mercadolibre.dambetan01.dtos.response.BatchStockResponseDTO;
import com.mercadolibre.dambetan01.exceptions.ApiException;
import com.mercadolibre.dambetan01.model.Batch;
import com.mercadolibre.dambetan01.model.InboundOrder;
import com.mercadolibre.dambetan01.model.Section;
import com.mercadolibre.dambetan01.repository.BatchRepository;
import com.mercadolibre.dambetan01.repository.InboundOrderRepository;
import com.mercadolibre.dambetan01.repository.ProductRepository;
import com.mercadolibre.dambetan01.repository.SectionRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class InboundOrderServiceImplTest {

    @Mock
    InboundOrderRepository inboundOrderRepository;
    @Mock
    BatchRepository batchRepository;
    @Mock
    SectionRepository sectionRepository;
    @Mock
    BatchServiceImpl batchService;
    @InjectMocks
    InboundOrderServiceImpl inboundOrderService;

    Faker faker = new Faker();

    @Test
    void inboundOrderRequestDTOToInboundOrderTest() {

        SectionRequestDTO sectionRequestDTO = SectionRequestDTO.builder()
                .sectionCode(UUID.fromString(faker.internet().uuid()))
                .build();

        Section section = Section.builder()
                .sectionCode(sectionRequestDTO.getSectionCode())
                .build();

        InboundOrderRequestDTO inboundOrderRequestDTO = InboundOrderRequestDTO.builder()
                .orderDate(LocalDate.now())
                .section(sectionRequestDTO)
                .build();

        InboundOrder inboundOrder = InboundOrder.builder()
                .orderDate(inboundOrderRequestDTO.getOrderDate())
                .section(section)
                .build();

        when(sectionRepository.findBySectionCode(any())).thenReturn(section);
        assertEquals(inboundOrder, inboundOrderService.inboundOrderRequestDTOToInboundOrder(inboundOrderRequestDTO));
    }

    @Test
    void inboundOrderToBatchStockResponseDTOTest() {

        InboundOrder inboundOrder = InboundOrder.builder()
                .orderNumber(faker.number().randomNumber())
                .orderDate(LocalDate.now())
                .build();

        Batch batch = Batch.builder().build();
        List<Batch> batches = Arrays.asList(batch);
        BatchStockDTO batchStockDTO = BatchStockDTO.builder().build();
        List<BatchStockDTO> batchStockDTOList = Arrays.asList(batchStockDTO);
        BatchStockResponseDTO batchStockResponseDTO = BatchStockResponseDTO.builder()
                .orderNumber(inboundOrder.getOrderNumber())
                .orderDate(inboundOrder.getOrderDate())
                .batchStock(batchStockDTOList)
                .build();

        when(batchRepository.findBatchesByInboundOrder_OrderNumber(any())).thenReturn(batches);
        when(batchService.convertBatchToBatchStockDTO(any())).thenReturn(batchStockDTO);
        assertEquals(batchStockResponseDTO, inboundOrderService.inboundOrderToBatchStockResponseDTO(inboundOrder));
    }

    @Test
    void orderNumberExists() {
        Long registeredOrderNumber = 100L;

        when(inboundOrderRepository.existsByOrderNumber(any())).thenReturn(true);
        assertDoesNotThrow(() -> {
            inboundOrderService.orderNumberExists(registeredOrderNumber);
        });
    }

    @Test
    void orderNumberDoesntExists() {
        Long newOrderNumber = 100L;

        when(inboundOrderRepository.existsByOrderNumber(any())).thenReturn(false);
        assertThrows(ApiException.class, () -> {
           inboundOrderService.orderNumberExists(newOrderNumber);
        });
    }

    @Test
    void inboundOrderContainsSectionCode() {
        UUID registeredSectionCode = UUID.fromString(faker.internet().uuid());
        Long registeredOrderNumber = 10L;

        InboundOrder inboundOrder = mock(InboundOrder.class);
        Section registeredSection = mock(Section.class);
        when(inboundOrderRepository.findByOrderNumber(any())).thenReturn(inboundOrder);
        when(inboundOrder.getSection()).thenReturn(registeredSection);
        when(registeredSection.getSectionCode()).thenReturn(registeredSectionCode);
        assertDoesNotThrow(() -> {
            inboundOrderService.inboundOrderContainsSectionCode(registeredOrderNumber, registeredSectionCode);
        });
    }

    @Test
    void inboundOrderDoesntContainsSectionCode() {
        UUID newSectionCode = UUID.fromString(faker.internet().uuid());
        UUID registeredSectionCode = UUID.fromString(faker.internet().uuid());
        Long registeredOrderNumber = 10L;

        InboundOrder inboundOrder = mock(InboundOrder.class);
        Section registeredSection = mock(Section.class);
        when(inboundOrderRepository.findByOrderNumber(any())).thenReturn(inboundOrder);
        when(inboundOrder.getSection()).thenReturn(registeredSection);
        when(registeredSection.getSectionCode()).thenReturn(registeredSectionCode);
        assertThrows(ApiException.class, () -> {
            inboundOrderService.inboundOrderContainsSectionCode(registeredOrderNumber, newSectionCode);
        });
    }

    @Test
    void inboundOrderContainsBatchNumbers() {
        Long registeredOrderNumber = 10L;
        List<Long> registeredBatchNumbers = Arrays.asList(10L, 11L);

        InboundOrder registeredInboundOrder = mock(InboundOrder.class);
        Batch registeredBatch = mock(Batch.class);
        when(batchRepository.findBatchByBatchNumber(any())).thenReturn(registeredBatch);
        when(registeredBatch.getInboundOrder()).thenReturn(registeredInboundOrder);
        when(registeredInboundOrder.getOrderNumber()).thenReturn(registeredOrderNumber);
        assertDoesNotThrow(() -> {
            inboundOrderService.inboundOrderContainsBatchNumbers(registeredOrderNumber, registeredBatchNumbers);
        });
    }

    @Test
    void inboundOrderDoesntContainsBatchNumbers() {
        Long registeredOrderNumber = 10L;
        Long newOrderNumber = 11L;
        List<Long> registeredBatchNumbers = Arrays.asList(10L, 11L);

        Batch newBatch = mock(Batch.class);
        InboundOrder newInboundOrder = mock(InboundOrder.class);
        when(batchRepository.findBatchByBatchNumber(any())).thenReturn(newBatch);
        when(newBatch.getInboundOrder()).thenReturn(newInboundOrder);
        when(newInboundOrder.getOrderNumber()). thenReturn(registeredOrderNumber);
        assertThrows(ApiException.class, () -> {
           inboundOrderService.inboundOrderContainsBatchNumbers(newOrderNumber, registeredBatchNumbers);
        });
    }
}