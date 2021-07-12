package com.mercadolibre.dambetan01.service.crud;

import com.mercadolibre.dambetan01.dtos.WalletDTO;

import java.math.BigInteger;
import java.util.UUID;

public interface WalletService {

    WalletDTO add(UUID userId, BigInteger amount);

    WalletDTO deduct(UUID userId, BigInteger amount);

    WalletDTO findBy(UUID userID);

}
