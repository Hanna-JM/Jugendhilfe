package com.jugendhilfe.jugendhilfe.repository;

import com.jugendhilfe.jugendhilfe.domain.FurtherEducation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface FurtherEducationRepository extends JpaRepository<FurtherEducation, Long> {

    FurtherEducation findByFurtherEducationId(Long furtherEducationId);

    @Query(value = "select * from further_education fe where fe.further_education_id like %:keyword% or fe.further_education_name like %:keyword% or fe.further_education_cycle like %:keyword% or fe.further_education_type like %:keyword% or fe.further_education_date_last_exam like %:keyword%", nativeQuery = true)
    List<FurtherEducation> findByKeyword(@Param("keyword") String keyword);
}
