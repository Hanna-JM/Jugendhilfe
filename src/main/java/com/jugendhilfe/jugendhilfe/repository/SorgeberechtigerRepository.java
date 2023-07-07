package com.jugendhilfe.jugendhilfe.repository;

import com.jugendhilfe.jugendhilfe.domain.Kind;
import com.jugendhilfe.jugendhilfe.domain.Sorgeberechtiger;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface SorgeberechtigerRepository extends JpaRepository<Sorgeberechtiger,Long> {

    Sorgeberechtiger findBySorgeberechtigerId(Long sorgeberechtigerId);


    @Query(value = "select * from sorgeberechtiger s where s.sorgeberechtiger_vorname like %:keyword% or " +
            "s.sorgeberechtiger_nachname like %:keyword%  or " +
            "s.geburtsdatum like %:keyword%  or " +
            "s.email like %:keyword% or " +
            "s.haus_nummer like %:keyword% or " +
            "s.strasse like %:keyword% or " +
            "s.plz like %:keyword% or " +
            "s.ort like %:keyword% or " +
            "s.telefon like %:keyword% or " +
            "s.vewandtschaft like %:keyword% or " +
            "s.besuchsrecht like %:keyword% or " +
            "s.anrede like %:keyword% ",nativeQuery=true )
    List<Sorgeberechtiger> findByKeyword(@Param("keyword")String keyword);



}
