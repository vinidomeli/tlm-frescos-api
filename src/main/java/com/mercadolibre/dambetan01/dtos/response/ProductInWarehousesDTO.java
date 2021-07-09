package com.mercadolibre.dambetan01.dtos.response;

import com.mercadolibre.dambetan01.dtos.WarehouseProductQuantityDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProductInWarehousesDTO {

    private Long productId;

    private List<WarehouseProductQuantityDTO> warehouses;

}
