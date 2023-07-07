package com.jugendhilfe.jugendhilfe.repository;

import com.jugendhilfe.jugendhilfe.domain.YouthWelfareOffice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface YouthWelfareOfficeRepository extends JpaRepository<YouthWelfareOffice, Long> {

    YouthWelfareOffice findByYouthWelfareOfficeId(Long youthWelfareOfficeId);

    @Query(value = "select * from youth_welfare_office ywo where ywo.youth_welfare_office_id like %:keyword% or ywo.youth_welfare_office_name like %:keyword% or ywo.youth_welfare_office_city like %:keyword% or ywo.youth_welfare_office_state like %:keyword% or ywo.youth_welfare_office_city_code like %:keyword%",nativeQuery = true)
    List<YouthWelfareOffice> findByKeyword(@Param("keyword") String keyword);
}
