package com.mercadolibre.dambetan01.controller;

import com.mercadolibre.dambetan01.dtos.WalletDTO;
import com.mercadolibre.dambetan01.service.crud.UserService;
import com.mercadolibre.dambetan01.service.crud.WalletService;
import com.mercadolibre.dambetan01.service.impl.SessionServiceImpl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigInteger;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/wallet")
public class WalletController {

    WalletService walletService;

    UserService userService;

    public WalletController(WalletService walletService, UserService userService) {
        this.walletService = walletService;
        this.userService = userService;
    }

    @PutMapping("/balance/add")
    public ResponseEntity<WalletDTO> addBalance(@RequestHeader String token, @RequestParam BigInteger amount) {
        String username = SessionServiceImpl.getUsername(token);
        UUID userId = this.userService.findByLogin(username).getId();

        WalletDTO response = this.walletService.add(userId, amount);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PutMapping("/balance/deduct")
    public ResponseEntity<WalletDTO> deductBalance(@RequestHeader String token, @RequestParam BigInteger amount) {
        String username = SessionServiceImpl.getUsername(token);
        UUID userId = this.userService.findByLogin(username).getId();

        WalletDTO response = this.walletService.deduct(userId, amount);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<WalletDTO> getBalance(@RequestHeader String token) {
        String username = SessionServiceImpl.getUsername(token);
        UUID userId = this.userService.findByLogin(username).getId();

        WalletDTO response = this.walletService.findBy(userId);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
