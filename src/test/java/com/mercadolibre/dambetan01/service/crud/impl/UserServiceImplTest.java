package com.mercadolibre.dambetan01.service.crud.impl;

import com.github.javafaker.Faker;
import com.mercadolibre.dambetan01.dtos.UserDTO;
import com.mercadolibre.dambetan01.exceptions.ApiException;
import com.mercadolibre.dambetan01.model.enums.Roles;
import com.mercadolibre.dambetan01.repository.SellerRepository;
import com.mercadolibre.dambetan01.repository.SupervisorRepository;
import com.mercadolibre.dambetan01.repository.UserRepository;
import com.mercadolibre.dambetan01.repository.WarehouseRepository;
import com.mercadolibre.dambetan01.service.crud.UserService;
import com.mercadolibre.dambetan01.service.crud.WarehouseService;
import com.mercadolibre.dambetan01.util.UserDB;
import com.mercadolibre.dambetan01.util.WarehouseDB;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class UserServiceImplTest {

    @Autowired
    UserRepository userRepository;

    @Autowired
    UserServiceImpl userService;

    private final Faker faker = new Faker();


    @Test
    void createANewUser() {
        String createdUserLogin = createAnUserGiven(faker.name().username());

        boolean userCreated = userRepository.findByLogin(createdUserLogin).isPresent();

        assertTrue(userCreated);
    }

    @Test
    void createAnUserThatAlreadyExists() {
        String createdUserLogin = createAnUserGiven(faker.name().username());

        assertThrows(ApiException.class, () -> {
            createAnUserGiven(createdUserLogin);
        });
    }

    @Test
    void existsByLogin() {
        String createdUserLogin = createAnUserGiven(faker.name().username());

        assertTrue(userService.existsBy(createdUserLogin));
    }

    private String createAnUserGiven(String login){
        UserDTO userDTO = UserDTO.builder()
                .name(faker.name().fullName())
                .login(login)
                .password(faker.internet().password())
                .role(Roles.SELLER.getDescription())
                .build();

        return userService.create(userDTO).getLogin();
    }

}