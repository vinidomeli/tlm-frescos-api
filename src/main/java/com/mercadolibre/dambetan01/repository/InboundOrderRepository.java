package com.mercadolibre.dambetan01.repository;

import com.mercadolibre.dambetan01.model.InboundOrder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface InboundOrderRepository extends JpaRepository<InboundOrder, Long> {

    InboundOrder findByOrderNumber(Long orderNumber);
    boolean existsByOrderNumber(Long orderNumber);
}
