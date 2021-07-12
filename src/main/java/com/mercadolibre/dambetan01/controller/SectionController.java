package com.mercadolibre.dambetan01.controller;

import com.mercadolibre.dambetan01.dtos.FullSectionDTO;
import com.mercadolibre.dambetan01.service.crud.SectionService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/section")
@Tag(name = "Section Operations")
public class SectionController {

    final SectionService sectionService;

    public SectionController(SectionService sectionService) {
        this.sectionService = sectionService;
    }

    @Operation(summary = "Get all sections", description = "Get all sections")
    @GetMapping("/list")
    public ResponseEntity<List<FullSectionDTO>> findAllSections() {
        List<FullSectionDTO> response = this.sectionService.findAllSections();

        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
