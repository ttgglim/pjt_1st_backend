package com.kt.seoul.commercialdistrict.service;

import com.kt.seoul.commercialdistrict.dto.DistrictPopulationResponse;
import com.kt.seoul.commercialdistrict.entity.DistrictPopulationStatistics;
import com.kt.seoul.commercialdistrict.repository.DistrictPopulationStatisticsRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

/**
 * 자치구 인구 통계 서비스
 * 
 * @author KT 개발팀
 * @version 1.0.0
 */
@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class DistrictPopulationService {
    
    private final DistrictPopulationStatisticsRepository repository;
    
    /**
     * 모든 자치구 인구 통계 조회 (총 인구수 기준 내림차순)
     * 
     * @return 모든 자치구 인구 통계 목록
     */
    public List<DistrictPopulationResponse> getAllDistricts() {
        log.info("모든 자치구 인구 통계 조회 요청");
        
        List<DistrictPopulationStatistics> districts = repository.findAllByOrderByTotalPopulationDesc();
        
        log.info("총 {}개 자치구 데이터 조회 완료", districts.size());
        
        return districts.stream()
                .map(DistrictPopulationResponse::from)
                .collect(Collectors.toList());
    }
    
    /**
     * 특정 자치구 인구 통계 조회
     * 
     * @param districtName 자치구명
     * @return 해당 자치구 인구 통계
     */
    public DistrictPopulationResponse getDistrictByName(String districtName) {
        log.info("자치구 '{}' 인구 통계 조회 요청", districtName);
        
        DistrictPopulationStatistics district = repository.findByDistrictName(districtName)
                .orElseThrow(() -> new IllegalArgumentException("해당 자치구를 찾을 수 없습니다: " + districtName));
        
        log.info("자치구 '{}' 데이터 조회 완료", districtName);
        
        return DistrictPopulationResponse.from(district);
    }
    
    /**
     * 상위 N개 인구 많은 자치구 조회
     * 
     * @param limit 조회할 자치구 수
     * @return 상위 N개 자치구 목록
     */
    public List<DistrictPopulationResponse> getTopDistrictsByPopulation(Integer limit) {
        log.info("상위 {}개 인구 많은 자치구 조회 요청", limit);
        
        if (limit <= 0 || limit > 25) {
            throw new IllegalArgumentException("조회 개수는 1~25 사이여야 합니다.");
        }
        
        List<DistrictPopulationStatistics> districts = repository.findTopDistrictsByPopulation(limit);
        
        log.info("상위 {}개 자치구 데이터 조회 완료", districts.size());
        
        return districts.stream()
                .map(DistrictPopulationResponse::from)
                .collect(Collectors.toList());
    }
    
    /**
     * 특정 인구수 이상인 자치구들 조회
     * 
     * @param minPopulation 최소 인구수
     * @return 조건에 맞는 자치구 목록
     */
    public List<DistrictPopulationResponse> getDistrictsWithMinimumPopulation(Integer minPopulation) {
        log.info("최소 인구수 {} 이상인 자치구 조회 요청", minPopulation);
        
        if (minPopulation < 0) {
            throw new IllegalArgumentException("최소 인구수는 0 이상이어야 합니다.");
        }
        
        List<DistrictPopulationStatistics> districts = repository.findDistrictsWithMinimumPopulation(minPopulation);
        
        log.info("조건에 맞는 {}개 자치구 데이터 조회 완료", districts.size());
        
        return districts.stream()
                .map(DistrictPopulationResponse::from)
                .collect(Collectors.toList());
    }
    
    /**
     * 자치구명 검색
     * 
     * @param keyword 검색 키워드
     * @return 조건에 맞는 자치구 목록
     */
    public List<DistrictPopulationResponse> searchDistrictsByName(String keyword) {
        log.info("키워드 '{}'로 자치구 검색 요청", keyword);
        
        if (keyword == null || keyword.trim().isEmpty()) {
            throw new IllegalArgumentException("검색 키워드는 비어있을 수 없습니다.");
        }
        
        List<DistrictPopulationStatistics> districts = repository.findByDistrictNameContaining(keyword.trim());
        
        log.info("키워드 '{}'로 {}개 자치구 검색 완료", keyword, districts.size());
        
        return districts.stream()
                .map(DistrictPopulationResponse::from)
                .collect(Collectors.toList());
    }
    
    /**
     * 서울시 전체 통계 정보 조회
     * 
     * @return 서울시 전체 통계 정보
     */
    public SeoulStatisticsSummary getSeoulStatisticsSummary() {
        log.info("서울시 전체 통계 정보 조회 요청");
        
        Long totalDistricts = repository.countAllDistricts();
        Long totalPopulation = repository.getTotalSeoulPopulation();
        
        log.info("서울시 통계 - 총 자치구: {}개, 총 인구: {}명", totalDistricts, totalPopulation);
        
        return SeoulStatisticsSummary.builder()
                .totalDistricts(totalDistricts)
                .totalPopulation(totalPopulation)
                .averagePopulationPerDistrict(totalDistricts > 0 ? totalPopulation / totalDistricts : 0)
                .build();
    }
    
    /**
     * 서울시 전체 통계 요약 정보 DTO
     */
    @lombok.Data
    @lombok.Builder
    @lombok.NoArgsConstructor
    @lombok.AllArgsConstructor
    public static class SeoulStatisticsSummary {
        private Long totalDistricts;
        private Long totalPopulation;
        private Long averagePopulationPerDistrict;
    }
}
