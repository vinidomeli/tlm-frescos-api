//package com.mercadolibre.dambetan01.service.crud.impl;
//
//import com.github.javafaker.Faker;
//import com.mercadolibre.dambetan01.dtos.BatchDueDateDTO;
//import com.mercadolibre.dambetan01.dtos.WarehouseDTO;
//import com.mercadolibre.dambetan01.dtos.response.BatchStockDueDateDTO;
//import com.mercadolibre.dambetan01.exceptions.ApiException;
//import com.mercadolibre.dambetan01.model.*;
//import com.mercadolibre.dambetan01.repository.BatchRepository;
//import com.mercadolibre.dambetan01.repository.WarehouseRepository;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.extension.ExtendWith;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//
//import org.mockito.junit.jupiter.MockitoExtension;
//
//import java.time.LocalDate;
//import java.util.ArrayList;
//import java.util.Arrays;
//import java.util.List;
//import java.util.UUID;
//
//import static org.junit.jupiter.api.Assertions.*;
//import static org.mockito.ArgumentMatchers.any;
//import static org.mockito.Mockito.when;
//
//@ExtendWith(MockitoExtension.class)
//class WarehouseServiceImplTest {
//
//    @Mock
//    WarehouseRepository warehouseRepository;
//    @Mock
//    BatchRepository batchRepository;
//
//    @InjectMocks
//    WarehouseServiceImpl warehouseService;
//
//    private final Faker faker = new Faker();
//
//    @Test
//    void warehouseCodeThatExistsTest() {
//        UUID newWarehouseCode = UUID.fromString(faker.internet().uuid());
//
//        when(warehouseRepository.existsByWarehouseCode(any())).thenReturn(true);
//        assertDoesNotThrow(() -> {
//            warehouseService.warehouseExists(newWarehouseCode);
//        });
//    }
//
//    @Test
//    void warehouseCodeDoesntExistsTest() {
//        UUID registeredWarehouseCode = UUID.fromString(faker.internet().uuid());
//
//        when(warehouseRepository.existsByWarehouseCode(any())).thenReturn(false);
//        assertThrows(ApiException.class, () -> {
//            warehouseService.warehouseExists(registeredWarehouseCode);
//        });
//    }
//
//    @Test
//    void getAllBatchesWarehouseTest() {
//        Integer quantityDays = 10;
//
//        Product product = Product.builder().build();
//
//        Batch batch = Batch.builder()
//                .batchNumber(faker.number().randomNumber())
//                .product(product)
//                .productType("Refrigerate")
//                .dueDate(LocalDate.now())
//                .currentQuantity(faker.number().numberBetween(1,10))
//                .build();
//        List<Batch> batchList = Arrays.asList(batch);
//
//        List<BatchDueDateDTO> batchDueDateDTOList = new ArrayList<>();
//        BatchDueDateDTO batchDueDateDTO = BatchDueDateDTO.builder()
//                .batchNumber(batch.getBatchNumber())
//                .productId(batch.getProduct().getId())
//                .productTypeId(batch.getProductType())
//                .dueDate(batch.getDueDate())
//                .quantity(batch.getCurrentQuantity())
//                .build();
//        batchDueDateDTOList.add(batchDueDateDTO);
//
//        BatchStockDueDateDTO batchStockDueDateDTO = BatchStockDueDateDTO.builder()
//                .batchStockDueDate(batchDueDateDTOList)
//                .build();
//
//        when(batchRepository.findBatchesByDueDateLessThanEqualOrderByDueDateDesc(any())).thenReturn(batchList);
//        assertEquals(batchStockDueDateDTO, warehouseService.getAllBatchesWarehouse(quantityDays));
//    }
//
//    @Test
//    void getAllBatchesWarehouseByCategoryTest() {
//        Integer quantityDays = 20;
//        String productType = "Refrigerate";
//        String order = "asc";
//
//        Product product = Product.builder().build();
//
//        Batch batch1 = Batch.builder()
//                .batchNumber(faker.number().randomNumber())
//                .product(product)
//                .productType("Refrigerate")
//                .dueDate(LocalDate.now().plusDays(2))
//                .currentQuantity(faker.number().numberBetween(1,10))
//                .build();
//        Batch batch2 = Batch.builder()
//                .batchNumber(faker.number().randomNumber())
//                .product(product)
//                .productType("Refrigerate")
//                .dueDate(LocalDate.now().plusDays(10))
//                .currentQuantity(faker.number().numberBetween(1,10))
//                .build();
//        List<Batch> batchList = Arrays.asList(batch1, batch2);
//
//        List<BatchDueDateDTO> batchDueDateDTOList = new ArrayList<>();
//        for (Batch batch : batchList) {
//            BatchDueDateDTO batchDueDateDTO = BatchDueDateDTO.builder()
//                    .batchNumber(batch.getBatchNumber())
//                    .productId(batch.getProduct().getId())
//                    .productTypeId(batch.getProductType())
//                    .dueDate(batch.getDueDate())
//                    .quantity(batch.getCurrentQuantity())
//                    .build();
//            batchDueDateDTOList.add(batchDueDateDTO);
//        }
//
//        BatchStockDueDateDTO batchStockDueDateDTO = BatchStockDueDateDTO.builder()
//                .batchStockDueDate(batchDueDateDTOList)
//                .build();
//
////        when(batchRepository.findBatchesByDueDateLessThanEqualAndProductTypeOrderByDueDateDesc(any(), any())).thenReturn(batchListDesc);
//        when(batchRepository.findBatchesByDueDateLessThanEqualAndProductTypeOrderByDueDateAsc(any(), any())).thenReturn(batchList);
//        assertEquals(batchStockDueDateDTO, warehouseService.getAllBatchesWarehouseByCategory(quantityDays, productType, order));
//    }
//
//    @Test
//    void findAllTest() {
//        Long registerNumber = faker.number().randomNumber();
//        Supervisor supervisor = Supervisor.builder()
//                .registerNumber(registerNumber)
//                .build();
//
//        UUID warehouseCode = UUID.fromString(faker.internet().uuid());
//        String location = faker.address().cityName();
//        Warehouse warehouse = Warehouse.builder()
//                .warehouseCode(warehouseCode)
//                .supervisor(supervisor)
//                .location(location)
//                .build();
//        List<Warehouse> warehouseList = Arrays.asList(warehouse);
//
//        WarehouseDTO warehouseDTO = WarehouseDTO.builder()
//                .warehouseCode(warehouse.getWarehouseCode())
//                .supervisorRegister(warehouse.getSupervisor().getRegisterNumber())
//                .location(warehouse.getLocation())
//                .build();
//        List<WarehouseDTO> warehouseDTOList = Arrays.asList(warehouseDTO);
//
//        when(warehouseRepository.findAll()).thenReturn(warehouseList);
//        assertEquals(warehouseDTOList, warehouseService.findAll());
//    }
//}