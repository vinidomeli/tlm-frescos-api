package com.mercadolibre.dambetan01.dtos.response;

import com.mercadolibre.dambetan01.dtos.BatchStockDTO;
import com.mercadolibre.dambetan01.dtos.SectionDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ProductBatchesResponseDTO {


    private SectionDTO sectionDTO;
    private Long productId;
    private List<BatchStockDTO> batchStock;

}
