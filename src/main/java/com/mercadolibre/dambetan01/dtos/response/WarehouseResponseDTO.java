package com.mercadolibre.dambetan01.dtos.response;

import com.mercadolibre.dambetan01.dtos.request.SectionFromWarehouseRequestDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class WarehouseResponseDTO {

    private UUID warehouseCode;
    private String location;
    private Long supervisorRegisterNumber;
    private UUID userId;
    private String supervisorName;
    private String supervisorLogin;
    private List<SectionFromWarehouseRequestDTO> sections;

}
