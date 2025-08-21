package com.kt.seoul.commercialdistrict.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 서울시 25개 자치구 코드 Entity
 * 
 * 자치구 코드와 자치구명을 매핑하여 저장합니다.
 * 
 * @author KT 개발팀
 * @version 1.0.0
 */
@Entity
@Table(name = "district_codes")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DistrictCode {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    
    /**
     * 자치구 코드 (예: 11110, 11140 등)
     */
    @Column(name = "district_code", nullable = false, unique = true, length = 10)
    private String districtCode;
    
    /**
     * 자치구명 (예: 종로구, 중구 등)
     */
    @Column(name = "district_name", nullable = false, length = 50)
    private String districtName;
}
