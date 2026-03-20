package com.smartpark.model.vo;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * 能耗数据视图对象
 */
@Data
public class EnergyDataVO implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键ID
     */
    private Long id;

    /**
     * 设备ID
     */
    private String deviceId;

    /**
     * 设备名称
     */
    private String deviceName;

    /**
     * 园区ID
     */
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
    private Integer energyType;

    /**
     * 能源类型名称
     */
    private String energyTypeName;

    /**
     * 能耗值
     */
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
     * 状态名称
     */
    private String statusName;

    /**
     * 备注
     */
    private String remark;

    /**
     * 创建时间
     */
    private LocalDateTime createTime;
}
