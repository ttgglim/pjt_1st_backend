package com.kt.seoul.commercialdistrict.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigInteger;

/**
 * 매출 데이터 Entity
 * 
 * 서울시 자치구별 서비스 업종 매출 정보를 저장합니다.
 * 
 * @author KT 개발팀
 * @version 1.0.0
 */
@Entity
@Table(name = "sales_data")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SalesData {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    
    /**
     * 기준 년월 코드 (예: 202504)
     */
    @Column(name = "기준_년월_코드", length = 10)
    private String baseYearMonth;
    
    /**
     * 자치구 코드 (예: 11500)
     */
    @Column(name = "자치구_코드")
    private Integer districtCode;
    
    /**
     * 자치구 코드명 (예: 양천구)
     */
    @Column(name = "자치구_코드_명", length = 20)
    private String districtName;
    
    /**
     * 서비스 업종 코드 (예: CS100008)
     */
    @Column(name = "서비스_업종_코드", length = 20)
    private String serviceCategoryCode;
    
    /**
     * 서비스 업종 코드명 (예: 분식전문점)
     */
    @Column(name = "서비스_업종_코드_명", length = 50)
    private String serviceCategoryName;
    
    /**
     * 당월 매출 금액
     */
    @Column(name = "당월_매출_금액")
    private BigInteger monthlySalesAmount;
    
    /**
     * 당월 매출 건수
     */
    @Column(name = "당월_매출_건수")
    private Integer monthlySalesCount;
    
    /**
     * 주중 매출 금액
     */
    @Column(name = "주중_매출_금액")
    private BigInteger weekdaySalesAmount;
    
    /**
     * 주말 매출 금액
     */
    @Column(name = "주말_매출_금액")
    private BigInteger weekendSalesAmount;
    
    /**
     * 남성 매출 금액
     */
    @Column(name = "남성_매출_금액")
    private BigInteger maleSalesAmount;
    
    /**
     * 여성 매출 금액
     */
    @Column(name = "여성_매출_금액")
    private BigInteger femaleSalesAmount;
    
    /**
     * 주중 매출 건수
     */
    @Column(name = "주중_매출_건수")
    private Integer weekdaySalesCount;
    
    /**
     * 주말 매출 건수
     */
    @Column(name = "주말_매출_건수")
    private Integer weekendSalesCount;
    
    /**
     * 남성 매출 건수
     */
    @Column(name = "남성_매출_건수")
    private Integer maleSalesCount;
    
    /**
     * 여성 매출 건수
     */
    @Column(name = "여성_매출_건수")
    private Integer femaleSalesCount;
}
