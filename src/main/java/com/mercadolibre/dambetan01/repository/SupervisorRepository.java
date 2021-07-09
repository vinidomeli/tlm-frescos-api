package com.mercadolibre.dambetan01.repository;

import com.mercadolibre.dambetan01.model.Supervisor;
import org.checkerframework.checker.nullness.Opt;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface SupervisorRepository extends JpaRepository<Supervisor, UUID> {

    Optional<Supervisor> findByRegisterNumber(Long registerNumber);

    Optional<Supervisor> findByUser_Id(UUID userId);

}
