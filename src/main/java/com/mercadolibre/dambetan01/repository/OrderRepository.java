package com.mercadolibre.dambetan01.repository;

import com.mercadolibre.dambetan01.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {
    public List<Order> findOrdersByProducts(Order purchaseOrder);
}
