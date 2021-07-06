package com.mercadolibre.dambetan01.dtos.response;
import lombok.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AccountResponseDTO {
    private String username;
    private String password;
    private String token;
}