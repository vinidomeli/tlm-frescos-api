package com.mercadolibre.dambetan01.repository;

import com.mercadolibre.dambetan01.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Long> {
    public List<Order> findOrdersByProducts(Order purchaseOrder);
}
