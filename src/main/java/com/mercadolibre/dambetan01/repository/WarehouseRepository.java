package com.mercadolibre.dambetan01.repository;

import com.mercadolibre.dambetan01.model.Warehouse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface WarehouseRepository extends JpaRepository<Warehouse, UUID> {

    @Query(value = "SELECT CASE WHEN COUNT(w.warehouseCode) > 0 THEN TRUE ELSE FALSE END FROM Warehouse w WHERE w.warehouseCode = ?1", nativeQuery = true)
    public boolean existsByWarehouseCode(UUID warehouseCode);
    public Warehouse findByWarehouseCode(UUID warehouseCode);
}
