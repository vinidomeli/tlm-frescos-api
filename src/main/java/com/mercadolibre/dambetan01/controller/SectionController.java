package com.mercadolibre.dambetan01.controller;

import com.mercadolibre.dambetan01.dtos.FullSectionDTO;
import com.mercadolibre.dambetan01.service.crud.SectionService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/section")
public class SectionController {

    SectionService sectionService;

    public SectionController(SectionService sectionService) {
        this.sectionService = sectionService;
    }

    @GetMapping("/list")
    public ResponseEntity<List<FullSectionDTO>> findAllWarehouses() {
        List<FullSectionDTO> response = this.sectionService.findAllSections();

        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
