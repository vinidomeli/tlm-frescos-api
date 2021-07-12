package com.mercadolibre.dambetan01.service.crud.impl;

import com.mercadolibre.dambetan01.dtos.BatchDueDateDTO;
import com.mercadolibre.dambetan01.dtos.WarehouseDTO;
import com.mercadolibre.dambetan01.dtos.request.SectionFromWarehouseRequestDTO;
import com.mercadolibre.dambetan01.dtos.request.WarehouseRequestDTO;
import com.mercadolibre.dambetan01.dtos.response.WarehouseResponseDTO;
import com.mercadolibre.dambetan01.exceptions.ApiException;
import com.mercadolibre.dambetan01.model.*;
import com.mercadolibre.dambetan01.repository.*;
import com.mercadolibre.dambetan01.dtos.response.BatchStockDueDateDTO;
import com.mercadolibre.dambetan01.service.crud.WarehouseService;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class WarehouseServiceImpl implements WarehouseService {

    WarehouseRepository warehouseRepository;
    SupervisorRepository supervisorRepository;
    UserRepository userRepository;
    BatchRepository batchRepository;
    SectionRepository sectionRepository;

    public WarehouseServiceImpl(WarehouseRepository warehouseRepository, SupervisorRepository supervisorRepository,
                                UserRepository userRepository, final BatchRepository batchRepository,
                                SectionRepository sectionRepository) {
        this.warehouseRepository = warehouseRepository;
        this.supervisorRepository = supervisorRepository;
        this.userRepository = userRepository;
        this.batchRepository = batchRepository;
        this.sectionRepository = sectionRepository;
    }

    @Override
    public void warehouseExists(UUID warehouseCode) {
        boolean warehouseCodeDoesntExists = !this.warehouseRepository.existsByWarehouseCode(warehouseCode);

        if (warehouseCodeDoesntExists) {
            throw new ApiException("404", "Warehouse Code doesn't exists", 404);
        }
    }

    @Override
    public BatchStockDueDateDTO getAllBatchesWarehouse(Integer quantityDays) {
        LocalDate todayPlusQuantityDays = LocalDate.now().plusDays(quantityDays);
        List<Batch> batchList = batchRepository.findBatchesByDueDateLessThanEqualOrderByDueDateDesc(todayPlusQuantityDays);

        List<BatchDueDateDTO> batchDueDateDTOList = new ArrayList<>();
        batchList.forEach(batch -> {
            BatchDueDateDTO batchDueDateDTO = BatchDueDateDTO.builder()
                    .batchNumber(batch.getBatchNumber())
                    .productId(batch.getProduct().getId())
                    .productTypeId(batch.getProductType())
                    .dueDate(batch.getDueDate())
                    .quantity(batch.getCurrentQuantity())
                    .build();
            batchDueDateDTOList.add(batchDueDateDTO);
        });

        return new BatchStockDueDateDTO(batchDueDateDTOList);
    }

    public BatchStockDueDateDTO getAllBatchesWarehouseByCategory(Integer numberOfDays, String productType, String order) {
        LocalDate todayPlusQuantityDays = LocalDate.now().plusDays(numberOfDays);
    
        List<Batch> batchList;
        if(order.equals("desc")) {
            batchList = batchRepository.findBatchesByDueDateLessThanEqualAndProductTypeOrderByDueDateDesc(todayPlusQuantityDays, productType);
        } else if (order.equals("asc")) {
            batchList = batchRepository.findBatchesByDueDateLessThanEqualAndProductTypeOrderByDueDateAsc(todayPlusQuantityDays, productType);
        } else {
            batchList = null;
        }

        List<BatchDueDateDTO> batchDueDateDTOList = new ArrayList<>();
        if (batchList != null) {
            batchList.forEach(batch -> {
                BatchDueDateDTO batchDueDateDTO = BatchDueDateDTO.builder()
                        .batchNumber(batch.getBatchNumber())
                        .productId(batch.getProduct().getId())
                        .productTypeId(batch.getProductType())
                        .dueDate(batch.getDueDate())
                        .quantity(batch.getCurrentQuantity())
                        .build();
                batchDueDateDTOList.add(batchDueDateDTO);
            });
        }

        return new BatchStockDueDateDTO(batchDueDateDTOList);
    }

    public List<WarehouseDTO> findAll() {
        return this.warehouseRepository.findAll().stream().map(WarehouseDTO::toDTO).collect(Collectors.toList());
    }

    @Override
    public void warehouseContainsSupervisor(UUID userId, UUID warehouseCode) {
        Long registerNumber = this.supervisorRepository.findByUser_Id(userId)
                .orElseThrow(() -> new ApiException("404", "Supervisor doesn't exists", 404))
                .getRegisterNumber();
        boolean warehouseDoesntContainsSupervisor = !this
                .warehouseRepository.existsBySupervisor_RegisterNumberAndWarehouseCode(registerNumber, warehouseCode);

        if(warehouseDoesntContainsSupervisor) {
            throw new ApiException("404", "Supervisor is not registered in warehouse " + warehouseCode, 404);
        }
    }

    @Override
    public WarehouseResponseDTO registerWarehouse(WarehouseRequestDTO warehouseRequestDTO) {

        User user = userRepository.findByLogin(warehouseRequestDTO.getLogin())
                .orElseThrow(() -> new ApiException("404", "User not found", 404));
        Supervisor supervisor = supervisorRepository.findByUser_Id(user.getId())
                .orElseThrow(() -> new ApiException("404", "Supervisor not found", 404));

        boolean supervisorAlreadyBelongsToSomeWarehouse = warehouseRepository
                .existsBySupervisor_RegisterNumber(supervisor.getRegisterNumber());
        if(supervisorAlreadyBelongsToSomeWarehouse) {
            throw new ApiException("400", "Supervisor already belongs to another warehouse", 400);
        }

        Warehouse warehouse = Warehouse.builder()
                .location(warehouseRequestDTO.getLocation())
                .supervisor(supervisor)
                .build();
        Warehouse warehouseSaved = warehouseRepository.save(warehouse);

        List<Section> sections = new ArrayList<>();
        warehouseRequestDTO.getSections().stream()
                .forEach(sectionFromWarehouseRequestDTO -> {
                    Section section = Section.builder()
                            .limitSize(sectionFromWarehouseRequestDTO.getLimitSize())
                            .warehouse(warehouseSaved)
                            .productType(sectionFromWarehouseRequestDTO.getProductType())
                            .temperature(sectionFromWarehouseRequestDTO.getTemperature())
                            .currentSize(0)
                            .build();
                    Section savedSection = sectionRepository.save(section);
                    sections.add(savedSection);
                });
        return WarehouseResponseDTO.builder()
                .warehouseCode(warehouseSaved.getWarehouseCode())
                .location(warehouseSaved.getLocation())
                .supervisorRegisterNumber(warehouseSaved.getSupervisor().getRegisterNumber())
                .userId(warehouseSaved.getSupervisor().getUser().getId())
                .supervisorName(warehouseSaved.getSupervisor().getUser().getName())
                .supervisorLogin(warehouseSaved.getSupervisor().getUser().getLogin())
                .sections(sections.stream().map(SectionFromWarehouseRequestDTO::fromSection).collect(Collectors.toList()))
                .build();
    }
}
