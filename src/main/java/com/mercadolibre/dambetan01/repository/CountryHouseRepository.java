package com.mercadolibre.dambetan01.repository;

import com.mercadolibre.dambetan01.model.CountryHouse;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CountryHouseRepository extends JpaRepository<CountryHouse, Long> {
    CountryHouse findByCountry(String country);
}
