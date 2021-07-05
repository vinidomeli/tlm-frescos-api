package com.mercadolibre.dambetan01.service;

import com.mercadolibre.dambetan01.dtos.UserDTO;
import com.mercadolibre.dambetan01.dtos.response.AccountResponseDTO;
import com.mercadolibre.dambetan01.model.Account;

public interface UserService {

    public AccountResponseDTO register(UserDTO userInfo);

}
