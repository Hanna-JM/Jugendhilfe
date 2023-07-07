package com.jugendhilfe.jugendhilfe.repository;

import com.jugendhilfe.jugendhilfe.domain.Kind;
import com.jugendhilfe.jugendhilfe.domain.KindDetail;
import com.jugendhilfe.jugendhilfe.domain.MitarbeiterDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface KindDetailRepository extends JpaRepository<KindDetail, Long> {
    KindDetail findByKindDetailId(Long kindDetailId);
    List<KindDetail> findByAktivTrue();
    @Query(value = "select * from kind_detail kd where kd.kind_detail_id like %:keyword% or kd.kind_detail_einzug like %:keyword% or kd.kind_detail_auszug like %:keyword% ", nativeQuery = true)
    List<KindDetail> findByKeyword(@Param("keyword") String keyword);

}
