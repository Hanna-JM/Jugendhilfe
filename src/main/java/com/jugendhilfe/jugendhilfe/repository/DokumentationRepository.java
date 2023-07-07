package com.jugendhilfe.jugendhilfe.repository;

import com.jugendhilfe.jugendhilfe.domain.Dokumentation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface DokumentationRepository extends JpaRepository<Dokumentation, Long> {
    Dokumentation findByDokumentationId(Long dokumentationId);

@Query(value = "select * from dokumentation d where " +
        "d.dokumentationdatum like %:keyword% or " +
        "d.kind_id like %:keyword% or " +
        "d.dokumentationtyp like %:keyword% or " +
        "d.dokumentationtext like %:keyword%", nativeQuery = true)
List<Dokumentation> findByKeyword(@Param("keyword") String keyword);

}
