package com.mercadolibre.dambetan01.service.crud.impl;

import com.mercadolibre.dambetan01.model.User;
import com.mercadolibre.dambetan01.repository.UserRepository;
import com.mercadolibre.dambetan01.service.crud.UserService;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public User registerNewUser(User user) {

        boolean userDoesntExists = !userRepository.existsByLogin(user.getLogin());

        if(userDoesntExists) {
            userRepository.save(user);
        }
        return userRepository.findByLogin(user.getLogin()).get();
    }
}
