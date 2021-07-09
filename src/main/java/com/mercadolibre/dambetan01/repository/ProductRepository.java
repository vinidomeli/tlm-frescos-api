package com.mercadolibre.dambetan01.repository;

import com.mercadolibre.dambetan01.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
    public Optional<Product> findById(Long productId);
    List<Product> findProductByType(String productType);
}
