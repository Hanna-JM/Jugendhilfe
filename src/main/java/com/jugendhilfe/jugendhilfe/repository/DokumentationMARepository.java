package com.jugendhilfe.jugendhilfe.repository;

import com.jugendhilfe.jugendhilfe.domain.Dokumentation;
import com.jugendhilfe.jugendhilfe.domain.DokumentationMA;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface DokumentationMARepository extends JpaRepository<DokumentationMA, Long> {
    DokumentationMA findByDokumentationId(Long dokumentationId);

@Query(value = "select * from dokumentation_ma d where d.dokumentationdatum like %:keyword% or +" +
//        "d.mitarbeiter_id like %:keyword% or +" +
        "d.dokumentationtyp like %:keyword% or +" +
        "d.dokumentationtext like %:keyword%", nativeQuery = true)
List<DokumentationMA> findByKeyword(@Param("keyword") String keyword);

}
