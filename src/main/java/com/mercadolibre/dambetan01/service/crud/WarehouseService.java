package com.mercadolibre.dambetan01.service.crud;

import com.mercadolibre.dambetan01.dtos.WarehouseDTO;
import com.mercadolibre.dambetan01.dtos.request.WarehouseRequestDTO;
import com.mercadolibre.dambetan01.dtos.response.WarehouseResponseDTO;

import java.util.List;
import java.util.UUID;

public interface WarehouseService {

    public void warehouseExists(UUID warehouseCode);

    public List<WarehouseDTO> findAll();

    public void warehouseContainsSupervisor(UUID userId, UUID warehouseCode);

    public WarehouseResponseDTO registerWarehouse(WarehouseRequestDTO warehouseRequestDTO);
}
