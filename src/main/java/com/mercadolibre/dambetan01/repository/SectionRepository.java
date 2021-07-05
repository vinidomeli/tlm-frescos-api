package com.mercadolibre.dambetan01.repository;

import com.mercadolibre.dambetan01.model.Section;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface SectionRepository extends JpaRepository<Section, UUID> {

//    @Query(value = "SELECT CASE WHEN COUNT(s.sectionCode) > 0 THEN TRUE ELSE FALSE END FROM Section s WHERE s.sectionCode = ?1", nativeQuery = true)
    public boolean existsBySectionCode(UUID sectionCode);
    public Section findBySectionCode(UUID sectionCode);
}
