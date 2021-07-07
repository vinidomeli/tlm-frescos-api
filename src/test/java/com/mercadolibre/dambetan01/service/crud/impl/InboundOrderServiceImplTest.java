package com.mercadolibre.dambetan01.service.crud.impl;

import com.github.javafaker.Faker;
import com.mercadolibre.dambetan01.dtos.request.InboundOrderRequestDTO;
import com.mercadolibre.dambetan01.dtos.request.SectionRequestDTO;
import com.mercadolibre.dambetan01.exceptions.ApiException;
import com.mercadolibre.dambetan01.model.Batch;
import com.mercadolibre.dambetan01.model.InboundOrder;
import com.mercadolibre.dambetan01.model.Section;
import com.mercadolibre.dambetan01.repository.BatchRepository;
import com.mercadolibre.dambetan01.repository.InboundOrderRepository;
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

    @InjectMocks
    InboundOrderServiceImpl inboundOrderService;

    Faker faker = new Faker();

//    @Test
//    void saveInboundOrder() {
//        InboundOrderRequestDTO inboundOrderRequestDTO = mock(InboundOrderRequestDTO.class);
//        InboundOrder inboundOrder = mock(InboundOrder.class);
//
//        SectionRequestDTO sectionRequestDTO = mock(SectionRequestDTO.class);
//        Section section = mock(Section.class);
//        UUID sectionCode = UUID.fromString(faker.internet().uuid());
//        LocalDate orderDate = LocalDate.now();
//
//        when(inboundOrderService.inboundOrderRequestDTOToInboundOrder(any())).thenReturn(inboundOrder);
//        when(inboundOrderRepository.save(inboundOrder)).thenReturn(inboundOrder);
//        when(inboundOrderRequestDTO.getSection()).thenReturn(sectionRequestDTO);
//        when(sectionRequestDTO.getSectionCode()).thenReturn(sectionCode);
//        when(sectionRepository.findBySectionCode(any())).thenReturn(section);
//        when(inboundOrderRequestDTO.getOrderDate()).thenReturn(orderDate);
//        assertEquals(inboundOrder, inboundOrderService.saveInboundOrder(inboundOrderRequestDTO));
//    }

//    @Test
//    void saveBatchStock() {
//        Batch batch = mock(Batch.class);
//        when(batchRepository.save(any())).thenReturn(batch);
//        assertEquals(batch, batchRepository.save(batch));
//    }

//    @Test
//    void updateInboundOrder() {
//    }

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