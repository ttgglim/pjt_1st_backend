package com.kt.seoul.commercialdistrict.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigInteger;
import java.util.List;

/**
 * 매출 통계 응답 DTO
 * 
 * @author KT 개발팀
 * @version 1.0.0
 */
@Data
@Builder
public class SalesStatisticsResponse {
    
    /**
     * 자치구별 업종별 매출 통계
     */
    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class CategorySalesStatistics {
        private String serviceCategoryName;
        private BigInteger totalAmount;
        private Integer totalCount;
    }
    
    /**
     * 자치구별 매출 통계
     */
    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class DistrictSalesStatistics {
        private String districtName;
        private BigInteger totalAmount;
        private Integer totalCount;
    }
    
    /**
     * 성별 매출 통계
     */
    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class GenderSalesStatistics {
        private BigInteger maleAmount;
        private BigInteger femaleAmount;
        private Integer maleCount;
        private Integer femaleCount;
    }
    
    /**
     * 주중/주말 매출 통계
     */
    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class WeekdayWeekendSalesStatistics {
        private BigInteger weekdayAmount;
        private BigInteger weekendAmount;
        private Integer weekdayCount;
        private Integer weekendCount;
    }
    
    /**
     * 자치구별 총 매출 정보
     */
    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class DistrictTotalSales {
        private String districtName;
        private BigInteger totalAmount;
        private Integer totalCount;
        private List<CategorySalesStatistics> categoryStatistics;
        private GenderSalesStatistics genderStatistics;
        private WeekdayWeekendSalesStatistics weekdayWeekendStatistics;
    }
    
    /**
     * 업종별 월별 평균 매출 통계
     */
    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class MonthlyAverageSales {
        private String serviceCategoryName;
        private String yearMonth;
        private Double averageAmount;
        private Double averageCount;
    }
    
    /**
     * 업종 분류별 월별 평균 매출 통계
     */
    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class CategoryGroupMonthlySales {
        private String categoryGroup;
        private List<MonthlyAverageSales> monthlyData;
    }
}
