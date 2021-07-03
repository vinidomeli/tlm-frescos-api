package com.mercadolibre.dambetan01.service.crud.impl;

import com.mercadolibre.dambetan01.repository.WarehouseRepository;
import com.mercadolibre.dambetan01.service.crud.WarehouseService;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class WarehouseServiceImpl implements WarehouseService {

    WarehouseRepository warehouseRepository;

    public WarehouseServiceImpl(final WarehouseRepository warehouseRepository) {
        this.warehouseRepository = warehouseRepository;
    }

    @Override
    public void warehouseExists(UUID warehouseCode) {
        boolean warehouseCodeDoesntExists = !warehouseRepository.existsByWarehouseCode(warehouseCode);

        if(warehouseCodeDoesntExists) {
            throw new RuntimeException("Warehouse Code coesn't exists");
        }
    }


}
