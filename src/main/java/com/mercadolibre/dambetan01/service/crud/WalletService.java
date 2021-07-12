package com.mercadolibre.dambetan01.service.crud;

import com.mercadolibre.dambetan01.dtos.WalletDTO;

import java.math.BigInteger;
import java.util.UUID;

public interface WalletService<T> {

    WalletDTO add(UUID userId, BigInteger amount);

    WalletDTO deduct(UUID userId, BigInteger amount);

    WalletDTO findBy(UUID userID);

    BigInteger convertToCents(Double amount);

    BigInteger convertFromCents(BigInteger amount);

}
