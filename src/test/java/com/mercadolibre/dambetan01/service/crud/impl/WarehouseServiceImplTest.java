package com.mercadolibre.dambetan01.service.crud.impl;

import com.github.javafaker.Faker;
import com.mercadolibre.dambetan01.exceptions.ApiException;
import com.mercadolibre.dambetan01.model.Seller;
import com.mercadolibre.dambetan01.model.Warehouse;
import com.mercadolibre.dambetan01.model.enums.Roles;
import com.mercadolibre.dambetan01.repository.SellerRepository;
import com.mercadolibre.dambetan01.repository.SupervisorRepository;
import com.mercadolibre.dambetan01.repository.UserRepository;
import com.mercadolibre.dambetan01.repository.WarehouseRepository;
import com.mercadolibre.dambetan01.service.crud.WarehouseService;
import com.mercadolibre.dambetan01.util.ProductDB;
import com.mercadolibre.dambetan01.util.UserDB;
import com.mercadolibre.dambetan01.util.WarehouseDB;
import net.bytebuddy.implementation.bind.annotation.Super;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfigurationPackage;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class WarehouseServiceImplTest {

    @Autowired
    WarehouseRepository warehouseRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    SellerRepository sellerRepository;

    @Autowired
    SupervisorRepository supervisorRepository;

    @Autowired
    WarehouseService warehouseService;

    private Faker faker = new Faker();

    @Test
    void warehouseExists() {
        Long supervisorId = insertASupervisorAndGetId();

        UUID warehouseId = insertAWarehouseGiven(supervisorId);

        assertDoesNotThrow(() -> {
            warehouseService.warehouseExists(warehouseId);
        });
    }

    @Test
    void warehouseThatDoesntExists() {
        UUID fakeWarehouseId = UUID.fromString(faker.internet().uuid());

        assertThrows(ApiException.class, () -> {
            warehouseService.warehouseExists(fakeWarehouseId);
        });
    }

    public Long insertASupervisorAndGetId(){
        UserDB userDB = new UserDB(userRepository, sellerRepository, supervisorRepository);
        return userDB.insertSupervisor(userDB.insertUser(Roles.SUPERVISOR));
    }

    public UUID insertAWarehouseGiven(Long supervisorId){
        WarehouseDB warehouseDB = new WarehouseDB(warehouseRepository, supervisorRepository);
        return warehouseDB.insertWarehouse(supervisorId);
    }
}