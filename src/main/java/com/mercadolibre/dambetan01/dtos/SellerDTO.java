package com.mercadolibre.dambetan01.dtos;

import com.mercadolibre.dambetan01.model.Seller;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SellerDTO {

    private String name;
    private String login;
    private String password;
    private String cnpj;

    public static SellerDTO fromEntity(Seller seller) {
        return SellerDTO.builder()
                .name(seller.getUser().getName())
                .login(seller.getUser().getLogin())
                .cnpj(seller.getCnpj())
                .build();
    }

}
