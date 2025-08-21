package com.kt.seoul.commercialdistrict.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 서울시 25개 자치구 인구 통계 Entity
 * 
 * 총 인구수, 상주인구, 직장인구, 유동인구와 
 * 연령대별/성별 인구 데이터를 저장합니다.
 * 
 * @author KT 개발팀
 * @version 1.0.0
 */
@Entity
@Table(name = "district_population_statistics")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DistrictPopulationStatistics {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    
    /**
     * 자치구명 (예: 강남구, 강동구, 강북구 등)
     */
    @Column(name = "district_name", nullable = false, length = 50)
    private String districtName;
    
    /**
     * 총 인구수
     */
    @Column(name = "total_population", nullable = false)
    private Integer totalPopulation;
    
    /**
     * 상주인구 (해당 지역에 거주하는 인구)
     */
    @Column(name = "resident_population", nullable = false)
    private Integer residentPopulation;
    
    /**
     * 직장인구 (해당 지역에서 근무하는 인구)
     */
    @Column(name = "worker_population", nullable = false)
    private Integer workerPopulation;
    
    /**
     * 유동인구 (해당 지역을 방문하는 인구)
     */
    @Column(name = "floating_population", nullable = false)
    private Integer floatingPopulation;
    
    // 연령대별 남성 인구
    @Column(name = "age_0_9_male", nullable = false)
    private Integer age0To9Male;
    
    @Column(name = "age_10_19_male", nullable = false)
    private Integer age10To19Male;
    
    @Column(name = "age_20_29_male", nullable = false)
    private Integer age20To29Male;
    
    @Column(name = "age_30_39_male", nullable = false)
    private Integer age30To39Male;
    
    @Column(name = "age_40_49_male", nullable = false)
    private Integer age40To49Male;
    
    @Column(name = "age_50_59_male", nullable = false)
    private Integer age50To59Male;
    
    @Column(name = "age_60_plus_male", nullable = false)
    private Integer age60PlusMale;
    
    // 연령대별 여성 인구
    @Column(name = "age_0_9_female", nullable = false)
    private Integer age0To9Female;
    
    @Column(name = "age_10_19_female", nullable = false)
    private Integer age10To19Female;
    
    @Column(name = "age_20_29_female", nullable = false)
    private Integer age20To29Female;
    
    @Column(name = "age_30_39_female", nullable = false)
    private Integer age30To39Female;
    
    @Column(name = "age_40_49_female", nullable = false)
    private Integer age40To49Female;
    
    @Column(name = "age_50_59_female", nullable = false)
    private Integer age50To59Female;
    
    @Column(name = "age_60_plus_female", nullable = false)
    private Integer age60PlusFemale;
    
    /**
     * 데이터 생성 시간
     */
    @Column(name = "created_at", nullable = false)
    private java.time.LocalDateTime createdAt;
    
    /**
     * 데이터 업데이트 시간
     */
    @Column(name = "updated_at")
    private java.time.LocalDateTime updatedAt;
    
    @PrePersist
    protected void onCreate() {
        createdAt = java.time.LocalDateTime.now();
        updatedAt = java.time.LocalDateTime.now();
    }
    
    @PreUpdate
    protected void onUpdate() {
        updatedAt = java.time.LocalDateTime.now();
    }
}
