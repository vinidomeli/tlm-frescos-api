package com.mercadolibre.dambetan01.repository;

import com.mercadolibre.dambetan01.model.Section;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface SectionRepository extends JpaRepository<Section, UUID> {

    public boolean existsBySectionCode(UUID sectionCode);
    public Section findBySectionCode(UUID sectionCode);
}
