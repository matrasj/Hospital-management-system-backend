package com.example.hospitalmanagementsystembackend.repository;

import com.example.hospitalmanagementsystembackend.model.entity.Drug;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DrugRepository extends JpaRepository<Drug, Long> {
     @Query(nativeQuery = true, value = "SELECT DISTINCT company_brand AS companyBrand, COUNT(*) AS dragsQuantity FROM drug\n" +
             "             GROUP BY drug.company_brand ORDER BY COUNT(*) DESC LIMIT :limit")
     List<Object[]> findBrandsWithDrugsQuantity(@Param("limit") Long limit);
     Page<Drug> findByNameContaining(@Param("keyword") String keyword, Pageable pageable);
}
