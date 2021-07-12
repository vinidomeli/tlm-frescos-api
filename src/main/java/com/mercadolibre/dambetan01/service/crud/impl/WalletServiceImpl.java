package com.mercadolibre.dambetan01.service.crud.impl;

import com.mercadolibre.dambetan01.dtos.WalletDTO;
import com.mercadolibre.dambetan01.exceptions.ApiException;
import com.mercadolibre.dambetan01.model.User;
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
        validateAmount(amount);

        Wallet wallet = this.walletRepository.findByUser_Id(userId)
                .orElseThrow(() -> new ApiException("404", "Wallet not found.", 404));

        BigInteger currentBalance = wallet.getBalance();
        BigInteger finalBalance = currentBalance.add(amount);

        wallet.setBalance(finalBalance);

        return WalletDTO.toDTO(this.walletRepository.save(wallet));
    }

    @Override
    public WalletDTO deduct(UUID userId, BigInteger amount) {
        validateAmount(amount);

        Wallet wallet = this.walletRepository.findByUser_Id(userId)
                .orElseThrow(() -> new ApiException("404", "Wallet not found.", 404));

        BigInteger currentBalance = wallet.getBalance();
        BigInteger finalBalance = currentBalance.subtract(amount);

        boolean balanceLowerThanZero = finalBalance.compareTo(BigInteger.ZERO) < 0;

        if (balanceLowerThanZero) {
            throw new ApiException("400", "Insufficient balance.", 400);
        }

        wallet.setBalance(finalBalance);

        return WalletDTO.toDTO(this.walletRepository.save(wallet));
    }

    @Override
    public WalletDTO findBy(UUID userID) {
        User user = User.builder()
                .id(userID)
                .build();

        Wallet newWallet = Wallet.builder()
                .user(user)
                .balance(BigInteger.ZERO)
                .build();

        Wallet wallet = this.walletRepository.findByUser_Id(userID)
                .orElseGet(() -> this.walletRepository.save(newWallet));

        return WalletDTO.toDTO(wallet);
    }

    @Override
    public BigInteger convertToCents(Double amount) {
        return BigInteger.valueOf(amount.longValue() * 100);
    }

    @Override
    public BigInteger convertFromCents(BigInteger amount) {
        return BigInteger.valueOf(amount.longValue() / 100);
    }

    protected void validateAmount(BigInteger amount) {
        boolean invalidAmount = amount.compareTo(BigInteger.ONE) < 0;

        if (invalidAmount) {
            throw new ApiException("400", "Invalid amount. Cannot add or deduct an  " + amount + " amount to your wallet.", 400);
        }
    }
}
