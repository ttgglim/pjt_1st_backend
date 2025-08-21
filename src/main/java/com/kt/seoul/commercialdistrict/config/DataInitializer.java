package com.kt.seoul.commercialdistrict.config;

import com.kt.seoul.commercialdistrict.entity.DistrictPopulationStatistics;
import com.kt.seoul.commercialdistrict.entity.DistrictCode;
import com.kt.seoul.commercialdistrict.repository.DistrictPopulationStatisticsRepository;
import com.kt.seoul.commercialdistrict.repository.DistrictCodeRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;

/**
 * 서울시 25개 자치구 더미 데이터 초기화 컴포넌트
 * 
 * @author KT 개발팀
 * @version 1.0.0
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class DataInitializer implements CommandLineRunner {
    
    private final DistrictPopulationStatisticsRepository repository;
    private final DistrictCodeRepository districtCodeRepository;
    
    @Override
    @Transactional
    public void run(String... args) throws Exception {
        log.info("서울시 25개 자치구 데이터 초기화 시작");
        
        // 자치구 코드 데이터 초기화
        initializeDistrictCodes();
        
        // 인구 통계 데이터 초기화
        initializePopulationStatistics();
        
        log.info("서울시 25개 자치구 데이터 초기화 완료");
    }
    
    /**
     * 자치구 코드 데이터 초기화
     */
    private void initializeDistrictCodes() {
        log.info("자치구 코드 데이터 초기화 시작");
        
        // 기존 데이터가 있으면 초기화하지 않음
        if (districtCodeRepository.count() > 0) {
            log.info("기존 자치구 코드 데이터가 존재하여 초기화를 건너뜁니다. (총 {}개 레코드)", districtCodeRepository.count());
            return;
        }
        
        List<DistrictCode> districtCodes = createDistrictCodeData();
        districtCodeRepository.saveAll(districtCodes);
        
        log.info("자치구 코드 데이터 초기화 완료 (총 {}개 레코드)", districtCodes.size());
    }
    
    /**
     * 인구 통계 데이터 초기화
     */
    private void initializePopulationStatistics() {
        log.info("인구 통계 데이터 초기화 시작");
        
        // 기존 데이터가 있으면 초기화하지 않음
        if (repository.count() > 0) {
            log.info("기존 인구 통계 데이터가 존재하여 초기화를 건너뜁니다. (총 {}개 레코드)", repository.count());
            return;
        }
        
        List<DistrictPopulationStatistics> districts = createDummyData();
        repository.saveAll(districts);
        
        log.info("인구 통계 데이터 초기화 완료 (총 {}개 레코드)", districts.size());
    }
    
    /**
     * 서울시 25개 자치구 코드 데이터 생성
     * 
     * @return 자치구 코드 데이터 목록
     */
    private List<DistrictCode> createDistrictCodeData() {
        return Arrays.asList(
            createDistrictCode("11110", "종로구"),
            createDistrictCode("11140", "중구"),
            createDistrictCode("11170", "용산구"),
            createDistrictCode("11200", "성동구"),
            createDistrictCode("11215", "광진구"),
            createDistrictCode("11230", "동대문구"),
            createDistrictCode("11260", "중랑구"),
            createDistrictCode("11290", "성북구"),
            createDistrictCode("11305", "강북구"),
            createDistrictCode("11320", "도봉구"),
            createDistrictCode("11350", "노원구"),
            createDistrictCode("11410", "은평구"),
            createDistrictCode("11440", "서대문구"),
            createDistrictCode("11470", "마포구"),
            createDistrictCode("11500", "양천구"),
            createDistrictCode("11530", "강서구"),
            createDistrictCode("11545", "구로구"),
            createDistrictCode("11560", "금천구"),
            createDistrictCode("11590", "영등포구"),
            createDistrictCode("11620", "동작구"),
            createDistrictCode("11650", "관악구"),
            createDistrictCode("11680", "서초구"),
            createDistrictCode("11710", "강남구"),
            createDistrictCode("11740", "송파구"),
            createDistrictCode("11770", "강동구")
        );
    }
    
    /**
     * 자치구 코드 데이터 생성 헬퍼 메서드
     */
    private DistrictCode createDistrictCode(String districtCode, String districtName) {
        return DistrictCode.builder()
                .districtCode(districtCode)
                .districtName(districtName)
                .build();
    }
    
    /**
     * 서울시 25개 자치구 더미 데이터 생성
     * 
     * @return 자치구 인구 통계 데이터 목록
     */
    private List<DistrictPopulationStatistics> createDummyData() {
        return Arrays.asList(
            // 강남구 - 가장 인구 많은 구
            createDistrict("강남구", 550000, 520000, 800000, 1200000,
                25000, 30000, 80000, 90000, 85000, 70000, 35000,
                23000, 28000, 75000, 85000, 80000, 65000, 32000),
            
            // 송파구
            createDistrict("송파구", 680000, 650000, 450000, 800000,
                32000, 38000, 95000, 110000, 105000, 90000, 45000,
                30000, 35000, 90000, 105000, 100000, 85000, 42000),
            
            // 강서구
            createDistrict("강서구", 590000, 570000, 350000, 600000,
                28000, 33000, 85000, 95000, 90000, 75000, 38000,
                26000, 31000, 80000, 90000, 85000, 70000, 35000),
            
            // 마포구
            createDistrict("마포구", 380000, 360000, 280000, 500000,
                18000, 22000, 55000, 65000, 60000, 50000, 25000,
                17000, 20000, 52000, 62000, 57000, 47000, 23000),
            
            // 서초구
            createDistrict("서초구", 430000, 410000, 320000, 550000,
                20000, 24000, 60000, 70000, 65000, 55000, 28000,
                19000, 22000, 57000, 67000, 62000, 52000, 26000),
            
            // 영등포구
            createDistrict("영등포구", 400000, 380000, 300000, 520000,
                19000, 23000, 58000, 68000, 63000, 53000, 27000,
                18000, 21000, 55000, 65000, 60000, 50000, 25000),
            
            // 성동구
            createDistrict("성동구", 290000, 270000, 220000, 380000,
                14000, 17000, 42000, 50000, 47000, 39000, 20000,
                13000, 16000, 40000, 48000, 45000, 37000, 19000),
            
            // 광진구
            createDistrict("광진구", 350000, 330000, 250000, 420000,
                17000, 20000, 50000, 58000, 55000, 46000, 23000,
                16000, 19000, 48000, 56000, 53000, 44000, 22000),
            
            // 용산구
            createDistrict("용산구", 220000, 200000, 180000, 300000,
                11000, 13000, 32000, 38000, 36000, 30000, 15000,
                10000, 12000, 30000, 36000, 34000, 28000, 14000),
            
            // 중구
            createDistrict("중구", 130000, 110000, 150000, 250000,
                6000, 7000, 19000, 22000, 21000, 18000, 9000,
                6000, 7000, 18000, 21000, 20000, 17000, 8000),
            
            // 종로구
            createDistrict("종로구", 150000, 130000, 120000, 220000,
                7000, 8000, 22000, 26000, 25000, 21000, 11000,
                7000, 8000, 21000, 25000, 24000, 20000, 10000),
            
            // 중랑구
            createDistrict("중랑구", 400000, 380000, 200000, 350000,
                19000, 23000, 58000, 68000, 63000, 53000, 27000,
                18000, 21000, 55000, 65000, 60000, 50000, 25000),
            
            // 동대문구
            createDistrict("동대문구", 350000, 330000, 180000, 320000,
                17000, 20000, 50000, 58000, 55000, 46000, 23000,
                16000, 19000, 48000, 56000, 53000, 44000, 22000),
            
            // 성북구
            createDistrict("성북구", 450000, 430000, 250000, 400000,
                22000, 26000, 65000, 75000, 70000, 58000, 29000,
                21000, 24000, 62000, 72000, 67000, 55000, 27000),
            
            // 강북구
            createDistrict("강북구", 320000, 300000, 150000, 280000,
                15000, 18000, 45000, 52000, 49000, 41000, 21000,
                14000, 17000, 43000, 50000, 47000, 39000, 20000),
            
            // 도봉구
            createDistrict("도봉구", 330000, 310000, 120000, 250000,
                16000, 19000, 47000, 54000, 51000, 43000, 22000,
                15000, 18000, 45000, 52000, 49000, 41000, 21000),
            
            // 노원구
            createDistrict("노원구", 550000, 530000, 200000, 380000,
                26000, 31000, 78000, 90000, 85000, 70000, 35000,
                25000, 29000, 75000, 87000, 82000, 67000, 33000),
            
            // 은평구
            createDistrict("은평구", 480000, 460000, 180000, 320000,
                23000, 27000, 70000, 80000, 75000, 62000, 31000,
                22000, 25000, 67000, 77000, 72000, 59000, 29000),
            
            // 서대문구
            createDistrict("서대문구", 310000, 290000, 200000, 350000,
                15000, 18000, 45000, 52000, 49000, 41000, 21000,
                14000, 17000, 43000, 50000, 47000, 39000, 20000),
            
            // 마포구 (중복 제거 - 실제로는 24개 구)
            createDistrict("양천구", 470000, 450000, 220000, 380000,
                22000, 26000, 68000, 78000, 73000, 60000, 30000,
                21000, 24000, 65000, 75000, 70000, 57000, 28000),
            
            // 구로구
            createDistrict("구로구", 420000, 400000, 280000, 450000,
                20000, 24000, 60000, 70000, 65000, 55000, 28000,
                19000, 22000, 57000, 67000, 62000, 52000, 26000),
            
            // 금천구
            createDistrict("금천구", 250000, 230000, 150000, 280000,
                12000, 14000, 35000, 41000, 39000, 32000, 16000,
                11000, 13000, 33000, 39000, 37000, 30000, 15000),
            
            // 동작구
            createDistrict("동작구", 400000, 380000, 200000, 350000,
                19000, 23000, 58000, 68000, 63000, 53000, 27000,
                18000, 21000, 55000, 65000, 60000, 50000, 25000),
            
            // 관악구
            createDistrict("관악구", 520000, 500000, 180000, 320000,
                25000, 30000, 75000, 85000, 80000, 65000, 33000,
                24000, 28000, 72000, 82000, 77000, 62000, 31000),
            
            // 강동구
            createDistrict("강동구", 430000, 410000, 180000, 320000,
                20000, 24000, 60000, 70000, 65000, 55000, 28000,
                19000, 22000, 57000, 67000, 62000, 52000, 26000)
        );
    }
    
    /**
     * 자치구 데이터 생성 헬퍼 메서드
     */
    private DistrictPopulationStatistics createDistrict(
            String districtName,
            Integer totalPopulation,
            Integer residentPopulation,
            Integer workerPopulation,
            Integer floatingPopulation,
            // 남성 연령대별 인구
            Integer age0To9Male, Integer age10To19Male, Integer age20To29Male,
            Integer age30To39Male, Integer age40To49Male, Integer age50To59Male, Integer age60PlusMale,
            // 여성 연령대별 인구
            Integer age0To9Female, Integer age10To19Female, Integer age20To29Female,
            Integer age30To39Female, Integer age40To49Female, Integer age50To59Female, Integer age60PlusFemale) {
        
        return DistrictPopulationStatistics.builder()
                .districtName(districtName)
                .totalPopulation(totalPopulation)
                .residentPopulation(residentPopulation)
                .workerPopulation(workerPopulation)
                .floatingPopulation(floatingPopulation)
                .age0To9Male(age0To9Male)
                .age10To19Male(age10To19Male)
                .age20To29Male(age20To29Male)
                .age30To39Male(age30To39Male)
                .age40To49Male(age40To49Male)
                .age50To59Male(age50To59Male)
                .age60PlusMale(age60PlusMale)
                .age0To9Female(age0To9Female)
                .age10To19Female(age10To19Female)
                .age20To29Female(age20To29Female)
                .age30To39Female(age30To39Female)
                .age40To49Female(age40To49Female)
                .age50To59Female(age50To59Female)
                .age60PlusFemale(age60PlusFemale)
                .build();
    }
}
