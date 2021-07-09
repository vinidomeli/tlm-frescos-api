package com.mercadolibre.dambetan01.service.crud.impl;

import com.mercadolibre.dambetan01.dtos.WarehouseDTO;
import com.mercadolibre.dambetan01.exceptions.ApiException;
import com.mercadolibre.dambetan01.repository.WarehouseRepository;
import com.mercadolibre.dambetan01.service.crud.WarehouseService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class WarehouseServiceImpl implements WarehouseService {

    WarehouseRepository warehouseRepository;

    public WarehouseServiceImpl(WarehouseRepository warehouseRepository) {
        this.warehouseRepository = warehouseRepository;
    }

    @Override
    public void warehouseExists(UUID warehouseCode) {
        boolean warehouseCodeDoesntExists = !this.warehouseRepository.existsByWarehouseCode(warehouseCode);

        if (warehouseCodeDoesntExists) {
            throw new ApiException("404", "Warehouse Code doesn't exists", 404);
        }
    }

    @Override
    public List<WarehouseDTO> findAll() {
        return this.warehouseRepository.findAll().stream().map(WarehouseDTO::toDTO).collect(Collectors.toList());
    }


}
