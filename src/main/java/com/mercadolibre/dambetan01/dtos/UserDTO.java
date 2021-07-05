package com.mercadolibre.dambetan01.dtos;

import com.mercadolibre.dambetan01.model.enums.RoleType;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
public class UserDTO {
    @NotBlank(message = "Name cannot be null")
    private String name;

    @NotBlank(message = "Login cannot be null")
    private String login;

    @NotBlank(message = "Password cannot be null")
    private String password;

    @NotNull(message = "Role cannot be null")
    private RoleType role;
}
