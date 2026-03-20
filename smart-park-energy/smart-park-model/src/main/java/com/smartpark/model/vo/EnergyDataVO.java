package com.smartpark.model.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Schema(description = "能耗数据VO")
public class EnergyDataVO implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Schema(description = "数据ID")
    private Long id;

    @Schema(description = "设备ID")
    private Long deviceId;

    @Schema(description = "设备名称")
    private String deviceName;

    @Schema(description = "建筑名称")
    private String buildingName;

    @Schema(description = "采集时间")
    private LocalDateTime collectTime;

    @Schema(description = "当前值")
    private BigDecimal currentValue;

    @Schema(description = "累计值")
    private BigDecimal totalValue;

    @Schema(description = "功率")
    private BigDecimal power;

    @Schema(description = "电压")
    private BigDecimal voltage;

    @Schema(description = "电流")
    private BigDecimal current;

    @Schema(description = "功率因数")
    private BigDecimal powerFactor;

    @Schema(description = "能源类型")
    private String energyType;

    @Schema(description = "单位")
    private String unit;

    @Schema(description = "数据质量")
    private String dataQuality;

    @Schema(description = "状态")
    private String status;
}
