-- 매출 데이터 테이블 생성
DROP TABLE IF EXISTS sales_data;

CREATE TABLE sales_data (
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

-- CSV 데이터 삽입
\COPY sales_data FROM '/tmp/dummy_sales_data.csv' CSV HEADER ENCODING 'UTF8';

-- 테이블 생성 확인
SELECT COUNT(*) FROM sales_data;
