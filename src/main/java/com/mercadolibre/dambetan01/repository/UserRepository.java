package com.mercadolibre.dambetan01.repository;

import com.mercadolibre.dambetan01.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface UserRepository extends JpaRepository<User, UUID> {

    Optional<User> findByLogin(String login);
    boolean existsByLogin(String login);
    Optional<User> findByRole(String role);
}
