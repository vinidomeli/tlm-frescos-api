package com.mercadolibre.dambetan01.controller;

import com.mercadolibre.dambetan01.dtos.AuthDTO;
import com.mercadolibre.dambetan01.dtos.UserDTO;
import com.mercadolibre.dambetan01.dtos.response.AccountResponseDTO;
import com.mercadolibre.dambetan01.service.ISessionService;
import javassist.NotFoundException;
import org.springframework.web.bind.annotation.*;

@RequestMapping(path = "/api/v1")
@RestController
public class SessionController {
    private final ISessionService service;

    public SessionController(ISessionService sessionService) {
        this.service = sessionService;
    }

    /**
     * Realiza la validación del usuario y contraseña ingresado.
     * En caso de ser correcto, devuelve la cuenta con el token necesario para realizar las demás consultas.
     *
     * @return
     * @throws NotFoundException
     */
    @PostMapping("/sign-in")
    public AccountResponseDTO login(@RequestBody AuthDTO credentials) throws NotFoundException {
        return service.login(credentials);
    }

}
