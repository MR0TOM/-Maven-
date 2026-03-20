package com.smartpark.model.bo;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * 能耗统计业务对象
 */
@Data
public class EnergyStatisticsBO implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 园区ID
     */
    private Long parkId;

    /**
     * 园区名称
     */
    private String parkName;

    /**
     * 能源类型
     */
    private Integer energyType;

    /**
     * 能源类型名称
     */
    private String energyTypeName;

    /**
     * 统计开始日期
     */
    private LocalDate startDate;

    /**
     * 统计结束日期
     */
    private LocalDate endDate;

    /**
     * 当前周期总能耗
     */
    private BigDecimal currentTotalEnergy;

    /**
     * 同比周期总能耗
     */
    private BigDecimal lastYearTotalEnergy;

    /**
     * 环比周期总能耗
     */
    private BigDecimal lastMonthTotalEnergy;

    /**
     * 同比增长率(%)
     */
    private BigDecimal yearOnYearRate;

    /**
     * 环比增长率(%)
     */
    private BigDecimal monthOnMonthRate;

    /**
     * 日平均能耗
     */
    private BigDecimal dailyAverageEnergy;

    /**
     * 最高日能耗
     */
    private BigDecimal maxDailyEnergy;

    /**
     * 最低日能耗
     */
    private BigDecimal minDailyEnergy;

    /**
     * 能耗峰值
     */
    private BigDecimal peakEnergy;

    /**
     * 能耗谷值
     */
    private BigDecimal valleyEnergy;

    /**
     * 统计时间
     */
    private LocalDate statisticsTime;
}
