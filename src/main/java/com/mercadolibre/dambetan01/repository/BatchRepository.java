package com.mercadolibre.dambetan01.repository;

import com.mercadolibre.dambetan01.model.Batch;
import com.mercadolibre.dambetan01.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BatchRepository extends JpaRepository<Batch, Long> {

    public List<Batch> findBatchesByInboundOrder_OrderNumber(Long orderNumber);

    public Batch findBatchByBatchNumber(Long batchNumber);

    public boolean existsByBatchNumber(Long batchNumber);

    public List<Batch> findBatchesByProduct(Product product);
}
