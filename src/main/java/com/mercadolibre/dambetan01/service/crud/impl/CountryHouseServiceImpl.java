package com.mercadolibre.dambetan01.service.crud.impl;

import com.mercadolibre.dambetan01.dtos.CountryHouseDTO;
import com.mercadolibre.dambetan01.model.CountryHouse;
import com.mercadolibre.dambetan01.repository.CountryHouseRepository;
import com.mercadolibre.dambetan01.service.crud.ICountryHouseService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CountryHouseServiceImpl implements ICountryHouseService {

    private CountryHouseRepository  countryHouseRepository;

    private ModelMapper modelMapper;

    public CountryHouseServiceImpl(CountryHouseRepository countryHouseRepository, ModelMapper modelMapper) {
        this.countryHouseRepository = countryHouseRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    @Transactional
    public CountryHouseDTO create(CountryHouseDTO countryHouseDTO) {
        if (countryHouseRepository.findByCountry(countryHouseDTO.getCountry())==null){
            CountryHouse newCountryHouse = modelMapper.map(countryHouseDTO, CountryHouse.class);
            countryHouseRepository.save(newCountryHouse);
        }else{
            countryHouseDTO=null;
        }

        return countryHouseDTO;
    }

    @Override
    @Transactional
    public CountryHouseDTO update(CountryHouseDTO countryHouseDTO) {
        CountryHouse newCountryHouse = modelMapper.map(countryHouseDTO, CountryHouse.class);
        countryHouseRepository.save(newCountryHouse);
        return countryHouseDTO;
    }

    @Override
    @Transactional
    public void delete(Long id) {
        Optional<CountryHouse> opt = countryHouseRepository.findById(id);
        if (!opt.isPresent()) {
            throw new NoSuchElementException("No existe empleado con el id: " + id);
        }
        countryHouseRepository.deleteById(id);
    }

    @Override
    public CountryHouseDTO findById(Long id) {
        Optional<CountryHouse> opt = countryHouseRepository.findById(id);

        if (!opt.isPresent()) {
            throw new NoSuchElementException("No existe empleado con el id: " + id);
        }
        return modelMapper.map(opt.get(), CountryHouseDTO.class);
    }

    @Override
    public List<CountryHouseDTO> findAll() {
        List<CountryHouseDTO> countryHousesDTO = countryHouseRepository.findAll()
                .stream()
                .map(countryHouse -> modelMapper.map(countryHouse, CountryHouseDTO.class))
                .collect(Collectors.toList());
        return countryHousesDTO;
    }

    @Override
    public CountryHouseDTO findByCountry(String country) {
        CountryHouse contryHouse = countryHouseRepository.findByCountry(country);
        return modelMapper.map(contryHouse, CountryHouseDTO.class);
    }
}
