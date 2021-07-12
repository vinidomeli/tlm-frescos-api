package com.mercadolibre.dambetan01.dtos;

import com.mercadolibre.dambetan01.model.Wallet;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigInteger;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class WalletDTO {

    UUID walletId;

    UUID userId;

    BigInteger balance;

    public static WalletDTO toDTO(Wallet wallet) {
        return WalletDTO.builder()
                .walletId(wallet.getId())
                .userId(wallet.getUser().getId())
                .balance(wallet.getBalance())
                .build();
    }
}
