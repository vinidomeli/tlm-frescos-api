package com.mercadolibre.dambetan01.service.crud.impl;

import com.mercadolibre.dambetan01.dtos.AuthDTO;
import com.mercadolibre.dambetan01.dtos.UserDTO;
import com.mercadolibre.dambetan01.exceptions.ApiException;
import com.mercadolibre.dambetan01.model.User;
import com.mercadolibre.dambetan01.model.enums.Roles;
import com.mercadolibre.dambetan01.repository.UserRepository;
import com.mercadolibre.dambetan01.service.crud.UserService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDTO create(UserDTO userCredentials) {
        Roles role = Roles.toEnum(userCredentials.getRole());

        if (existsBy(userCredentials.getLogin())){
            throw new ApiException("400","User already exists!", 400);
        }

        User user = User.builder()
                .name(userCredentials.getName())
                .password(passwordEncoder.encode(userCredentials.getPassword()))
                .login(userCredentials.getLogin())
                .role(role.getDescription())
                .build();

        return UserDTO.fromEntity(userRepository.save(user));
    }

    protected boolean existsBy(String login){
        return userRepository.findByLogin(login).isPresent();
    }
}
