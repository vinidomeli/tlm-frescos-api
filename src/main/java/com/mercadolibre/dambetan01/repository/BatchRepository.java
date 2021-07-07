package com.mercadolibre.dambetan01.repository;

import com.mercadolibre.dambetan01.model.Batch;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BatchRepository extends JpaRepository<Batch, Long> {

    List<Batch> findBatchesByInboundOrder_OrderNumber(Long orderNumber);
    Batch findBatchByBatchNumber(Long batchNumber);
    boolean existsByBatchNumber(Long batchNumber);
}
