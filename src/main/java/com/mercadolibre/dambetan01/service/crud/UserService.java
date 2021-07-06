package com.mercadolibre.dambetan01.service.crud;

import com.mercadolibre.dambetan01.dtos.AuthDTO;
import com.mercadolibre.dambetan01.dtos.UserDTO;

public interface UserService {

    public UserDTO create(UserDTO userCredentials);

}
