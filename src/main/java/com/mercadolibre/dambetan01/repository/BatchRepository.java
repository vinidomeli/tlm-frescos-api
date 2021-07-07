package com.mercadolibre.dambetan01.repository;

import com.mercadolibre.dambetan01.model.Batch;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface BatchRepository extends JpaRepository<Batch, Long> {

    List<Batch> findBatchesByInboundOrder_OrderNumber(Long orderNumber);
    List<Batch> findBatchesByProduct_Id(Long productId);
    List<Batch> findBatchesByProduct_IdAndDueDateGreaterThanOrderByCurrentQuantity(Long productId, LocalDate localDate);
    List<Batch> findBatchesByProduct_IdAndDueDateGreaterThanOrderByDueDateAsc(Long productId, LocalDate localDate);
    Batch findBatchByBatchNumber(Long batchNumber);
    boolean existsByBatchNumber(Long batchNumber);
    List<Batch> findBatchesByDueDateLessThanEqualOrderByDueDateDesc(LocalDate localDate);
    List<Batch> findBatchesByDueDateLessThanEqualAndProductTypeOrderByDueDateAsc(LocalDate localDate, String productType);
    List<Batch> findBatchesByDueDateLessThanEqualAndProductTypeOrderByDueDateDesc(LocalDate localDate, String productType);
}
