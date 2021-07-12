package com.mercadolibre.dambetan01.service.impl;

import com.mercadolibre.dambetan01.dtos.WalletDTO;
import com.mercadolibre.dambetan01.exceptions.ApiException;
import com.mercadolibre.dambetan01.model.Wallet;
import com.mercadolibre.dambetan01.repository.WalletRepository;
import com.mercadolibre.dambetan01.service.crud.WalletService;
import org.springframework.stereotype.Service;

import java.math.BigInteger;
import java.util.UUID;

@Service
public class WalletServiceImpl implements WalletService {

    WalletRepository walletRepository;

    public WalletServiceImpl(WalletRepository walletRepository) {
        this.walletRepository = walletRepository;
    }

    @Override
    public WalletDTO add(UUID userId, BigInteger amount) {
        boolean invalidAmount = amount.compareTo(BigInteger.ONE) < 0;

        if (invalidAmount) {
            throw new RuntimeException("Invalid amount. Cannot add " + amount + " amount to your wallet.");
        }


        Wallet wallet = this.walletRepository.findByUserId(userId)
                .orElseThrow(() -> new ApiException("404", "Wallet not found.", 404));

        BigInteger currentBalance = wallet.getBalance();
        BigInteger finalBalance = currentBalance.add(amount);

        wallet.setBalance(finalBalance);

        return WalletDTO.toDTO(this.walletRepository.save(wallet));
    }

    @Override
    public WalletDTO deduct(UUID userId, BigInteger amount) {
        Wallet wallet = this.walletRepository.findByUserId(userId)
                .orElseThrow(() -> new ApiException("404", "Wallet not found.", 404));

        BigInteger currentBalance = wallet.getBalance();
        BigInteger finalBalance = currentBalance.subtract(amount);

        boolean balanceLowerThanZero = finalBalance.compareTo(BigInteger.ZERO) < 0;

        if (balanceLowerThanZero) {
            throw new RuntimeException("Insufficient balance.");
        }

        wallet.setBalance(finalBalance);

        return WalletDTO.toDTO(this.walletRepository.save(wallet));
    }

    @Override
    public WalletDTO findBy(UUID userID) {
        Wallet wallet = this.walletRepository.findByUserId(userID)
                .orElseThrow(() -> new ApiException("404", "Wallet not found.", 404));

        return WalletDTO.toDTO(wallet);
    }
}
