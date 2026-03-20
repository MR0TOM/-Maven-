package com.smartpark.model.do;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * 能耗数据实体类
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("energy_data")
public class EnergyDataDO extends BaseDO {

    private static final long serialVersionUID = 1L;

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
     * 备注
     */
    private String remark;
}
