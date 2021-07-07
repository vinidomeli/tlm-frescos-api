package com.mercadolibre.dambetan01.repository;

import com.mercadolibre.dambetan01.model.Batch;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface BatchRepository extends JpaRepository<Batch, Long> {

    public List<Batch> findBatchesByInboundOrder_OrderNumber(Long orderNumber);
    public List<Batch> findBatchesByProduct_Id(Long productId);
    public List<Batch> findBatchesByProduct_IdAndDueDateGreaterThanOrderByCurrentQuantity(Long productId, LocalDate localDate);
    public List<Batch> findBatchesByProduct_IdAndDueDateGreaterThanOrderByDueDateAsc(Long productId, LocalDate localDate);
    public Batch findBatchByBatchNumber(Long batchNumber);
    public boolean existsByBatchNumber(Long batchNumber);
}
