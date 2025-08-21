package com.kt.seoul.commercialdistrict.controller;

import com.kt.seoul.commercialdistrict.dto.DistrictPopulationResponse;
import com.kt.seoul.commercialdistrict.service.DistrictPopulationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 서울시 자치구 인구 통계 REST API 컨트롤러
 * 
 * @author KT 개발팀
 * @version 1.0.0
 */
@Slf4j
@RestController
@RequestMapping("/districts")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class DistrictPopulationController {
    
    private final DistrictPopulationService districtPopulationService;
    
    /**
     * 모든 자치구 인구 통계 조회 (총 인구수 기준 내림차순)
     * 
     * @return 모든 자치구 인구 통계 목록
     */
    @GetMapping
    public ResponseEntity<List<DistrictPopulationResponse>> getAllDistricts() {
        log.info("GET /districts - 모든 자치구 인구 통계 조회 요청");
        
        try {
            List<DistrictPopulationResponse> districts = districtPopulationService.getAllDistricts();
            
            log.info("GET /districts - 성공적으로 {}개 자치구 데이터 응답", districts.size());
            return ResponseEntity.ok(districts);
            
        } catch (Exception e) {
            log.error("GET /districts - 자치구 데이터 조회 중 오류 발생", e);
            return ResponseEntity.internalServerError().build();
        }
    }
    
    /**
     * 특정 자치구 인구 통계 조회
     * 
     * @param districtName 자치구명
     * @return 해당 자치구 인구 통계
     */
    @GetMapping("/{districtName}")
    public ResponseEntity<DistrictPopulationResponse> getDistrictByName(@PathVariable String districtName) {
        log.info("GET /districts/{} - 특정 자치구 인구 통계 조회 요청", districtName);
        
        try {
            DistrictPopulationResponse district = districtPopulationService.getDistrictByName(districtName);
            
            log.info("GET /districts/{} - 성공적으로 자치구 데이터 응답", districtName);
            return ResponseEntity.ok(district);
            
        } catch (IllegalArgumentException e) {
            log.warn("GET /districts/{} - 자치구를 찾을 수 없음: {}", districtName, e.getMessage());
            return ResponseEntity.notFound().build();
            
        } catch (Exception e) {
            log.error("GET /districts/{} - 자치구 데이터 조회 중 오류 발생", districtName, e);
            return ResponseEntity.internalServerError().build();
        }
    }
    
    /**
     * 상위 N개 인구 많은 자치구 조회
     * 
     * @param limit 조회할 자치구 수 (기본값: 5)
     * @return 상위 N개 자치구 목록
     */
    @GetMapping("/top")
    public ResponseEntity<List<DistrictPopulationResponse>> getTopDistrictsByPopulation(
            @RequestParam(defaultValue = "5") Integer limit) {
        
        log.info("GET /districts/top?limit={} - 상위 인구 많은 자치구 조회 요청", limit);
        
        try {
            List<DistrictPopulationResponse> districts = districtPopulationService.getTopDistrictsByPopulation(limit);
            
            log.info("GET /districts/top - 성공적으로 상위 {}개 자치구 데이터 응답", districts.size());
            return ResponseEntity.ok(districts);
            
        } catch (IllegalArgumentException e) {
            log.warn("GET /districts/top - 잘못된 파라미터: {}", e.getMessage());
            return ResponseEntity.badRequest().build();
            
        } catch (Exception e) {
            log.error("GET /districts/top - 상위 자치구 데이터 조회 중 오류 발생", e);
            return ResponseEntity.internalServerError().build();
        }
    }
    
    /**
     * 특정 인구수 이상인 자치구들 조회
     * 
     * @param minPopulation 최소 인구수
     * @return 조건에 맞는 자치구 목록
     */
    @GetMapping("/filter")
    public ResponseEntity<List<DistrictPopulationResponse>> getDistrictsWithMinimumPopulation(
            @RequestParam Integer minPopulation) {
        
        log.info("GET /districts/filter?minPopulation={} - 최소 인구수 조건 자치구 조회 요청", minPopulation);
        
        try {
            List<DistrictPopulationResponse> districts = 
                districtPopulationService.getDistrictsWithMinimumPopulation(minPopulation);
            
            log.info("GET /districts/filter - 성공적으로 {}개 자치구 데이터 응답", districts.size());
            return ResponseEntity.ok(districts);
            
        } catch (IllegalArgumentException e) {
            log.warn("GET /districts/filter - 잘못된 파라미터: {}", e.getMessage());
            return ResponseEntity.badRequest().build();
            
        } catch (Exception e) {
            log.error("GET /districts/filter - 조건별 자치구 데이터 조회 중 오류 발생", e);
            return ResponseEntity.internalServerError().build();
        }
    }
    
    /**
     * 자치구명 검색
     * 
     * @param keyword 검색 키워드
     * @return 조건에 맞는 자치구 목록
     */
    @GetMapping("/search")
    public ResponseEntity<List<DistrictPopulationResponse>> searchDistrictsByName(
            @RequestParam String keyword) {
        
        log.info("GET /districts/search?keyword={} - 자치구명 검색 요청", keyword);
        
        try {
            List<DistrictPopulationResponse> districts = districtPopulationService.searchDistrictsByName(keyword);
            
            log.info("GET /districts/search - 성공적으로 {}개 자치구 검색 결과 응답", districts.size());
            return ResponseEntity.ok(districts);
            
        } catch (IllegalArgumentException e) {
            log.warn("GET /districts/search - 잘못된 검색 키워드: {}", e.getMessage());
            return ResponseEntity.badRequest().build();
            
        } catch (Exception e) {
            log.error("GET /districts/search - 자치구 검색 중 오류 발생", e);
            return ResponseEntity.internalServerError().build();
        }
    }
    
    /**
     * 서울시 전체 통계 정보 조회
     * 
     * @return 서울시 전체 통계 정보
     */
    @GetMapping("/statistics/summary")
    public ResponseEntity<DistrictPopulationService.SeoulStatisticsSummary> getSeoulStatisticsSummary() {
        log.info("GET /districts/statistics/summary - 서울시 전체 통계 정보 조회 요청");
        
        try {
            DistrictPopulationService.SeoulStatisticsSummary summary = 
                districtPopulationService.getSeoulStatisticsSummary();
            
            log.info("GET /districts/statistics/summary - 성공적으로 서울시 통계 정보 응답");
            return ResponseEntity.ok(summary);
            
        } catch (Exception e) {
            log.error("GET /districts/statistics/summary - 서울시 통계 정보 조회 중 오류 발생", e);
            return ResponseEntity.internalServerError().build();
        }
    }
    
    /**
     * API 상태 확인 (Health Check)
     * 
     * @return API 상태 메시지
     */
    @GetMapping("/health")
    public ResponseEntity<String> healthCheck() {
        log.info("GET /districts/health - Health Check 요청");
        return ResponseEntity.ok("서울시 상권 분석 API가 정상적으로 동작 중입니다.");
    }
}
