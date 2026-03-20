package com.smartpark.model.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@Schema(description = "能耗统计VO")
public class EnergyStatisticsVO implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Schema(description = "统计ID")
    private Long id;

    @Schema(description = "建筑ID")
    private Long buildingId;

    @Schema(description = "建筑名称")
    private String buildingName;

    @Schema(description = "设备ID")
    private Long deviceId;

    @Schema(description = "设备名称")
    private String deviceName;

    @Schema(description = "统计日期")
    private LocalDate statisticsDate;

    @Schema(description = "统计类型")
    private String statisticsType;

    @Schema(description = "能源类型")
    private String energyType;

    @Schema(description = "总能耗")
    private BigDecimal totalConsumption;

    @Schema(description = "峰时能耗")
    private BigDecimal peakConsumption;

    @Schema(description = "谷时能耗")
    private BigDecimal valleyConsumption;

    @Schema(description = "平时能耗")
    private BigDecimal flatConsumption;

    @Schema(description = "最大功率")
    private BigDecimal maxPower;

    @Schema(description = "平均功率")
    private BigDecimal avgPower;

    @Schema(description = "费用")
    private BigDecimal cost;

    @Schema(description = "单位")
    private String unit;

    @Schema(description = "同比变化率")
    private BigDecimal yoyChangeRate;

    @Schema(description = "环比变化率")
    private BigDecimal momChangeRate;
}
