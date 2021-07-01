package com.mercadolibre.dambetan01.service.crud;

import com.mercadolibre.dambetan01.dtos.CountryHouseDTO;

public interface ICountryHouseService extends ICRUD<CountryHouseDTO>{
    CountryHouseDTO findByCountry(String country);
}
