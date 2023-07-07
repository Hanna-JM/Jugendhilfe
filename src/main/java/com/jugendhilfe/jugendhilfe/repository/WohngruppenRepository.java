package com.jugendhilfe.jugendhilfe.repository;

import com.jugendhilfe.jugendhilfe.domain.Mitarbeiter;
import com.jugendhilfe.jugendhilfe.domain.Wohngruppe;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface WohngruppenRepository extends JpaRepository<Wohngruppe,Long> {
    Wohngruppe findByWohngruppenId(Long wohngruppenId);

    @Query(value = "select * from wohngruppe p " +
            "where p.wohngruppenname like %:keyword% or " +
            "p.ort like %:keyword% or " +
            "p.strasse like %:keyword% or " +
            "p.anzahl_plaetze like %:keyword% or " +
            "p.tagessatz like %:keyword% ",nativeQuery = true)
//    List<Wohngruppe> findByKeyword(@Param("keyword") String keyword);


    Page<Wohngruppe> findByKeyword(String keyword, Pageable pageable);

}
