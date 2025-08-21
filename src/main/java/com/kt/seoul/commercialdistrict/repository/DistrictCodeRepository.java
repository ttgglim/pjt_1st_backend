package com.kt.seoul.commercialdistrict.repository;

import com.kt.seoul.commercialdistrict.entity.DistrictCode;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * 자치구 코드 Repository
 * 
 * @author KT 개발팀
 * @version 1.0.0
 */
@Repository
public interface DistrictCodeRepository extends JpaRepository<DistrictCode, Long> {
    
    /**
     * 자치구 코드로 자치구 정보 조회
     * 
     * @param districtCode 자치구 코드
     * @return 해당 코드의 자치구 정보
     */
    Optional<DistrictCode> findByDistrictCode(String districtCode);
    
    /**
     * 자치구명으로 자치구 정보 조회
     * 
     * @param districtName 자치구명
     * @return 해당 이름의 자치구 정보
     */
    Optional<DistrictCode> findByDistrictName(String districtName);
    
    /**
     * 모든 자치구 코드 정보를 코드 순으로 조회
     * 
     * @return 코드 순으로 정렬된 자치구 목록
     */
    List<DistrictCode> findAllByOrderByDistrictCodeAsc();
    
    /**
     * 모든 자치구 코드 정보를 이름 순으로 조회
     * 
     * @return 이름 순으로 정렬된 자치구 목록
     */
    List<DistrictCode> findAllByOrderByDistrictNameAsc();
    
    /**
     * 자치구명에 특정 문자열이 포함된 자치구들 조회
     * 
     * @param keyword 검색 키워드
     * @return 조건에 맞는 자치구 목록
     */
    List<DistrictCode> findByDistrictNameContaining(String keyword);
    
    /**
     * 전체 자치구 수 조회
     * 
     * @return 전체 자치구 수
     */
    @Query("SELECT COUNT(d) FROM DistrictCode d")
    Long countAllDistricts();
}
