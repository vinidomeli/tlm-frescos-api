package com.mercadolibre.dambetan01.repository;

import com.mercadolibre.dambetan01.model.Seller;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface SellerRepository extends JpaRepository<Seller, UUID> {

    Optional<Seller> findByCnpj(String cnpj);
    Optional<Seller> findByUser_Id(UUID userId);
    boolean existsByCnpj(String cnpj);

}
