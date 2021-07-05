package com.mercadolibre.dambetan01.dtos.response;
import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AccountResponseDTO {
    private String username;
    private String password;
    private String token;
}