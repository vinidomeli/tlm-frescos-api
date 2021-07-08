package com.mercadolibre.dambetan01.util;

import com.github.javafaker.Faker;
import com.mercadolibre.dambetan01.model.Seller;
import com.mercadolibre.dambetan01.model.Supervisor;
import com.mercadolibre.dambetan01.model.User;
import com.mercadolibre.dambetan01.model.enums.Reputation;
import com.mercadolibre.dambetan01.model.enums.Roles;
import com.mercadolibre.dambetan01.repository.SellerRepository;
import com.mercadolibre.dambetan01.repository.SupervisorRepository;
import com.mercadolibre.dambetan01.repository.UserRepository;
import lombok.NoArgsConstructor;

import java.util.UUID;

public class UserDB {

    private Faker faker = new Faker();

    UserRepository userRepository;
    SupervisorRepository supervisorRepository;
    SellerRepository sellerRepository;

    public UserDB(UserRepository userRepository, SellerRepository sellerRepository,
                  SupervisorRepository supervisorRepository) {
        this.userRepository = userRepository;
        this.supervisorRepository = supervisorRepository;
        this.sellerRepository = sellerRepository;
    }

    public UUID insertUser(Roles role) {

        User user = User.builder()
                .login(faker.name().username())
                .name(faker.name().firstName())
                .password(faker.internet().password())
                .role(role.getDescription())
                .build();

        User userResponse = userRepository.save(user);

        return userResponse.getId();
    }

    public Long insertSupervisor(UUID userId) {
        Supervisor supervisor = Supervisor.builder()
                .user(userRepository.findById(userId).get())
                .build();

        return supervisorRepository.save(supervisor).getRegisterNumber();
    }

    public String insertSeller(UUID userId) {

        Integer reputationId = faker.number().numberBetween(1,3);
        Seller seller = Seller.builder()
                .cnpj(faker.idNumber().ssnValid())
                .reputation(Reputation.toEnum(reputationId).getDescription())
                .user(userRepository.findById(userId).get())
                .build();

        return sellerRepository.save(seller).getCnpj();
    }

}
