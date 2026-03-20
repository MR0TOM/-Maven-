package com.smartpark.entity.dto;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * 能耗报表 DTO
 */
@Data
public class EnergyReportDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 统计日期
     */
    private LocalDate statDate;

    /**
     * 区域编码
     */
    private String areaCode;

    /**
     * 能耗类型
     */
    private Integer energyType;

    /**
     * 总能耗
     */
    private BigDecimal totalConsumption;

    /**
     * 峰值能耗
     */
    private BigDecimal peakConsumption;

    /**
     * 谷值能耗
     */
    private BigDecimal valleyConsumption;

    /**
     * 平均能耗
     */
    private BigDecimal avgConsumption;
}
