package com.kt.seoul.commercialdistrict.dto;

import com.kt.seoul.commercialdistrict.entity.DistrictPopulationStatistics;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 자치구 인구 통계 응답 DTO
 * 
 * @author KT 개발팀
 * @version 1.0.0
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DistrictPopulationResponse {
    
    private Long id;
    private String districtName;
    private Integer totalPopulation;
    private Integer residentPopulation;
    private Integer workerPopulation;
    private Integer floatingPopulation;
    
    // 연령대별 남성 인구
    private Integer age0To9Male;
    private Integer age10To19Male;
    private Integer age20To29Male;
    private Integer age30To39Male;
    private Integer age40To49Male;
    private Integer age50To59Male;
    private Integer age60PlusMale;
    
    // 연령대별 여성 인구
    private Integer age0To9Female;
    private Integer age10To19Female;
    private Integer age20To29Female;
    private Integer age30To39Female;
    private Integer age40To49Female;
    private Integer age50To59Female;
    private Integer age60PlusFemale;
    
    private String createdAt;
    private String updatedAt;
    
    /**
     * Entity를 DTO로 변환
     * 
     * @param entity DistrictPopulationStatistics Entity
     * @return DistrictPopulationResponse DTO
     */
    public static DistrictPopulationResponse from(DistrictPopulationStatistics entity) {
        return DistrictPopulationResponse.builder()
                .id(entity.getId())
                .districtName(entity.getDistrictName())
                .totalPopulation(entity.getTotalPopulation())
                .residentPopulation(entity.getResidentPopulation())
                .workerPopulation(entity.getWorkerPopulation())
                .floatingPopulation(entity.getFloatingPopulation())
                .age0To9Male(entity.getAge0To9Male())
                .age10To19Male(entity.getAge10To19Male())
                .age20To29Male(entity.getAge20To29Male())
                .age30To39Male(entity.getAge30To39Male())
                .age40To49Male(entity.getAge40To49Male())
                .age50To59Male(entity.getAge50To59Male())
                .age60PlusMale(entity.getAge60PlusMale())
                .age0To9Female(entity.getAge0To9Female())
                .age10To19Female(entity.getAge10To19Female())
                .age20To29Female(entity.getAge20To29Female())
                .age30To39Female(entity.getAge30To39Female())
                .age40To49Female(entity.getAge40To49Female())
                .age50To59Female(entity.getAge50To59Female())
                .age60PlusFemale(entity.getAge60PlusFemale())
                .createdAt(entity.getCreatedAt().toString())
                .updatedAt(entity.getUpdatedAt() != null ? entity.getUpdatedAt().toString() : null)
                .build();
    }
}
