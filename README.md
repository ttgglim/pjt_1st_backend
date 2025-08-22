# 서울시 상권 분석 서비스 백엔드

서울시 25개 자치구의 인구 통계 데이터를 제공하는 REST API 서비스입니다.

## 🚀 기술 스택

- **Backend**: Spring Boot 3.2.0 + Java 21
- **Build Tool**: Gradle 8.5
- **Database**: PostgreSQL 15 (Docker)
- **ORM**: Spring Data JPA + Hibernate
- **Documentation**: Lombok

## 📋 프로젝트 구조

```
backend/
├── src/main/java/com/kt/seoul/commercialdistrict/
│   ├── SeoulCommercialDistrictApplication.java    # 메인 애플리케이션
│   ├── controller/
│   │   └── DistrictPopulationController.java      # REST API 컨트롤러
│   ├── service/
│   │   └── DistrictPopulationService.java         # 비즈니스 로직
│   ├── repository/
│   │   └── DistrictPopulationStatisticsRepository.java  # 데이터 접근 계층
│   ├── entity/
│   │   └── DistrictPopulationStatistics.java      # JPA 엔티티
│   ├── dto/
│   │   └── DistrictPopulationResponse.java        # 응답 DTO
│   └── config/
│       └── DataInitializer.java                   # 더미 데이터 초기화
├── src/main/resources/
│   └── application.yml                            # 애플리케이션 설정
├── build.gradle                                   # Gradle 빌드 설정
└── README.md                                      # 프로젝트 문서
```

## 🗄️ 데이터베이스 스키마

### district_population_statistics 테이블

| 컬럼명 | 타입 | 설명 |
|--------|------|------|
| id | BIGINT | 기본키 (자동 증가) |
| district_name | VARCHAR(50) | 자치구명 |
| total_population | INTEGER | 총 인구수 |
| resident_population | INTEGER | 상주인구 |
| worker_population | INTEGER | 직장인구 |
| floating_population | INTEGER | 유동인구 |
| age_0_9_male | INTEGER | 0-9세 남성 인구 |
| age_10_19_male | INTEGER | 10-19세 남성 인구 |
| age_20_29_male | INTEGER | 20-29세 남성 인구 |
| age_30_39_male | INTEGER | 30-39세 남성 인구 |
| age_40_49_male | INTEGER | 40-49세 남성 인구 |
| age_50_59_male | INTEGER | 50-59세 남성 인구 |
| age_60_plus_male | INTEGER | 60세 이상 남성 인구 |
| age_0_9_female | INTEGER | 0-9세 여성 인구 |
| age_10_19_female | INTEGER | 10-19세 여성 인구 |
| age_20_29_female | INTEGER | 20-29세 여성 인구 |
| age_30_39_female | INTEGER | 30-39세 여성 인구 |
| age_40_49_female | INTEGER | 40-49세 여성 인구 |
| age_50_59_female | INTEGER | 50-59세 여성 인구 |
| age_60_plus_female | INTEGER | 60세 이상 여성 인구 |
| created_at | TIMESTAMP | 생성 시간 |
| updated_at | TIMESTAMP | 수정 시간 |

## 🛠️ 설치 및 실행

### 1. PostgreSQL Docker 컨테이너 실행

```bash
docker run --name seoul-postgres \
  -e POSTGRES_PASSWORD=password \
  -e POSTGRES_DB=seoul_commercial_district \
  -p 5432:5432 \
  -d postgres:15
```

### 2. 애플리케이션 빌드 및 실행

```bash
# 프로젝트 빌드
./gradlew clean build

# 애플리케이션 실행
./gradlew bootRun
```

### 3. 애플리케이션 접속

- **서버 주소**: http://localhost:8080
- **API 기본 경로**: http://localhost:8080/api

## 📡 API 엔드포인트

### 1. 모든 자치구 인구 통계 조회
```
GET /api/districts
```

### 2. 특정 자치구 인구 통계 조회
```
GET /api/districts/{districtName}
```

### 3. 상위 N개 인구 많은 자치구 조회
```
GET /api/districts/top?limit=5
```

### 4. 최소 인구수 조건 자치구 조회
```
GET /api/districts/filter?minPopulation=500000
```

### 5. 자치구명 검색
```
GET /api/districts/search?keyword=강남
```

### 6. 서울시 전체 통계 정보 조회
```
GET /api/districts/statistics/summary
```

### 7. API 상태 확인
```
GET /api/districts/health
```

## 📊 더미 데이터

서울시 25개 자치구의 더미 데이터가 자동으로 생성됩니다:

1. **강남구** - 총 인구: 550,000명 (가장 많은 인구)
2. **송파구** - 총 인구: 680,000명
3. **강서구** - 총 인구: 590,000명
4. **마포구** - 총 인구: 380,000명
5. **서초구** - 총 인구: 430,000명
6. **영등포구** - 총 인구: 400,000명
7. **성동구** - 총 인구: 290,000명
8. **광진구** - 총 인구: 350,000명
9. **용산구** - 총 인구: 220,000명
10. **중구** - 총 인구: 130,000명
11. **종로구** - 총 인구: 150,000명
12. **중랑구** - 총 인구: 400,000명
13. **동대문구** - 총 인구: 350,000명
14. **성북구** - 총 인구: 450,000명
15. **강북구** - 총 인구: 320,000명
16. **도봉구** - 총 인구: 330,000명
17. **노원구** - 총 인구: 550,000명
18. **은평구** - 총 인구: 480,000명
19. **서대문구** - 총 인구: 310,000명
20. **양천구** - 총 인구: 470,000명
21. **구로구** - 총 인구: 420,000명
22. **금천구** - 총 인구: 250,000명
23. **동작구** - 총 인구: 400,000명
24. **관악구** - 총 인구: 520,000명
25. **강동구** - 총 인구: 430,000명

## 🔧 개발 환경 설정

### 필수 요구사항
- Java 21
- Docker
- Gradle 8.5 (Wrapper 포함)

### 환경 변수
- `DB_HOST`: localhost
- `DB_PORT`: 5432
- `DB_NAME`: seoul_commercial_district
- `DB_USERNAME`: postgres
- `DB_PASSWORD`: password

## 📝 API 응답 예시

### 자치구 목록 조회 응답
```json
[
  {
    "id": 1,
    "districtName": "강남구",
    "totalPopulation": 550000,
    "residentPopulation": 520000,
    "workerPopulation": 800000,
    "floatingPopulation": 1200000,
    "age0To9Male": 25000,
    "age10To19Male": 30000,
    "age20To29Male": 80000,
    "age30To39Male": 90000,
    "age40To49Male": 85000,
    "age50To59Male": 70000,
    "age60PlusMale": 35000,
    "age0To9Female": 23000,
    "age10To19Female": 28000,
    "age20To29Female": 75000,
    "age30To39Female": 85000,
    "age40To49Female": 80000,
    "age50To59Female": 65000,
    "age60PlusFemale": 32000,
    "createdAt": "2025-08-21T08:00:00",
    "updatedAt": "2025-08-21T08:00:00"
  }
]
```

### 서울시 통계 요약 응답
```json
{
  "totalDistricts": 25,
  "totalPopulation": 10500000,
  "averagePopulationPerDistrict": 420000
}
```

## 🚀 배포

### Docker 이미지 빌드
```bash
./gradlew bootBuildImage
```

### Docker 컨테이너 실행
```bash
docker run -p 8080:8080 seoul-commercial-district-api:1.0.0
```