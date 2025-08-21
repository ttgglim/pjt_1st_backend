package com.kt.seoul.commercialdistrict.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigInteger;

/**
 * 매출 데이터 응답 DTO
 * 
 * @author KT 개발팀
 * @version 1.0.0
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SalesDataResponse {
    
    /**
     * 매출 데이터 ID
     */
    private Long id;
    
    /**
     * 기준 년월 코드
     */
    private String baseYearMonth;
    
    /**
     * 자치구 코드
     */
    private Integer districtCode;
    
    /**
     * 자치구명
     */
    private String districtName;
    
    /**
     * 서비스 업종 코드
     */
    private String serviceCategoryCode;
    
    /**
     * 서비스 업종명
     */
    private String serviceCategoryName;
    
    /**
     * 당월 매출 금액
     */
    private BigInteger monthlySalesAmount;
    
    /**
     * 당월 매출 건수
     */
    private Integer monthlySalesCount;
    
    /**
     * 주중 매출 금액
     */
    private BigInteger weekdaySalesAmount;
    
    /**
     * 주말 매출 금액
     */
    private BigInteger weekendSalesAmount;
    
    /**
     * 남성 매출 금액
     */
    private BigInteger maleSalesAmount;
    
    /**
     * 여성 매출 금액
     */
    private BigInteger femaleSalesAmount;
    
    /**
     * 주중 매출 건수
     */
    private Integer weekdaySalesCount;
    
    /**
     * 주말 매출 건수
     */
    private Integer weekendSalesCount;
    
    /**
     * 남성 매출 건수
     */
    private Integer maleSalesCount;
    
    /**
     * 여성 매출 건수
     */
    private Integer femaleSalesCount;
}
