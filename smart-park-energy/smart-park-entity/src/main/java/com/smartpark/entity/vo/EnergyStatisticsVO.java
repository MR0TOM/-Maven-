package com.smartpark.entity.vo;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * 能耗统计 VO
 */
@Data
public class EnergyStatisticsVO implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 能耗类型
     */
    private Integer energyType;

    /**
     * 能耗类型名称
     */
    private String energyTypeName;

    /**
     * 今日能耗
     */
    private BigDecimal todayConsumption;

    /**
     * 昨日能耗
     */
    private BigDecimal yesterdayConsumption;

    /**
     * 本月能耗
     */
    private BigDecimal monthConsumption;

    /**
     * 本年能耗
     */
    private BigDecimal yearConsumption;

    /**
     * 同比变化率
     */
    private BigDecimal yoyRate;

    /**
     * 环比变化率
     */
    private BigDecimal momRate;
}
