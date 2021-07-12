package com.mercadolibre.dambetan01.repository;

import com.mercadolibre.dambetan01.model.Warehouse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface WarehouseRepository extends JpaRepository<Warehouse, UUID> {

    boolean existsByWarehouseCode(UUID warehouseCode);
    Warehouse findByWarehouseCode(UUID warehouseCode);
    boolean existsBySupervisor_RegisterNumberAndWarehouseCode(Long registerNumber, UUID warehouseCode);
    boolean existsBySupervisor_RegisterNumber(Long registerNumber);
//    boolean findBySupervisor_RegisterNumberAndWarehouseCode(Long registerNumber, UUID warehouseCode);

}
