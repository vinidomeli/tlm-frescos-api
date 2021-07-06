package com.mercadolibre.dambetan01.repository;

import com.mercadolibre.dambetan01.model.PurchaseOrder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<PurchaseOrder, Long> {
    public List<PurchaseOrder> findOrdersByProducts(PurchaseOrder purchaseOrder);
}
