package com.jugendhilfe.jugendhilfe.repository;

import com.jugendhilfe.jugendhilfe.domain.Kind;
import com.jugendhilfe.jugendhilfe.domain.Sorgeberechtiger;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface KindRepository extends JpaRepository<Kind,Long> {
    Kind findByKindId(Long kindId);
    @Query(value = "select * from kind k where k.kindvorname like %:keyword% or k.kindnachname like %:keyword% or " +
            "k.geschlecht like %:keyword% or k.einzug_datum like %:keyword% or" +
            " k.auszug_datum like %:keyword%  or " +
            "k.youth_welfare_office_id like %:keyword% or " +
            "k.allergie like %:keyword% or " +
            "k.ernaehrung like %:keyword% or " +
            "k.behinderung like %:keyword% or " +
            "k.religion  like %:keyword% or " +
            "k.geburtsdatum like %:keyword% or " +
            "k.betreuungsgrund like %:keyword% ",nativeQuery=true )
    List<Kind> findByKeyword(@Param("keyword")String keyword);


}
