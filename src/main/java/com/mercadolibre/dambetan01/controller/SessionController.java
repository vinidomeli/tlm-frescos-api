package com.mercadolibre.dambetan01.controller;

import com.mercadolibre.dambetan01.dtos.AuthDTO;
import com.mercadolibre.dambetan01.dtos.SellerDTO;
import com.mercadolibre.dambetan01.dtos.UserDTO;
import com.mercadolibre.dambetan01.dtos.response.AccountResponseDTO;
import com.mercadolibre.dambetan01.service.ISessionService;
import com.mercadolibre.dambetan01.service.crud.UserService;
import javassist.NotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequestMapping(path = "/api/v1")
@RestController
public class SessionController {
    private final ISessionService service;

    private final UserService userService;

    public SessionController(ISessionService sessionService, UserService userService) {
        this.service = sessionService;
        this.userService = userService;
    }

    @PostMapping("/sign-in")
    public AccountResponseDTO login(@RequestBody AuthDTO credentials) throws NotFoundException {
        return service.login(credentials.getUsername(), credentials.getPassword());
    }

    @PostMapping("/register")
    public ResponseEntity<UserDTO> register(@RequestBody UserDTO userDTO) {
        UserDTO response = userService.create(userDTO);

        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @PostMapping("/register/seller")
    public ResponseEntity<SellerDTO> registerSeller(@RequestBody SellerDTO sellerDTO) {
        SellerDTO response = userService.createSeller(sellerDTO);

        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

}
