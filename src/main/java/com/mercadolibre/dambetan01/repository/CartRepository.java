package com.mercadolibre.dambetan01.repository;

import com.mercadolibre.dambetan01.model.Cart;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface CartRepository extends JpaRepository<Cart, UUID> {
    Optional<Cart> findByUserId(UUID userId);

    List<Cart> findAllByUserId(UUID userId);
}
