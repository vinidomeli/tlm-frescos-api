package com.mercadolibre.dambetan01.dtos.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.mercadolibre.dambetan01.model.Supervisor;
import lombok.*;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class WarehouseRequestDTO {

    @Valid
    @JsonProperty("location")
    @NotNull(message = "Location is required.")
    private String location;

    @Valid
    @JsonProperty("login")
    @NotNull(message = "Supervisor login is required.")
    private String login;
}
