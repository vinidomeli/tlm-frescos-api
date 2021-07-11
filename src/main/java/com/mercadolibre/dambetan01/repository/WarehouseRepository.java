package com.mercadolibre.dambetan01.repository;

import com.mercadolibre.dambetan01.model.Warehouse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface WarehouseRepository extends JpaRepository<Warehouse, UUID> {

    public boolean existsByWarehouseCode(UUID warehouseCode);
    public Warehouse findByWarehouseCode(UUID warehouseCode);
    public boolean existsBySupervisor_RegisterNumberAndWarehouseCode(Long registerNumber, UUID warehouseCode);
    public boolean existsBySupervisor_RegisterNumber(Long registerNumber);
//    public boolean findBySupervisor_RegisterNumberAndWarehouseCode(Long registerNumber, UUID warehouseCode);

}
