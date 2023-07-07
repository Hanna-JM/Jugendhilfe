package com.jugendhilfe.jugendhilfe.repository;

import com.jugendhilfe.jugendhilfe.domain.Mitarbeiter;
import com.jugendhilfe.jugendhilfe.domain.MitarbeiterDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MitarbeiterRepository extends JpaRepository<Mitarbeiter,Long> {

    Mitarbeiter findByMitarbeiterId(Long mitarbeiterId);

    List<Mitarbeiter> findByMitarbeiterAktivFirmaTrue();

    @Query(value = "select * from mitarbeiter m where m.mitarbeiter_id like %:keyword% or m.vorname like %:keyword% or m.nachname like %:keyword% or m.ort like %:keyword% or m.strasse like %:keyword% or m.mitarbeiter_languages like %:keyword% or m.mitarbeiter_job like %:keyword% " ,nativeQuery = true)
    List<Mitarbeiter> findByKeyword(@Param("keyword") String keyword);

}
