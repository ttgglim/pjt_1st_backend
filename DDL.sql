-- =====================================================
-- 서울시 상권 분석 시스템 DDL 스키마
-- =====================================================

-- 데이터베이스 생성 (필요시)
-- CREATE DATABASE seoul_commercial_district;

-- 기존 테이블 삭제 (순서 주의: 외래키 참조 순서)
DROP TABLE IF EXISTS sales_data CASCADE;
DROP TABLE IF EXISTS district_population_statistics CASCADE;
DROP TABLE IF EXISTS district_codes CASCADE;

-- =====================================================
-- 1. 자치구 코드 테이블
-- =====================================================
CREATE TABLE district_codes (
    id BIGSERIAL PRIMARY KEY,
    district_code VARCHAR(10) NOT NULL UNIQUE,
    district_name VARCHAR(50) NOT NULL
);

-- 자치구 코드 인덱스
CREATE INDEX idx_district_codes_code ON district_codes(district_code);
CREATE INDEX idx_district_codes_name ON district_codes(district_name);

-- =====================================================
-- 2. 자치구 인구 통계 테이블
-- =====================================================
CREATE TABLE district_population_statistics (
    id BIGSERIAL PRIMARY KEY,
    district_name VARCHAR(50) NOT NULL,
    total_population INTEGER NOT NULL,
    resident_population INTEGER NOT NULL,
    worker_population INTEGER NOT NULL,
    floating_population INTEGER NOT NULL,
    
    -- 연령대별 남성 인구
    age_0_9_male INTEGER NOT NULL,
    age_10_19_male INTEGER NOT NULL,
    age_20_29_male INTEGER NOT NULL,
    age_30_39_male INTEGER NOT NULL,
    age_40_49_male INTEGER NOT NULL,
    age_50_59_male INTEGER NOT NULL,
    age_60_plus_male INTEGER NOT NULL,
    
    -- 연령대별 여성 인구
    age_0_9_female INTEGER NOT NULL,
    age_10_19_female INTEGER NOT NULL,
    age_20_29_female INTEGER NOT NULL,
    age_30_39_female INTEGER NOT NULL,
    age_40_49_female INTEGER NOT NULL,
    age_50_59_female INTEGER NOT NULL,
    age_60_plus_female INTEGER NOT NULL,
    
    -- 타임스탬프
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- 인구 통계 인덱스
CREATE INDEX idx_population_district_name ON district_population_statistics(district_name);
CREATE INDEX idx_population_total ON district_population_statistics(total_population);
CREATE INDEX idx_population_created_at ON district_population_statistics(created_at);

-- =====================================================
-- 3. 매출 데이터 테이블
-- =====================================================
CREATE TABLE sales_data (
    id BIGSERIAL PRIMARY KEY,
    기준_년월_코드 VARCHAR(10),
    자치구_코드 INTEGER,
    자치구_코드_명 VARCHAR(20),
    서비스_업종_코드 VARCHAR(20),
    서비스_업종_코드_명 VARCHAR(50),
    당월_매출_금액 BIGINT,
    당월_매출_건수 INTEGER,
    주중_매출_금액 BIGINT,
    주말_매출_금액 BIGINT,
    남성_매출_금액 BIGINT,
    여성_매출_금액 BIGINT,
    주중_매출_건수 INTEGER,
    주말_매출_건수 INTEGER,
    남성_매출_건수 INTEGER,
    여성_매출_건수 INTEGER
);

-- 매출 데이터 인덱스
CREATE INDEX idx_sales_year_month ON sales_data(기준_년월_코드);
CREATE INDEX idx_sales_district_code ON sales_data(자치구_코드);
CREATE INDEX idx_sales_district_name ON sales_data(자치구_코드_명);
CREATE INDEX idx_sales_service_category ON sales_data(서비스_업종_코드);
CREATE INDEX idx_sales_amount ON sales_data(당월_매출_금액);

-- =====================================================
-- 4. 샘플 데이터 삽입 (자치구 코드)
-- =====================================================
INSERT INTO district_codes (district_code, district_name) VALUES
('11110', '종로구'),
('11140', '중구'),
('11170', '용산구'),
('11200', '성동구'),
('11215', '광진구'),
('11230', '동대문구'),
('11260', '중랑구'),
('11290', '성북구'),
('11305', '강북구'),
('11320', '도봉구'),
('11350', '노원구'),
('11380', '은평구'),
('11410', '서대문구'),
('11440', '마포구'),
('11470', '양천구'),
('11500', '강서구'),
('11530', '구로구'),
('11545', '금천구'),
('11560', '영등포구'),
('11590', '동작구'),
('11620', '관악구'),
('11650', '서초구'),
('11680', '강남구'),
('11710', '송파구'),
('11740', '강동구');

-- =====================================================
-- 5. 뷰 생성 (자주 사용되는 쿼리를 위한 뷰)
-- =====================================================

-- 자치구별 총 매출 뷰
CREATE OR REPLACE VIEW district_total_sales AS
SELECT 
    자치구_코드,
    자치구_코드_명,
    기준_년월_코드,
    SUM(당월_매출_금액) as 총_매출_금액,
    SUM(당월_매출_건수) as 총_매출_건수,
    COUNT(*) as 업종_수
FROM sales_data
GROUP BY 자치구_코드, 자치구_코드_명, 기준_년월_코드;

-- 자치구별 인구 및 매출 통합 뷰
CREATE OR REPLACE VIEW district_population_sales AS
SELECT 
    d.district_name,
    p.total_population,
    p.resident_population,
    p.worker_population,
    p.floating_population,
    s.총_매출_금액,
    s.총_매출_건수,
    CASE 
        WHEN p.total_population > 0 
        THEN ROUND(s.총_매출_금액::NUMERIC / p.total_population, 2)
        ELSE 0 
    END as 인구당_매출액
FROM district_population_statistics p
LEFT JOIN district_total_sales s ON p.district_name = s.자치구_코드_명
LEFT JOIN district_codes d ON p.district_name = d.district_name;

-- =====================================================
-- 6. 권한 설정 (필요시)
-- =====================================================
-- GRANT ALL PRIVILEGES ON ALL TABLES IN SCHEMA public TO your_user;
-- GRANT ALL PRIVILEGES ON ALL SEQUENCES IN SCHEMA public TO your_user;

-- =====================================================
-- 7. 테이블 생성 확인
-- =====================================================
SELECT 
    table_name, 
    column_count,
    row_count
FROM (
    SELECT 'district_codes' as table_name, COUNT(*) as column_count, 0 as row_count FROM information_schema.columns WHERE table_name = 'district_codes'
    UNION ALL
    SELECT 'district_population_statistics' as table_name, COUNT(*) as column_count, 0 as row_count FROM information_schema.columns WHERE table_name = 'district_population_statistics'
    UNION ALL
    SELECT 'sales_data' as table_name, COUNT(*) as column_count, 0 as row_count FROM information_schema.columns WHERE table_name = 'sales_data'
) t;

-- 자치구 코드 데이터 확인
SELECT COUNT(*) as district_codes_count FROM district_codes;
