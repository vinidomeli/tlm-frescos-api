package com.mercadolibre.dambetan01.service.impl;

import com.mercadolibre.dambetan01.dtos.UserDTO;
import com.mercadolibre.dambetan01.dtos.response.AccountResponseDTO;
import com.mercadolibre.dambetan01.exceptions.ApiException;
import com.mercadolibre.dambetan01.model.User;
import com.mercadolibre.dambetan01.repository.UserRepository;
import com.mercadolibre.dambetan01.service.UserService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public AccountResponseDTO register(UserDTO userInfo) {

        if (userExistsBy(userInfo.getLogin())){
            throw new ApiException("400","User already exists!", 400);
        }

        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
        String encryptedPassword = bCryptPasswordEncoder.encode(userInfo.getPassword());

        User user = User.builder()
                .name(userInfo.getName())
                .login(userInfo.getLogin())
                .password(encryptedPassword)
                .role(userInfo.getRole())
                .build();


        userRepository.save(user);

        return AccountResponseDTO.builder()
                .username(userInfo.getLogin())
                .build();
    }

    protected boolean userExistsBy(String login){
        return userRepository.existsByLogin(login);
    }
}
