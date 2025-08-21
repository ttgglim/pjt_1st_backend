package com.kt.seoul.commercialdistrict.repository;

import com.kt.seoul.commercialdistrict.entity.DistrictPopulationStatistics;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * 자치구 인구 통계 Repository
 * 
 * @author KT 개발팀
 * @version 1.0.0
 */
@Repository
public interface DistrictPopulationStatisticsRepository extends JpaRepository<DistrictPopulationStatistics, Long> {
    
    /**
     * 자치구명으로 인구 통계 조회
     * 
     * @param districtName 자치구명
     * @return 해당 자치구의 인구 통계
     */
    Optional<DistrictPopulationStatistics> findByDistrictName(String districtName);
    
    /**
     * 모든 자치구의 인구 통계를 총 인구수 기준 내림차순으로 조회
     * 
     * @return 총 인구수 기준 내림차순 정렬된 인구 통계 목록
     */
    List<DistrictPopulationStatistics> findAllByOrderByTotalPopulationDesc();
    
    /**
     * 특정 인구수 이상인 자치구들 조회
     * 
     * @param minPopulation 최소 인구수
     * @return 조건에 맞는 자치구 목록
     */
    @Query("SELECT d FROM DistrictPopulationStatistics d WHERE d.totalPopulation >= :minPopulation ORDER BY d.totalPopulation DESC")
    List<DistrictPopulationStatistics> findDistrictsWithMinimumPopulation(@Param("minPopulation") Integer minPopulation);
    
    /**
     * 상위 N개 인구 많은 자치구 조회
     * 
     * @param limit 조회할 자치구 수
     * @return 상위 N개 자치구 목록
     */
    @Query(value = "SELECT * FROM district_population_statistics ORDER BY total_population DESC LIMIT :limit", nativeQuery = true)
    List<DistrictPopulationStatistics> findTopDistrictsByPopulation(@Param("limit") Integer limit);
    
    /**
     * 자치구명에 특정 문자열이 포함된 자치구들 조회
     * 
     * @param keyword 검색 키워드
     * @return 조건에 맞는 자치구 목록
     */
    List<DistrictPopulationStatistics> findByDistrictNameContaining(String keyword);
    
    /**
     * 전체 자치구 수 조회
     * 
     * @return 전체 자치구 수
     */
    @Query("SELECT COUNT(d) FROM DistrictPopulationStatistics d")
    Long countAllDistricts();
    
    /**
     * 전체 서울시 총 인구수 합계
     * 
     * @return 서울시 전체 인구수
     */
    @Query("SELECT SUM(d.totalPopulation) FROM DistrictPopulationStatistics d")
    Long getTotalSeoulPopulation();
}
