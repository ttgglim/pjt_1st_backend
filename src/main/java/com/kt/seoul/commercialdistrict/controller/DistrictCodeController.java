package com.kt.seoul.commercialdistrict.controller;

import com.kt.seoul.commercialdistrict.dto.DistrictCodeResponse;
import com.kt.seoul.commercialdistrict.service.DistrictCodeService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 자치구 코드 REST API 컨트롤러
 * 
 * @author KT 개발팀
 * @version 1.0.0
 */
@Slf4j
@RestController
@RequestMapping("/district-codes")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class DistrictCodeController {
    
    private final DistrictCodeService districtCodeService;
    
    /**
     * 모든 자치구 코드 정보 조회 (코드 순)
     * 
     * @return 모든 자치구 코드 정보 목록
     */
    @GetMapping
    public ResponseEntity<List<DistrictCodeResponse>> getAllDistrictCodes() {
        log.info("GET /district-codes - 모든 자치구 코드 정보 조회 요청");
        
        try {
            List<DistrictCodeResponse> districts = districtCodeService.getAllDistrictCodes();
            
            log.info("GET /district-codes - 성공적으로 {}개 자치구 코드 데이터 응답", districts.size());
            return ResponseEntity.ok(districts);
            
        } catch (Exception e) {
            log.error("GET /district-codes - 자치구 코드 데이터 조회 중 오류 발생", e);
            return ResponseEntity.internalServerError().build();
        }
    }
    
    /**
     * 모든 자치구 코드 정보 조회 (이름 순)
     * 
     * @return 이름 순으로 정렬된 자치구 코드 정보 목록
     */
    @GetMapping("/by-name")
    public ResponseEntity<List<DistrictCodeResponse>> getAllDistrictCodesByName() {
        log.info("GET /district-codes/by-name - 이름 순으로 모든 자치구 코드 정보 조회 요청");
        
        try {
            List<DistrictCodeResponse> districts = districtCodeService.getAllDistrictCodesByName();
            
            log.info("GET /district-codes/by-name - 성공적으로 {}개 자치구 코드 데이터 응답", districts.size());
            return ResponseEntity.ok(districts);
            
        } catch (Exception e) {
            log.error("GET /district-codes/by-name - 자치구 코드 데이터 조회 중 오류 발생", e);
            return ResponseEntity.internalServerError().build();
        }
    }
    
    /**
     * 자치구 코드로 자치구 정보 조회
     * 
     * @param districtCode 자치구 코드
     * @return 해당 코드의 자치구 정보
     */
    @GetMapping("/code/{districtCode}")
    public ResponseEntity<DistrictCodeResponse> getDistrictByCode(@PathVariable String districtCode) {
        log.info("GET /district-codes/code/{} - 자치구 코드로 조회 요청", districtCode);
        
        try {
            DistrictCodeResponse district = districtCodeService.getDistrictByCode(districtCode);
            
            log.info("GET /district-codes/code/{} - 성공적으로 자치구 코드 데이터 응답", districtCode);
            return ResponseEntity.ok(district);
            
        } catch (IllegalArgumentException e) {
            log.warn("GET /district-codes/code/{} - 자치구 코드를 찾을 수 없음: {}", districtCode, e.getMessage());
            return ResponseEntity.notFound().build();
            
        } catch (Exception e) {
            log.error("GET /district-codes/code/{} - 자치구 코드 데이터 조회 중 오류 발생", districtCode, e);
            return ResponseEntity.internalServerError().build();
        }
    }
    
    /**
     * 자치구명으로 자치구 정보 조회
     * 
     * @param districtName 자치구명
     * @return 해당 이름의 자치구 정보
     */
    @GetMapping("/name/{districtName}")
    public ResponseEntity<DistrictCodeResponse> getDistrictByName(@PathVariable String districtName) {
        log.info("GET /district-codes/name/{} - 자치구명으로 조회 요청", districtName);
        
        try {
            DistrictCodeResponse district = districtCodeService.getDistrictByName(districtName);
            
            log.info("GET /district-codes/name/{} - 성공적으로 자치구 코드 데이터 응답", districtName);
            return ResponseEntity.ok(district);
            
        } catch (IllegalArgumentException e) {
            log.warn("GET /district-codes/name/{} - 자치구를 찾을 수 없음: {}", districtName, e.getMessage());
            return ResponseEntity.notFound().build();
            
        } catch (Exception e) {
            log.error("GET /district-codes/name/{} - 자치구 코드 데이터 조회 중 오류 발생", districtName, e);
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
    public ResponseEntity<List<DistrictCodeResponse>> searchDistrictsByName(@RequestParam String keyword) {
        log.info("GET /district-codes/search?keyword={} - 자치구명 검색 요청", keyword);
        
        try {
            List<DistrictCodeResponse> districts = districtCodeService.searchDistrictsByName(keyword);
            
            log.info("GET /district-codes/search - 성공적으로 {}개 검색 결과 응답", districts.size());
            return ResponseEntity.ok(districts);
            
        } catch (Exception e) {
            log.error("GET /district-codes/search - 자치구 검색 중 오류 발생", e);
            return ResponseEntity.internalServerError().build();
        }
    }
    
    /**
     * 전체 자치구 수 조회
     * 
     * @return 전체 자치구 수
     */
    @GetMapping("/count")
    public ResponseEntity<Long> getTotalDistrictCount() {
        log.info("GET /district-codes/count - 전체 자치구 수 조회 요청");
        
        try {
            Long count = districtCodeService.getTotalDistrictCount();
            
            log.info("GET /district-codes/count - 성공적으로 전체 자치구 수 응답: {}", count);
            return ResponseEntity.ok(count);
            
        } catch (Exception e) {
            log.error("GET /district-codes/count - 전체 자치구 수 조회 중 오류 발생", e);
            return ResponseEntity.internalServerError().build();
        }
    }
}
