package com.mercadolibre.dambetan01.service.crud;

import com.mercadolibre.dambetan01.dtos.SellerDTO;
import com.mercadolibre.dambetan01.dtos.UserDTO;
import com.mercadolibre.dambetan01.model.User;

public interface UserService {

    public UserDTO create(UserDTO userCredentials);

    User findByLogin(String login);

    public SellerDTO createSeller(SellerDTO sellerDTO);

}
