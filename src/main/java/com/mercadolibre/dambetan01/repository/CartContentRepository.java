package com.mercadolibre.dambetan01.repository;

import com.mercadolibre.dambetan01.model.Cart;
import com.mercadolibre.dambetan01.model.CartContent;
import com.mercadolibre.dambetan01.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CartContentRepository extends JpaRepository<CartContent, Long> {
    List<CartContent> findAllByCart(Cart cart);

    CartContent findByProductAndCart(Product product, Cart cart);
}
