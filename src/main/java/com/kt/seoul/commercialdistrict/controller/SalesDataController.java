package com.kt.seoul.commercialdistrict.controller;

import com.kt.seoul.commercialdistrict.dto.SalesDataResponse;
import com.kt.seoul.commercialdistrict.dto.SalesStatisticsResponse;
import com.kt.seoul.commercialdistrict.service.SalesDataService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.math.BigInteger;
import com.kt.seoul.commercialdistrict.repository.SalesDataRepository;
import com.kt.seoul.commercialdistrict.entity.SalesData;

/**
 * 매출 데이터 REST API 컨트롤러
 * 
 * @author KT 개발팀
 * @version 1.0.0
 */
@Slf4j
@RestController
@RequestMapping("/sales")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class SalesDataController {
    
    private final SalesDataService salesDataService;
    private final SalesDataRepository salesDataRepository;
    
    /**
     * 자치구별 매출 데이터 조회
     * 
     * @param districtName 자치구명
     * @return 해당 자치구의 매출 데이터 목록
     */
    @GetMapping("/district/{districtName}")
    public ResponseEntity<List<SalesDataResponse>> getSalesDataByDistrict(@PathVariable String districtName) {
        log.info("GET /sales/district/{} - 자치구별 매출 데이터 조회 요청", districtName);
        
        try {
            List<SalesDataResponse> salesData = salesDataService.getSalesDataByDistrict(districtName);
            
            log.info("GET /sales/district/{} - 성공적으로 {}개 매출 데이터 응답", districtName, salesData.size());
            return ResponseEntity.ok(salesData);
            
        } catch (Exception e) {
            log.error("GET /sales/district/{} - 매출 데이터 조회 중 오류 발생", districtName, e);
            return ResponseEntity.internalServerError().build();
        }
    }
    
    /**
     * 자치구별 특정 업종 매출 데이터 조회
     * 
     * @param districtName 자치구명
     * @param serviceCategoryName 서비스 업종명
     * @return 해당 자치구의 특정 업종 매출 데이터 목록
     */
    @GetMapping("/district/{districtName}/category/{serviceCategoryName}")
    public ResponseEntity<List<SalesDataResponse>> getSalesDataByDistrictAndCategory(
            @PathVariable String districtName,
            @PathVariable String serviceCategoryName) {
        log.info("GET /sales/district/{}/category/{} - 자치구별 업종별 매출 데이터 조회 요청", districtName, serviceCategoryName);
        
        try {
            List<SalesDataResponse> salesData = salesDataService.getSalesDataByDistrictAndCategory(districtName, serviceCategoryName);
            
            log.info("GET /sales/district/{}/category/{} - 성공적으로 {}개 매출 데이터 응답", districtName, serviceCategoryName, salesData.size());
            return ResponseEntity.ok(salesData);
            
        } catch (Exception e) {
            log.error("GET /sales/district/{}/category/{} - 매출 데이터 조회 중 오류 발생", districtName, serviceCategoryName, e);
            return ResponseEntity.internalServerError().build();
        }
    }
    
    /**
     * 업종별 매출 데이터 조회
     * 
     * @param serviceCategoryName 서비스 업종명
     * @return 해당 업종의 매출 데이터 목록
     */
    @GetMapping("/category/{serviceCategoryName}")
    public ResponseEntity<List<SalesDataResponse>> getSalesDataByCategory(@PathVariable String serviceCategoryName) {
        log.info("GET /sales/category/{} - 업종별 매출 데이터 조회 요청", serviceCategoryName);
        
        try {
            List<SalesDataResponse> salesData = salesDataService.getSalesDataByCategory(serviceCategoryName);
            
            log.info("GET /sales/category/{} - 성공적으로 {}개 매출 데이터 응답", serviceCategoryName, salesData.size());
            return ResponseEntity.ok(salesData);
            
        } catch (Exception e) {
            log.error("GET /sales/category/{} - 매출 데이터 조회 중 오류 발생", serviceCategoryName, e);
            return ResponseEntity.internalServerError().build();
        }
    }
    
    /**
     * 자치구별 총 매출 정보 조회
     * 
     * @param districtName 자치구명
     * @return 자치구별 총 매출 정보
     */
    @GetMapping("/district/{districtName}/total")
    public ResponseEntity<SalesStatisticsResponse.DistrictTotalSales> getDistrictTotalSales(@PathVariable String districtName) {
        log.info("GET /sales/district/{}/total - 자치구별 총 매출 정보 조회 요청", districtName);
        
        try {
            SalesStatisticsResponse.DistrictTotalSales totalSales = salesDataService.getDistrictTotalSales(districtName);
            
            log.info("GET /sales/district/{}/total - 성공적으로 자치구 총 매출 정보 응답", districtName);
            return ResponseEntity.ok(totalSales);
            
        } catch (Exception e) {
            log.error("GET /sales/district/{}/total - 자치구 총 매출 정보 조회 중 오류 발생", districtName, e);
            return ResponseEntity.internalServerError().build();
        }
    }
    
    /**
     * 자치구별 업종별 매출 통계 조회
     * 
     * @param districtName 자치구명
     * @return 자치구별 업종별 매출 통계
     */
    @GetMapping("/district/{districtName}/statistics/category")
    public ResponseEntity<List<SalesStatisticsResponse.CategorySalesStatistics>> getCategorySalesStatisticsByDistrict(@PathVariable String districtName) {
        log.info("GET /sales/district/{}/statistics/category - 자치구별 업종별 매출 통계 조회 요청", districtName);
        
        try {
            List<SalesStatisticsResponse.CategorySalesStatistics> statistics = salesDataService.getCategorySalesStatisticsByDistrict(districtName);
            
            log.info("GET /sales/district/{}/statistics/category - 성공적으로 {}개 업종별 통계 응답", districtName, statistics.size());
            return ResponseEntity.ok(statistics);
            
        } catch (Exception e) {
            log.error("GET /sales/district/{}/statistics/category - 업종별 매출 통계 조회 중 오류 발생", districtName, e);
            return ResponseEntity.internalServerError().build();
        }
    }
    
    /**
     * 업종별 자치구별 매출 통계 조회
     * 
     * @param serviceCategoryName 서비스 업종명
     * @return 업종별 자치구별 매출 통계
     */
    @GetMapping("/category/{serviceCategoryName}/statistics/district")
    public ResponseEntity<List<SalesStatisticsResponse.DistrictSalesStatistics>> getDistrictSalesStatisticsByCategory(@PathVariable String serviceCategoryName) {
        log.info("GET /sales/category/{}/statistics/district - 업종별 자치구별 매출 통계 조회 요청", serviceCategoryName);
        
        try {
            List<SalesStatisticsResponse.DistrictSalesStatistics> statistics = salesDataService.getDistrictSalesStatisticsByCategory(serviceCategoryName);
            
            log.info("GET /sales/category/{}/statistics/district - 성공적으로 {}개 자치구별 통계 응답", serviceCategoryName, statistics.size());
            return ResponseEntity.ok(statistics);
            
        } catch (Exception e) {
            log.error("GET /sales/category/{}/statistics/district - 자치구별 매출 통계 조회 중 오류 발생", serviceCategoryName, e);
            return ResponseEntity.internalServerError().build();
        }
    }
    
    /**
     * 자치구별 성별 매출 통계 조회
     * 
     * @param districtName 자치구명
     * @return 자치구별 성별 매출 통계
     */
    @GetMapping("/district/{districtName}/statistics/gender")
    public ResponseEntity<SalesStatisticsResponse.GenderSalesStatistics> getGenderSalesStatisticsByDistrict(@PathVariable String districtName) {
        log.info("GET /sales/district/{}/statistics/gender - 자치구별 성별 매출 통계 조회 요청", districtName);
        
        try {
            SalesStatisticsResponse.GenderSalesStatistics statistics = salesDataService.getGenderSalesStatisticsByDistrict(districtName);
            
            log.info("GET /sales/district/{}/statistics/gender - 성공적으로 성별 매출 통계 응답", districtName);
            return ResponseEntity.ok(statistics);
            
        } catch (Exception e) {
            log.error("GET /sales/district/{}/statistics/gender - 성별 매출 통계 조회 중 오류 발생", districtName, e);
            return ResponseEntity.internalServerError().build();
        }
    }
    
    /**
     * 자치구별 주중/주말 매출 통계 조회
     * 
     * @param districtName 자치구명
     * @return 자치구별 주중/주말 매출 통계
     */
    @GetMapping("/district/{districtName}/statistics/weekday-weekend")
    public ResponseEntity<SalesStatisticsResponse.WeekdayWeekendSalesStatistics> getWeekdayWeekendSalesStatisticsByDistrict(@PathVariable String districtName) {
        log.info("GET /sales/district/{}/statistics/weekday-weekend - 자치구별 주중/주말 매출 통계 조회 요청", districtName);
        
        try {
            SalesStatisticsResponse.WeekdayWeekendSalesStatistics statistics = salesDataService.getWeekdayWeekendSalesStatisticsByDistrict(districtName);
            
            log.info("GET /sales/district/{}/statistics/weekday-weekend - 성공적으로 주중/주말 매출 통계 응답", districtName);
            return ResponseEntity.ok(statistics);
            
        } catch (Exception e) {
            log.error("GET /sales/district/{}/statistics/weekday-weekend - 주중/주말 매출 통계 조회 중 오류 발생", districtName, e);
            return ResponseEntity.internalServerError().build();
        }
    }
    
    /**
     * 전체 자치구 매출 순위 조회 (상위 N개)
     * 
     * @param limit 조회할 개수 (기본값: 10)
     * @return 매출 순위별 자치구 목록
     */
    @GetMapping("/top/districts")
    public ResponseEntity<List<SalesStatisticsResponse.DistrictSalesStatistics>> getTopDistrictsBySales(
            @RequestParam(defaultValue = "10") int limit) {
        log.info("GET /sales/top/districts?limit={} - 전체 자치구 매출 순위 조회 요청", limit);
        
        try {
            List<SalesStatisticsResponse.DistrictSalesStatistics> topDistricts = salesDataService.getTopDistrictsBySales(limit);
            
            log.info("GET /sales/top/districts - 성공적으로 {}개 자치구 매출 순위 응답", topDistricts.size());
            return ResponseEntity.ok(topDistricts);
            
        } catch (Exception e) {
            log.error("GET /sales/top/districts - 자치구 매출 순위 조회 중 오류 발생", e);
            return ResponseEntity.internalServerError().build();
        }
    }
    
    /**
     * 전체 업종 매출 순위 조회 (상위 N개)
     * 
     * @param limit 조회할 개수 (기본값: 10)
     * @return 매출 순위별 업종 목록
     */
    @GetMapping("/top/categories")
    public ResponseEntity<List<SalesStatisticsResponse.CategorySalesStatistics>> getTopServiceCategoriesBySales(
            @RequestParam(defaultValue = "10") int limit) {
        log.info("GET /sales/top/categories?limit={} - 전체 업종 매출 순위 조회 요청", limit);
        
        try {
            List<SalesStatisticsResponse.CategorySalesStatistics> topCategories = salesDataService.getTopServiceCategoriesBySales(limit);
            
            log.info("GET /sales/top/categories - 성공적으로 {}개 업종 매출 순위 응답", topCategories.size());
            return ResponseEntity.ok(topCategories);
            
        } catch (Exception e) {
            log.error("GET /sales/top/categories - 업종 매출 순위 조회 중 오류 발생", e);
            return ResponseEntity.internalServerError().build();
        }
    }
    
    /**
     * 업종별 월별 평균 매출 통계 조회 (3개 분류로 그룹화)
     * 
     * @return 업종 분류별 월별 평균 매출 통계
     */
    @GetMapping("/monthly/category-groups")
    public ResponseEntity<List<SalesStatisticsResponse.CategoryGroupMonthlySales>> getCategoryGroupMonthlySales() {
        log.info("GET /sales/monthly/category-groups - 업종별 월별 평균 매출 통계 조회 요청");
        
        try {
            List<SalesStatisticsResponse.CategoryGroupMonthlySales> categoryGroups = salesDataService.getCategoryGroupMonthlySales();
            
            log.info("GET /sales/monthly/category-groups - 성공적으로 {}개 업종 분류별 월별 통계 응답", categoryGroups.size());
            return ResponseEntity.ok(categoryGroups);
            
        } catch (Exception e) {
            log.error("GET /sales/monthly/category-groups - 업종별 월별 평균 매출 통계 조회 중 오류 발생", e);
            return ResponseEntity.internalServerError().build();
        }
    }
    
    /**
     * 자치구별 업종별 월별 평균 매출 통계 조회 (3개 분류로 그룹화)
     * 
     * @param districtName 자치구명
     * @return 자치구별 업종 분류별 월별 평균 매출 통계
     */
    @GetMapping("/monthly/category-groups/{districtName}")
    public ResponseEntity<List<SalesStatisticsResponse.CategoryGroupMonthlySales>> getCategoryGroupMonthlySalesByDistrict(@PathVariable String districtName) {
        log.info("GET /sales/monthly/category-groups/{} - 자치구별 업종별 월별 평균 매출 통계 조회 요청", districtName);
        
        try {
            List<SalesStatisticsResponse.CategoryGroupMonthlySales> categoryGroups = salesDataService.getCategoryGroupMonthlySalesByDistrict(districtName);
            
            log.info("GET /sales/monthly/category-groups/{} - 성공적으로 {}개 업종 분류별 월별 통계 응답", districtName, categoryGroups.size());
            return ResponseEntity.ok(categoryGroups);
            
        } catch (Exception e) {
            log.error("GET /sales/monthly/category-groups/{} - 자치구별 업종별 월별 평균 매출 통계 조회 중 오류 발생", districtName, e);
            return ResponseEntity.internalServerError().build();
        }
    }

    /**
     * 자치구별 월 평균 매출 조회
     * 
     * @param districtName 자치구명
     * @return 자치구별 월 평균 매출
     */
    @GetMapping("/average-monthly-sales/{districtName}")
    public ResponseEntity<Double> getAverageMonthlySalesByDistrict(@PathVariable String districtName) {
        log.info("GET /sales/average-monthly-sales/{} - 자치구별 월 평균 매출 조회 요청", districtName);
        
        try {
            Double avgMonthlySales = salesDataService.getAverageMonthlySalesByDistrict(districtName);
            
            log.info("GET /sales/average-monthly-sales/{} - 성공적으로 월 평균 매출 응답: {}", districtName, avgMonthlySales);
            return ResponseEntity.ok(avgMonthlySales);
            
        } catch (Exception e) {
            log.error("GET /sales/average-monthly-sales/{} - 자치구별 월 평균 매출 조회 중 오류 발생", districtName, e);
            return ResponseEntity.internalServerError().build();
        }
    }

    /**
     * 자치구별 최근 날짜 사업체 수 조회
     * 
     * @param districtName 자치구명
     * @return 자치구별 최근 날짜 사업체 수
     */
    @GetMapping("/recent-businesses/{districtName}")
    public ResponseEntity<Integer> getRecentBusinessesByDistrict(@PathVariable String districtName) {
        log.info("GET /sales/recent-businesses/{} - 자치구별 최근 날짜 사업체 수 조회 요청", districtName);
        
        try {
            Integer recentBusinesses = salesDataService.getRecentBusinessesByDistrict(districtName);
            
            log.info("GET /sales/recent-businesses/{} - 성공적으로 최근 사업체 수 응답: {}", districtName, recentBusinesses);
            return ResponseEntity.ok(recentBusinesses);
            
        } catch (Exception e) {
            log.error("GET /sales/recent-businesses/{} - 자치구별 최근 날짜 사업체 수 조회 중 오류 발생", districtName, e);
            return ResponseEntity.internalServerError().build();
        }
    }
    
    /**
     * 테스트용 API - 데이터베이스 연결 확인
     * 
     * @return 간단한 테스트 응답
     */
    @GetMapping("/test")
    public ResponseEntity<String> testConnection() {
        log.info("GET /sales/test - 데이터베이스 연결 테스트 요청");
        
        try {
            long count = salesDataRepository.count();
            String response = "데이터베이스 연결 성공! 총 매출 데이터: " + count + "개";
            log.info("GET /sales/test - 성공적으로 응답: {}", response);
            return ResponseEntity.ok(response);
            
        } catch (Exception e) {
            log.error("GET /sales/test - 데이터베이스 연결 테스트 중 오류 발생", e);
            return ResponseEntity.internalServerError().body("데이터베이스 연결 실패: " + e.getMessage());
        }
    }
    
    /**
     * 테스트용 API - 자치구별 간단한 통계 확인
     * 
     * @param districtName 자치구명
     * @return 간단한 통계 정보
     */
    @GetMapping("/test/{districtName}")
    public ResponseEntity<String> testDistrictStatistics(@PathVariable String districtName) {
        log.info("GET /sales/test/{} - 자치구 통계 테스트 요청", districtName);
        
        try {
            // 기본 매출 데이터 조회
            List<SalesData> salesDataList = salesDataRepository.findByDistrictName(districtName);
            
            // 총 매출 금액 계산
            BigInteger totalAmount = salesDataRepository.sumMonthlySalesAmountByDistrictName(districtName);
            Integer totalCount = salesDataRepository.sumMonthlySalesCountByDistrictName(districtName);
            
            String response = String.format(
                "자치구: %s\n" +
                "매출 데이터 개수: %d개\n" +
                "총 매출 금액: %s원\n" +
                "총 매출 건수: %d건",
                districtName, salesDataList.size(), 
                totalAmount != null ? totalAmount.toString() : "0",
                totalCount != null ? totalCount : 0
            );
            
            log.info("GET /sales/test/{} - 성공적으로 응답", districtName);
            return ResponseEntity.ok(response);
            
        } catch (Exception e) {
            log.error("GET /sales/test/{} - 자치구 통계 테스트 중 오류 발생", districtName, e);
            return ResponseEntity.internalServerError().body("통계 조회 실패: " + e.getMessage());
        }
    }
}
