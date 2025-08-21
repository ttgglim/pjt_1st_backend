package com.kt.seoul.commercialdistrict.service;

import com.kt.seoul.commercialdistrict.dto.SalesDataResponse;
import com.kt.seoul.commercialdistrict.dto.SalesStatisticsResponse;
import com.kt.seoul.commercialdistrict.entity.SalesData;
import com.kt.seoul.commercialdistrict.repository.SalesDataRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigInteger;
import java.util.List;
import java.util.Map;
import java.util.HashMap;
import java.util.ArrayList;
import java.util.stream.Collectors;
import java.util.Comparator;

/**
 * 매출 데이터 서비스
 * 
 * @author KT 개발팀
 * @version 1.0.0
 */
@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class SalesDataService {
    
    private final SalesDataRepository salesDataRepository;
    
    /**
     * 자치구별 매출 데이터 조회
     * 
     * @param districtName 자치구명
     * @return 해당 자치구의 매출 데이터 목록
     */
    public List<SalesDataResponse> getSalesDataByDistrict(String districtName) {
        log.info("자치구별 매출 데이터 조회: {}", districtName);
        
        List<SalesData> salesDataList = salesDataRepository.findByDistrictName(districtName);
        
        return salesDataList.stream()
                .map(this::convertToResponse)
                .collect(Collectors.toList());
    }
    
    /**
     * 자치구별 특정 업종 매출 데이터 조회
     * 
     * @param districtName 자치구명
     * @param serviceCategoryName 서비스 업종명
     * @return 해당 자치구의 특정 업종 매출 데이터 목록
     */
    public List<SalesDataResponse> getSalesDataByDistrictAndCategory(String districtName, String serviceCategoryName) {
        log.info("자치구별 업종별 매출 데이터 조회: {} - {}", districtName, serviceCategoryName);
        
        List<SalesData> salesDataList = salesDataRepository.findByDistrictNameAndServiceCategoryName(districtName, serviceCategoryName);
        
        return salesDataList.stream()
                .map(this::convertToResponse)
                .collect(Collectors.toList());
    }
    
    /**
     * 업종별 매출 데이터 조회
     * 
     * @param serviceCategoryName 서비스 업종명
     * @return 해당 업종의 매출 데이터 목록
     */
    public List<SalesDataResponse> getSalesDataByCategory(String serviceCategoryName) {
        log.info("업종별 매출 데이터 조회: {}", serviceCategoryName);
        
        List<SalesData> salesDataList = salesDataRepository.findByServiceCategoryName(serviceCategoryName);
        
        return salesDataList.stream()
                .map(this::convertToResponse)
                .collect(Collectors.toList());
    }
    
    /**
     * 자치구별 총 매출 정보 조회
     * 
     * @param districtName 자치구명
     * @return 자치구별 총 매출 정보
     */
    public SalesStatisticsResponse.DistrictTotalSales getDistrictTotalSales(String districtName) {
        log.info("자치구별 총 매출 정보 조회: {}", districtName);
        
        try {
            // 총 매출 금액과 건수 조회
            BigInteger totalAmount = salesDataRepository.sumMonthlySalesAmountByDistrictName(districtName);
            Integer totalCount = salesDataRepository.sumMonthlySalesCountByDistrictName(districtName);
            
            log.info("자치구 {} - 총 매출 금액: {}, 총 매출 건수: {}", districtName, totalAmount, totalCount);
            
            // 업종별 통계 조회
            List<SalesStatisticsResponse.CategorySalesStatistics> categoryStatistics = 
                getCategorySalesStatisticsByDistrict(districtName);
            
            // 성별 통계 조회
            SalesStatisticsResponse.GenderSalesStatistics genderStatistics = 
                getGenderSalesStatisticsByDistrict(districtName);
            
            // 주중/주말 통계 조회
            SalesStatisticsResponse.WeekdayWeekendSalesStatistics weekdayWeekendStatistics = 
                getWeekdayWeekendSalesStatisticsByDistrict(districtName);
            
            SalesStatisticsResponse.DistrictTotalSales result = SalesStatisticsResponse.DistrictTotalSales.builder()
                    .districtName(districtName)
                    .totalAmount(totalAmount != null ? totalAmount : BigInteger.ZERO)
                    .totalCount(totalCount != null ? totalCount : 0)
                    .categoryStatistics(categoryStatistics)
                    .genderStatistics(genderStatistics)
                    .weekdayWeekendStatistics(weekdayWeekendStatistics)
                    .build();
            
            log.info("자치구 {} 총 매출 정보 조회 완료 - 업종별 통계: {}개, 성별 통계: 남성 {}원/{}건, 여성 {}원/{}건", 
                    districtName, categoryStatistics.size(), 
                    genderStatistics.getMaleAmount(), genderStatistics.getMaleCount(),
                    genderStatistics.getFemaleAmount(), genderStatistics.getFemaleCount());
            
            return result;
            
        } catch (Exception e) {
            log.error("자치구 {} 총 매출 정보 조회 중 오류 발생", districtName, e);
            throw new RuntimeException("매출 통계 조회 실패: " + e.getMessage(), e);
        }
    }
    
    /**
     * 자치구별 업종별 매출 통계 조회
     * 
     * @param districtName 자치구명
     * @return 자치구별 업종별 매출 통계
     */
    public List<SalesStatisticsResponse.CategorySalesStatistics> getCategorySalesStatisticsByDistrict(String districtName) {
        log.info("자치구별 업종별 매출 통계 조회: {}", districtName);
        
        List<Object[]> results = salesDataRepository.getSalesStatisticsByDistrict(districtName);
        
        return results.stream()
                .map(result -> SalesStatisticsResponse.CategorySalesStatistics.builder()
                        .serviceCategoryName(result[0] != null ? (String) result[0] : "")
                        .totalAmount(result[1] != null ? (BigInteger) result[1] : BigInteger.ZERO)
                        .totalCount(result[2] != null ? (Integer) result[2] : 0)
                        .build())
                .collect(Collectors.toList());
    }
    
    /**
     * 업종별 자치구별 매출 통계 조회
     * 
     * @param serviceCategoryName 서비스 업종명
     * @return 업종별 자치구별 매출 통계
     */
    public List<SalesStatisticsResponse.DistrictSalesStatistics> getDistrictSalesStatisticsByCategory(String serviceCategoryName) {
        log.info("업종별 자치구별 매출 통계 조회: {}", serviceCategoryName);
        
        List<Object[]> results = salesDataRepository.getSalesStatisticsByServiceCategory(serviceCategoryName);
        
        return results.stream()
                .map(result -> SalesStatisticsResponse.DistrictSalesStatistics.builder()
                        .districtName(result[0] != null ? (String) result[0] : "")
                        .totalAmount(result[1] != null ? (BigInteger) result[1] : BigInteger.ZERO)
                        .totalCount(result[2] != null ? (Integer) result[2] : 0)
                        .build())
                .collect(Collectors.toList());
    }
    
    /**
     * 자치구별 성별 매출 통계 조회
     * 
     * @param districtName 자치구명
     * @return 자치구별 성별 매출 통계
     */
    public SalesStatisticsResponse.GenderSalesStatistics getGenderSalesStatisticsByDistrict(String districtName) {
        log.info("자치구별 성별 매출 통계 조회: {}", districtName);
        
        Object[] result = salesDataRepository.getGenderSalesStatisticsByDistrict(districtName);
        
        if (result != null && result.length >= 4) {
            return SalesStatisticsResponse.GenderSalesStatistics.builder()
                    .maleAmount(result[0] != null ? (BigInteger) result[0] : BigInteger.ZERO)
                    .femaleAmount(result[1] != null ? (BigInteger) result[1] : BigInteger.ZERO)
                    .maleCount(result[2] != null ? (Integer) result[2] : 0)
                    .femaleCount(result[3] != null ? (Integer) result[3] : 0)
                    .build();
        }
        
        return SalesStatisticsResponse.GenderSalesStatistics.builder()
                .maleAmount(BigInteger.ZERO)
                .femaleAmount(BigInteger.ZERO)
                .maleCount(0)
                .femaleCount(0)
                .build();
    }
    
    /**
     * 자치구별 주중/주말 매출 통계 조회
     * 
     * @param districtName 자치구명
     * @return 자치구별 주중/주말 매출 통계
     */
    public SalesStatisticsResponse.WeekdayWeekendSalesStatistics getWeekdayWeekendSalesStatisticsByDistrict(String districtName) {
        log.info("자치구별 주중/주말 매출 통계 조회: {}", districtName);
        
        Object[] result = salesDataRepository.getWeekdayWeekendSalesStatisticsByDistrict(districtName);
        
        if (result != null && result.length >= 4) {
            return SalesStatisticsResponse.WeekdayWeekendSalesStatistics.builder()
                    .weekdayAmount(result[0] != null ? (BigInteger) result[0] : BigInteger.ZERO)
                    .weekendAmount(result[1] != null ? (BigInteger) result[1] : BigInteger.ZERO)
                    .weekdayCount(result[2] != null ? (Integer) result[2] : 0)
                    .weekendCount(result[3] != null ? (Integer) result[3] : 0)
                    .build();
        }
        
        return SalesStatisticsResponse.WeekdayWeekendSalesStatistics.builder()
                .weekdayAmount(BigInteger.ZERO)
                .weekendAmount(BigInteger.ZERO)
                .weekdayCount(0)
                .weekendCount(0)
                .build();
    }
    
    /**
     * 전체 자치구 매출 순위 조회 (상위 N개)
     * 
     * @param limit 조회할 개수
     * @return 매출 순위별 자치구 목록
     */
    public List<SalesStatisticsResponse.DistrictSalesStatistics> getTopDistrictsBySales(int limit) {
        log.info("전체 자치구 매출 순위 조회 (상위 {}개)", limit);
        
        List<Object[]> results = salesDataRepository.getTopDistrictsBySales(limit);
        
        return results.stream()
                .map(result -> SalesStatisticsResponse.DistrictSalesStatistics.builder()
                        .districtName(result[0] != null ? (String) result[0] : "")
                        .totalAmount(result[1] != null ? (BigInteger) result[1] : BigInteger.ZERO)
                        .totalCount(result[2] != null ? (Integer) result[2] : 0)
                        .build())
                .collect(Collectors.toList());
    }
    
    /**
     * 전체 업종 매출 순위 조회 (상위 N개)
     * 
     * @param limit 조회할 개수
     * @return 매출 순위별 업종 목록
     */
    public List<SalesStatisticsResponse.CategorySalesStatistics> getTopServiceCategoriesBySales(int limit) {
        log.info("전체 업종 매출 순위 조회 (상위 {}개)", limit);
        
        List<Object[]> results = salesDataRepository.getTopServiceCategoriesBySales(limit);
        
        return results.stream()
                .map(result -> SalesStatisticsResponse.CategorySalesStatistics.builder()
                        .serviceCategoryName(result[0] != null ? (String) result[0] : "")
                        .totalAmount(result[1] != null ? (BigInteger) result[1] : BigInteger.ZERO)
                        .totalCount(result[2] != null ? (Integer) result[2] : 0)
                        .build())
                .collect(Collectors.toList());
    }
    
    /**
     * 업종별 월별 평균 매출 통계 조회 (3개 분류로 그룹화)
     * 
     * @return 업종 분류별 월별 평균 매출 통계
     */
    public List<SalesStatisticsResponse.CategoryGroupMonthlySales> getCategoryGroupMonthlySales() {
        log.info("업종별 월별 평균 매출 통계 조회");
        
        try {
            List<Object[]> results = salesDataRepository.getMonthlyAverageSalesByCategory();
            
            return processMonthlySalesData(results);
            
        } catch (Exception e) {
            log.error("업종별 월별 평균 매출 통계 조회 중 오류 발생", e);
            throw new RuntimeException("월별 매출 통계 조회 실패: " + e.getMessage(), e);
        }
    }
    
    /**
     * 자치구별 업종별 월별 평균 매출 통계 조회 (3개 분류로 그룹화)
     * 
     * @param districtName 자치구명
     * @return 자치구별 업종 분류별 월별 평균 매출 통계
     */
    public List<SalesStatisticsResponse.CategoryGroupMonthlySales> getCategoryGroupMonthlySalesByDistrict(String districtName) {
        log.info("자치구별 업종별 월별 평균 매출 통계 조회: {}", districtName);
        
        try {
            List<Object[]> results = salesDataRepository.getMonthlyAverageSalesByCategoryAndDistrict(districtName);
            
            return processMonthlySalesData(results);
            
        } catch (Exception e) {
            log.error("자치구별 업종별 월별 평균 매출 통계 조회 중 오류 발생: {}", districtName, e);
            throw new RuntimeException("자치구별 월별 매출 통계 조회 실패: " + e.getMessage(), e);
        }
    }
    
    /**
     * 월별 매출 데이터 처리 공통 메서드
     * 
     * @param results 쿼리 결과
     * @return 업종 분류별 월별 평균 매출 통계
     */
    private List<SalesStatisticsResponse.CategoryGroupMonthlySales> processMonthlySalesData(List<Object[]> results) {
        Map<String, Map<String, List<Double>>> groupedData = new HashMap<>();

        for (Object[] row : results) {
            String serviceCategoryName = (String) row[0];
            String yearMonth = (String) row[1];
            Double averageAmount = ((Number) row[2]).doubleValue();

            String categoryGroup = "기타 서비스"; // Default category

            if (serviceCategoryName.contains("한식") || serviceCategoryName.contains("중식") ||
                serviceCategoryName.contains("양식") || serviceCategoryName.contains("일식")) {
                categoryGroup = "한식/중식/양식/일식";
            } else if (serviceCategoryName.contains("분식") || serviceCategoryName.contains("치킨") ||
                       serviceCategoryName.contains("패스트푸드") || serviceCategoryName.contains("피자") ||
                       serviceCategoryName.contains("햄버거")) {
                categoryGroup = "분식/치킨/패스트푸드";
            } else if (serviceCategoryName.contains("제과점") || serviceCategoryName.contains("카페") ||
                       serviceCategoryName.contains("호프") || serviceCategoryName.contains("베이커리") ||
                       serviceCategoryName.contains("음료")) {
                categoryGroup = "제과점/카페/호프";
            }

            groupedData.computeIfAbsent(categoryGroup, k -> new HashMap<>())
                       .computeIfAbsent(yearMonth, k -> new ArrayList<>())
                       .add(averageAmount);
        }

        List<SalesStatisticsResponse.CategoryGroupMonthlySales> categoryGroups = new ArrayList<>();
        for (Map.Entry<String, Map<String, List<Double>>> entry : groupedData.entrySet()) {
            String categoryGroup = entry.getKey();
            Map<String, List<Double>> monthlyAmounts = entry.getValue();

            List<SalesStatisticsResponse.MonthlyAverageSales> monthlyData = monthlyAmounts.entrySet().stream()
                    .map(monthEntry -> {
                        String yearMonth = monthEntry.getKey();
                        Double avgAmount = monthEntry.getValue().stream()
                                .mapToDouble(Double::doubleValue)
                                .average()
                                .orElse(0.0);
                        return SalesStatisticsResponse.MonthlyAverageSales.builder()
                                .serviceCategoryName(categoryGroup)
                                .yearMonth(yearMonth)
                                .averageAmount(avgAmount)
                                .build();
                    })
                    .sorted(Comparator.comparing(SalesStatisticsResponse.MonthlyAverageSales::getYearMonth))
                    .collect(Collectors.toList());

            categoryGroups.add(SalesStatisticsResponse.CategoryGroupMonthlySales.builder()
                    .categoryGroup(categoryGroup)
                    .monthlyData(monthlyData)
                    .build());
        }
        
        // Ensure consistent order for categories
        categoryGroups.sort(Comparator.comparing(g -> {
            if (g.getCategoryGroup().equals("한식/중식/양식/일식")) return 1;
            if (g.getCategoryGroup().equals("분식/치킨/패스트푸드")) return 2;
            if (g.getCategoryGroup().equals("제과점/카페/호프")) return 3;
            return 4; // For "기타 서비스" or any other
        }));
        
        return categoryGroups;
    }

    /**
     * 자치구별 월 평균 매출 조회
     * 
     * @param districtName 자치구명
     * @return 자치구별 월 평균 매출
     */
    public Double getAverageMonthlySalesByDistrict(String districtName) {
        log.info("자치구별 월 평균 매출 조회 - 자치구: {}", districtName);
        
        try {
            Double avgMonthlySales = salesDataRepository.getAverageMonthlySalesByDistrict(districtName);
            log.info("자치구별 월 평균 매출 조회 완료 - 자치구: {}, 월 평균 매출: {}", districtName, avgMonthlySales);
            return avgMonthlySales != null ? avgMonthlySales : 0.0;
            
        } catch (Exception e) {
            log.error("자치구별 월 평균 매출 조회 중 오류 발생 - 자치구: {}", districtName, e);
            throw new RuntimeException("월 평균 매출 조회 실패: " + e.getMessage(), e);
        }
    }

    /**
     * 자치구별 최근 날짜 사업체 수 조회
     * 
     * @param districtName 자치구명
     * @return 자치구별 최근 날짜 사업체 수
     */
    public Integer getRecentBusinessesByDistrict(String districtName) {
        log.info("자치구별 최근 날짜 사업체 수 조회 - 자치구: {}", districtName);
        
        try {
            Integer recentBusinesses = salesDataRepository.getRecentBusinessesByDistrict(districtName);
            log.info("자치구별 최근 날짜 사업체 수 조회 완료 - 자치구: {}, 최근 사업체 수: {}", districtName, recentBusinesses);
            return recentBusinesses != null ? recentBusinesses : 0;
            
        } catch (Exception e) {
            log.error("자치구별 최근 날짜 사업체 수 조회 중 오류 발생 - 자치구: {}", districtName, e);
            throw new RuntimeException("최근 사업체 수 조회 실패: " + e.getMessage(), e);
        }
    }
    
    /**
     * Entity를 Response DTO로 변환
     * 
     * @param salesData 매출 데이터 Entity
     * @return 매출 데이터 Response DTO
     */
    private SalesDataResponse convertToResponse(SalesData salesData) {
        return SalesDataResponse.builder()
                .id(salesData.getId())
                .baseYearMonth(salesData.getBaseYearMonth())
                .districtCode(salesData.getDistrictCode())
                .districtName(salesData.getDistrictName())
                .serviceCategoryCode(salesData.getServiceCategoryCode())
                .serviceCategoryName(salesData.getServiceCategoryName())
                .monthlySalesAmount(salesData.getMonthlySalesAmount())
                .monthlySalesCount(salesData.getMonthlySalesCount())
                .weekdaySalesAmount(salesData.getWeekdaySalesAmount())
                .weekendSalesAmount(salesData.getWeekendSalesAmount())
                .maleSalesAmount(salesData.getMaleSalesAmount())
                .femaleSalesAmount(salesData.getFemaleSalesAmount())
                .weekdaySalesCount(salesData.getWeekdaySalesCount())
                .weekendSalesCount(salesData.getWeekendSalesCount())
                .maleSalesCount(salesData.getMaleSalesCount())
                .femaleSalesCount(salesData.getFemaleSalesCount())
                .build();
    }
}
