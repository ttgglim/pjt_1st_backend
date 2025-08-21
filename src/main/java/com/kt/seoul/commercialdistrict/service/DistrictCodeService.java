package com.kt.seoul.commercialdistrict.service;

import com.kt.seoul.commercialdistrict.dto.DistrictCodeResponse;
import com.kt.seoul.commercialdistrict.entity.DistrictCode;
import com.kt.seoul.commercialdistrict.repository.DistrictCodeRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

/**
 * 자치구 코드 서비스
 * 
 * @author KT 개발팀
 * @version 1.0.0
 */
@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class DistrictCodeService {
    
    private final DistrictCodeRepository districtCodeRepository;
    
    /**
     * 모든 자치구 코드 정보 조회 (코드 순)
     * 
     * @return 모든 자치구 코드 정보 목록
     */
    public List<DistrictCodeResponse> getAllDistrictCodes() {
        log.info("모든 자치구 코드 정보 조회");
        
        List<DistrictCode> districts = districtCodeRepository.findAllByOrderByDistrictCodeAsc();
        
        return districts.stream()
                .map(this::convertToResponse)
                .collect(Collectors.toList());
    }
    
    /**
     * 모든 자치구 코드 정보 조회 (이름 순)
     * 
     * @return 이름 순으로 정렬된 자치구 코드 정보 목록
     */
    public List<DistrictCodeResponse> getAllDistrictCodesByName() {
        log.info("이름 순으로 모든 자치구 코드 정보 조회");
        
        List<DistrictCode> districts = districtCodeRepository.findAllByOrderByDistrictNameAsc();
        
        return districts.stream()
                .map(this::convertToResponse)
                .collect(Collectors.toList());
    }
    
    /**
     * 자치구 코드로 자치구 정보 조회
     * 
     * @param districtCode 자치구 코드
     * @return 해당 코드의 자치구 정보
     * @throws IllegalArgumentException 자치구를 찾을 수 없는 경우
     */
    public DistrictCodeResponse getDistrictByCode(String districtCode) {
        log.info("자치구 코드로 조회: {}", districtCode);
        
        DistrictCode district = districtCodeRepository.findByDistrictCode(districtCode)
                .orElseThrow(() -> new IllegalArgumentException("자치구 코드를 찾을 수 없습니다: " + districtCode));
        
        return convertToResponse(district);
    }
    
    /**
     * 자치구명으로 자치구 정보 조회
     * 
     * @param districtName 자치구명
     * @return 해당 이름의 자치구 정보
     * @throws IllegalArgumentException 자치구를 찾을 수 없는 경우
     */
    public DistrictCodeResponse getDistrictByName(String districtName) {
        log.info("자치구명으로 조회: {}", districtName);
        
        DistrictCode district = districtCodeRepository.findByDistrictName(districtName)
                .orElseThrow(() -> new IllegalArgumentException("자치구를 찾을 수 없습니다: " + districtName));
        
        return convertToResponse(district);
    }
    
    /**
     * 자치구명 검색
     * 
     * @param keyword 검색 키워드
     * @return 조건에 맞는 자치구 목록
     */
    public List<DistrictCodeResponse> searchDistrictsByName(String keyword) {
        log.info("자치구명 검색: {}", keyword);
        
        List<DistrictCode> districts = districtCodeRepository.findByDistrictNameContaining(keyword);
        
        return districts.stream()
                .map(this::convertToResponse)
                .collect(Collectors.toList());
    }
    
    /**
     * 전체 자치구 수 조회
     * 
     * @return 전체 자치구 수
     */
    public Long getTotalDistrictCount() {
        return districtCodeRepository.countAllDistricts();
    }
    
    /**
     * Entity를 Response DTO로 변환
     * 
     * @param districtCode 자치구 코드 Entity
     * @return 자치구 코드 Response DTO
     */
    private DistrictCodeResponse convertToResponse(DistrictCode districtCode) {
        return DistrictCodeResponse.builder()
                .id(districtCode.getId())
                .districtCode(districtCode.getDistrictCode())
                .districtName(districtCode.getDistrictName())
                .build();
    }
}
