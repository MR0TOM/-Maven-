package com.smartpark.model.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * 能耗数据传输对象
 */
@Data
public class EnergyDataDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 设备ID
     */
    @NotBlank(message = "设备ID不能为空")
    private String deviceId;

    /**
     * 设备名称
     */
    private String deviceName;

    /**
     * 园区ID
     */
    @NotNull(message = "园区ID不能为空")
    private Long parkId;

    /**
     * 园区名称
     */
    private String parkName;

    /**
     * 建筑ID
     */
    private Long buildingId;

    /**
     * 建筑名称
     */
    private String buildingName;

    /**
     * 楼层
     */
    private Integer floor;

    /**
     * 能源类型(1:电力,2:水,3:燃气,4:热力,5:冷气)
     */
    @NotNull(message = "能源类型不能为空")
    private Integer energyType;

    /**
     * 能耗值
     */
    @NotNull(message = "能耗值不能为空")
    private BigDecimal energyValue;

    /**
     * 累计能耗值
     */
    private BigDecimal totalEnergy;

    /**
     * 数据采集时间
     */
    private LocalDateTime collectTime;

    /**
     * 数据状态(0:正常,1:异常)
     */
    private Integer status;

    /**
     * 备注
     */
    private String remark;
}
