package com.mercadolibre.dambetan01.service.crud.impl;

import com.mercadolibre.dambetan01.exceptions.ApiException;
import com.mercadolibre.dambetan01.repository.BatchRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class BatchServiceImplTest {

    @Mock
    BatchRepository batchRepository;

    @InjectMocks
    BatchServiceImpl batchServiceImpl;

    @Test
    void batchNumbersExist() {

        List<Long> registeredBatchNumbers = Arrays.asList(1L, 2L, 10L, 100L);

        when(batchRepository.existsByBatchNumber(any())).thenReturn(true);
        assertDoesNotThrow(() -> {
            batchServiceImpl.batchNumbersExist(registeredBatchNumbers);
        });
    }

    @Test
    void batchNumbersDoesntExist() {

        List<Long> newBatchNumbers = Arrays.asList(1L, 2L);

        when(batchRepository.existsByBatchNumber(any())).thenReturn(false);
        assertThrows(ApiException.class, () -> {
            batchServiceImpl.batchNumbersExist(newBatchNumbers);
        });

    }
}