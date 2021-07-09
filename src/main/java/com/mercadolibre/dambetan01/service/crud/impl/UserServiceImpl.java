package com.mercadolibre.dambetan01.service.crud.impl;

import com.mercadolibre.dambetan01.dtos.UserDTO;
import com.mercadolibre.dambetan01.exceptions.ApiException;
import com.mercadolibre.dambetan01.model.Supervisor;
import com.mercadolibre.dambetan01.model.User;
import com.mercadolibre.dambetan01.model.enums.Roles;
import com.mercadolibre.dambetan01.repository.SupervisorRepository;
import com.mercadolibre.dambetan01.repository.UserRepository;
import com.mercadolibre.dambetan01.service.crud.UserService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    UserRepository userRepository;
    SupervisorRepository supervisorRepository;

    public UserServiceImpl(UserRepository userRepository, SupervisorRepository supervisorRepository) {
        this.userRepository = userRepository;
        this.supervisorRepository = supervisorRepository;
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
            Supervisor supervisor = Supervisor.builder()
                    .user(userSaved)
                    .build();
            supervisorRepository.save(supervisor);
        }

        return UserDTO.fromEntity(userSaved);
    }

    @Override
    public User findByLogin(String login) {
        return userRepository.findByLogin(login)
                .orElseThrow(() -> new ApiException("404", "User not found", 404));
    }

    protected boolean existsBy(String login) {
        return userRepository.findByLogin(login).isPresent();
    }
}
