package com.mercadolibre.dambetan01.dtos.response;

import com.mercadolibre.dambetan01.dtos.ProductDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PurchaseOrderDetailDTO {
    private ProductDTO product;
}
