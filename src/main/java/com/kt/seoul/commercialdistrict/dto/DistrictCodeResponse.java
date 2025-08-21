package com.kt.seoul.commercialdistrict.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 자치구 코드 응답 DTO
 * 
 * @author KT 개발팀
 * @version 1.0.0
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DistrictCodeResponse {
    
    /**
     * 자치구 ID
     */
    private Long id;
    
    /**
     * 자치구 코드
     */
    private String districtCode;
    
    /**
     * 자치구명
     */
    private String districtName;
}
