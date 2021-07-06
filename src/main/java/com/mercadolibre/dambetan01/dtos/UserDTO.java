package com.mercadolibre.dambetan01.dtos;

import com.mercadolibre.dambetan01.model.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserDTO {
    private String name;
    private String role;
    private String login;
    private String password;

    public static UserDTO fromEntity(User user){
        return UserDTO.builder()
                .name(user.getName())
                .login(user.getLogin())
                .role(user.getRole())
                .build();
    }
}
