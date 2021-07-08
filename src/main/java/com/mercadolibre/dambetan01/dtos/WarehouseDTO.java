package com.mercadolibre.dambetan01.dtos;

import com.mercadolibre.dambetan01.model.Warehouse;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class WarehouseDTO {

    private UUID warehouseCode;

    private String location;

    private Long supervisorRegister;

    public static WarehouseDTO toDTO(Warehouse warehouse) {
        return WarehouseDTO.builder()
                .warehouseCode(warehouse.getWarehouseCode())
                .location(warehouse.getLocation())
                .supervisorRegister(warehouse.getSupervisor().getRegisterNumber())
                .build();
    }

}
