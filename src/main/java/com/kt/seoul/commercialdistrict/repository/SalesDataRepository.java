package com.kt.seoul.commercialdistrict.repository;

import com.kt.seoul.commercialdistrict.entity.SalesData;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.math.BigInteger;
import java.util.List;

/**
 * 매출 데이터 Repository
 * 
 * @author KT 개발팀
 * @version 1.0.0
 */
@Repository
public interface SalesDataRepository extends JpaRepository<SalesData, Long> {
    
    /**
     * 자치구별 매출 데이터 조회
     * 
     * @param districtName 자치구명
     * @return 해당 자치구의 매출 데이터 목록
     */
    @Query(value = "SELECT * FROM sales_data WHERE 자치구_코드_명 = ?1", nativeQuery = true)
    List<SalesData> findByDistrictName(String districtName);
    
    /**
     * 자치구별 특정 업종 매출 데이터 조회
     * 
     * @param districtName 자치구명
     * @param serviceCategoryName 서비스 업종명
     * @return 해당 자치구의 특정 업종 매출 데이터 목록
     */
    @Query(value = "SELECT * FROM sales_data WHERE 자치구_코드_명 = ?1 AND 서비스_업종_코드_명 = ?2", nativeQuery = true)
    List<SalesData> findByDistrictNameAndServiceCategoryName(String districtName, String serviceCategoryName);
    
    /**
     * 업종별 매출 데이터 조회
     * 
     * @param serviceCategoryName 서비스 업종명
     * @return 해당 업종의 매출 데이터 목록
     */
    @Query(value = "SELECT * FROM sales_data WHERE 서비스_업종_코드_명 = ?1", nativeQuery = true)
    List<SalesData> findByServiceCategoryName(String serviceCategoryName);
    
    /**
     * 기준 년월별 매출 데이터 조회
     * 
     * @param baseYearMonth 기준 년월 코드
     * @return 해당 년월의 매출 데이터 목록
     */
    @Query(value = "SELECT * FROM sales_data WHERE 기준_년월_코드 = ?1", nativeQuery = true)
    List<SalesData> findByBaseYearMonth(String baseYearMonth);
    
    /**
     * 자치구별 총 매출 금액 조회
     * 
     * @param districtName 자치구명
     * @return 해당 자치구의 총 매출 금액
     */
    @Query(value = "SELECT SUM(s.당월_매출_금액) FROM sales_data s WHERE s.자치구_코드_명 = ?1", nativeQuery = true)
    BigInteger sumMonthlySalesAmountByDistrictName(String districtName);
    
    /**
     * 자치구별 총 매출 건수 조회
     * 
     * @param districtName 자치구명
     * @return 해당 자치구의 총 매출 건수
     */
    @Query(value = "SELECT SUM(s.당월_매출_건수) FROM sales_data s WHERE s.자치구_코드_명 = ?1", nativeQuery = true)
    Integer sumMonthlySalesCountByDistrictName(String districtName);
    
    /**
     * 업종별 총 매출 금액 조회
     * 
     * @param serviceCategoryName 서비스 업종명
     * @return 해당 업종의 총 매출 금액
     */
    @Query(value = "SELECT SUM(s.당월_매출_금액) FROM sales_data s WHERE s.서비스_업종_코드_명 = ?1", nativeQuery = true)
    BigInteger sumMonthlySalesAmountByServiceCategoryName(String serviceCategoryName);
    
    /**
     * 자치구별 업종별 매출 통계 조회
     * 
     * @param districtName 자치구명
     * @return 자치구별 업종별 매출 통계
     */
    @Query(value = "SELECT s.서비스_업종_코드_명, SUM(s.당월_매출_금액) as totalAmount, SUM(s.당월_매출_건수) as totalCount " +
           "FROM sales_data s WHERE s.자치구_코드_명 = ?1 " +
           "GROUP BY s.서비스_업종_코드_명 ORDER BY totalAmount DESC", nativeQuery = true)
    List<Object[]> getSalesStatisticsByDistrict(String districtName);
    
    /**
     * 업종별 자치구별 매출 통계 조회
     * 
     * @param serviceCategoryName 서비스 업종명
     * @return 업종별 자치구별 매출 통계
     */
    @Query(value = "SELECT s.자치구_코드_명, SUM(s.당월_매출_금액) as totalAmount, SUM(s.당월_매출_건수) as totalCount " +
           "FROM sales_data s WHERE s.서비스_업종_코드_명 = ?1 " +
           "GROUP BY s.자치구_코드_명 ORDER BY totalAmount DESC", nativeQuery = true)
    List<Object[]> getSalesStatisticsByServiceCategory(String serviceCategoryName);
    
    /**
     * 자치구별 성별 매출 통계 조회
     * 
     * @param districtName 자치구명
     * @return 자치구별 성별 매출 통계
     */
    @Query(value = "SELECT SUM(s.남성_매출_금액) as maleAmount, SUM(s.여성_매출_금액) as femaleAmount, " +
           "SUM(s.남성_매출_건수) as maleCount, SUM(s.여성_매출_건수) as femaleCount " +
           "FROM sales_data s WHERE s.자치구_코드_명 = ?1", nativeQuery = true)
    Object[] getGenderSalesStatisticsByDistrict(String districtName);
    
    /**
     * 자치구별 주중/주말 매출 통계 조회
     * 
     * @param districtName 자치구명
     * @return 자치구별 주중/주말 매출 통계
     */
    @Query(value = "SELECT SUM(s.주중_매출_금액) as weekdayAmount, SUM(s.주말_매출_금액) as weekendAmount, " +
           "SUM(s.주중_매출_건수) as weekdayCount, SUM(s.주말_매출_건수) as weekendCount " +
           "FROM sales_data s WHERE s.자치구_코드_명 = ?1", nativeQuery = true)
    Object[] getWeekdayWeekendSalesStatisticsByDistrict(String districtName);
    
    /**
     * 전체 자치구 매출 순위 조회 (상위 N개)
     * 
     * @param limit 조회할 개수
     * @return 매출 순위별 자치구 목록
     */
    @Query(value = "SELECT s.자치구_코드_명, SUM(s.당월_매출_금액) as totalAmount, SUM(s.당월_매출_건수) as totalCount " +
           "FROM sales_data s GROUP BY s.자치구_코드_명 ORDER BY totalAmount DESC LIMIT ?1", nativeQuery = true)
    List<Object[]> getTopDistrictsBySales(int limit);
    
    /**
     * 전체 업종 매출 순위 조회 (상위 N개)
     * 
     * @param limit 조회할 개수
     * @return 매출 순위별 업종 목록
     */
    @Query(value = "SELECT s.서비스_업종_코드_명, SUM(s.당월_매출_금액) as totalAmount, SUM(s.당월_매출_건수) as totalCount " +
           "FROM sales_data s GROUP BY s.서비스_업종_코드_명 ORDER BY totalAmount DESC LIMIT ?1", nativeQuery = true)
    List<Object[]> getTopServiceCategoriesBySales(int limit);
    
    /**
     * 업종별 월별 평균 매출 통계 조회
     * 
     * @return 업종별 월별 평균 매출 통계
     */
    @Query(value = "SELECT s.서비스_업종_코드_명, s.기준_년월_코드, " +
           "AVG(s.당월_매출_금액) as avgAmount, AVG(s.당월_매출_건수) as avgCount " +
           "FROM sales_data s " +
           "GROUP BY s.서비스_업종_코드_명, s.기준_년월_코드 " +
           "ORDER BY s.서비스_업종_코드_명, s.기준_년월_코드", nativeQuery = true)
    List<Object[]> getMonthlyAverageSalesByCategory();
    
    /**
     * 자치구별 업종별 월별 평균 매출 통계 조회
     * 
     * @param districtName 자치구명
     * @return 자치구별 업종별 월별 평균 매출 통계
     */
    @Query(value = "SELECT s.서비스_업종_코드_명, s.기준_년월_코드, " +
           "AVG(s.당월_매출_금액) as avgAmount, AVG(s.당월_매출_건수) as avgCount " +
           "FROM sales_data s WHERE s.자치구_코드_명 = ?1 " +
           "GROUP BY s.서비스_업종_코드_명, s.기준_년월_코드 " +
           "ORDER BY s.서비스_업종_코드_명, s.기준_년월_코드", nativeQuery = true)
    List<Object[]> getMonthlyAverageSalesByCategoryAndDistrict(String districtName);

    /**
     * 자치구별 월 평균 매출 조회
     * 
     * @param districtName 자치구명
     * @return 자치구별 월 평균 매출
     */
    @Query(value = "SELECT AVG(s.당월_매출_금액) as avgMonthlySales " +
           "FROM sales_data s " +
           "WHERE s.자치구_코드_명 = ?1", nativeQuery = true)
    Double getAverageMonthlySalesByDistrict(String districtName);

    /**
     * 자치구별 최근 날짜 사업체 수 조회
     * 
     * @param districtName 자치구명
     * @return 자치구별 최근 날짜 사업체 수
     */
    @Query(value = "SELECT COUNT(*) as recentBusinesses " +
           "FROM sales_data s " +
           "WHERE s.자치구_코드_명 = ?1 " +
           "AND s.기준_년월_코드 = (SELECT MAX(기준_년월_코드) FROM sales_data WHERE 자치구_코드_명 = ?1)", nativeQuery = true)
    Integer getRecentBusinessesByDistrict(String districtName);
}
