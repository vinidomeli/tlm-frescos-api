package com.mercadolibre.dambetan01.service.crud.impl;

import com.mercadolibre.dambetan01.dtos.SellerDTO;
import com.mercadolibre.dambetan01.dtos.UserDTO;
import com.mercadolibre.dambetan01.exceptions.ApiException;
import com.mercadolibre.dambetan01.model.Seller;
import com.mercadolibre.dambetan01.model.Supervisor;
import com.mercadolibre.dambetan01.model.User;
import com.mercadolibre.dambetan01.model.enums.Reputation;
import com.mercadolibre.dambetan01.model.enums.Roles;
import com.mercadolibre.dambetan01.repository.SellerRepository;
import com.mercadolibre.dambetan01.repository.SupervisorRepository;
import com.mercadolibre.dambetan01.repository.UserRepository;
import com.mercadolibre.dambetan01.service.crud.UserService;
import net.bytebuddy.implementation.bind.annotation.Super;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    UserRepository userRepository;
    SupervisorRepository supervisorRepository;
    SellerRepository sellerRepository;

    public UserServiceImpl(UserRepository userRepository, SupervisorRepository supervisorRepository,
                           SellerRepository sellerRepository) {
        this.userRepository = userRepository;
        this.supervisorRepository = supervisorRepository;
        this.sellerRepository = sellerRepository;
    }

    @Override
    public UserDTO create(UserDTO userCredentials) {
        Roles role = Roles.toEnum(userCredentials.getRole());

        if (existsBy(userCredentials.getLogin())) {
            throw new ApiException("400", "User already exists!", 400);
        }

        User user = User.builder()
                .name(userCredentials.getName())
                .password(passwordEncoder.encode(userCredentials.getPassword()))
                .login(userCredentials.getLogin())
                .role(role.getDescription())
                .build();

        User userSaved = userRepository.save(user);

        if(role.getDescription() == "Supervisor") {
            createSupervisorByUser(userSaved);
        }

        return UserDTO.fromEntity(userSaved);
    }

    @Override
    public SellerDTO createSeller(SellerDTO sellerDTO) {
        boolean sellerExistsByCNPJ = sellerRepository.existsByCnpj(sellerDTO.getCnpj());
        if(sellerExistsByCNPJ) {
            throw new ApiException("400", "CNPJ already exists", 400);
        }

        UserDTO userDTO = UserDTO.builder()
                .login(sellerDTO.getLogin())
                .name(sellerDTO.getName())
                .password(sellerDTO.getPassword())
                .role(Roles.SELLER.getDescription())
                .build();
        create(userDTO);

        Seller seller = Seller.builder()
                .cnpj(sellerDTO.getCnpj())
                .reputation(Reputation.GOOD.getDescription())
                .user(userRepository.findByLogin(userDTO.getLogin())
                        .orElseThrow(() -> new ApiException("404", "User not found", 404)))
                .build();
        Seller savedSeller = sellerRepository.save(seller);
        return SellerDTO.fromEntity(savedSeller);
    }

    @Override
    public User findByLogin(String login) {
        return userRepository.findByLogin(login)
                .orElseThrow(() -> new ApiException("404", "User not found", 404));
    }

    protected boolean existsBy(String login) {
        return userRepository.findByLogin(login).isPresent();
    }

    public void createSupervisorByUser(User user) {
        Supervisor supervisor = Supervisor.builder()
                .user(user)
                .build();
        supervisorRepository.save(supervisor);
    }
}
