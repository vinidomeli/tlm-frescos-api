//package com.mercadolibre.dambetan01.util;
//
//import com.github.javafaker.Faker;
//import com.mercadolibre.dambetan01.model.Warehouse;
//import com.mercadolibre.dambetan01.repository.SupervisorRepository;
//import com.mercadolibre.dambetan01.repository.WarehouseRepository;
//import org.springframework.beans.factory.annotation.Autowired;
//
//import java.util.UUID;
//
//public class WarehouseDB {
//
//    private Faker faker = new Faker();
//
//    WarehouseRepository warehouseRepository;
//
//    SupervisorRepository supervisorRepository;
//
//    public WarehouseDB(WarehouseRepository warehouseRepository, SupervisorRepository supervisorRepository){
//        this.warehouseRepository = warehouseRepository;
//        this.supervisorRepository = supervisorRepository;
//    }
//
//    public UUID insertWarehouse(Long supervisorRegisterNumber){
//        Warehouse wareHouse = Warehouse.builder()
//                .supervisor(supervisorRepository.findByRegisterNumber(supervisorRegisterNumber).get())
//                .location(faker.address().cityName())
//                .build();
//
//        return warehouseRepository.save(wareHouse).getWarehouseCode();
//    }
//}
