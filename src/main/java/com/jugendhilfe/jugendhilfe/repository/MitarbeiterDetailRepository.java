package com.jugendhilfe.jugendhilfe.repository;

import com.jugendhilfe.jugendhilfe.domain.Mitarbeiter;
import com.jugendhilfe.jugendhilfe.domain.MitarbeiterDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface MitarbeiterDetailRepository extends JpaRepository<MitarbeiterDetail, Long> {
    MitarbeiterDetail findByMitarbeiterDetailId(Long mitarbeiterDetailId);

    List<MitarbeiterDetail> findByAktivTrue();
    boolean existsByMitarbeiterDetailMitarbeiterAndAktivTrue(Mitarbeiter mitarbeiter);

    @Query(value = "select * from mitarbeiter_detail md where md.mitarbeiter_detail_id like %:keyword% or md.mitarbeiter_detail_einsatz_beginn like %:keyword% or md.mitarbeiter_detail_einsatz_ende like %:keyword% ", nativeQuery = true)
    List<MitarbeiterDetail> findByKeyword(@Param("keyword") String keyword);
}
