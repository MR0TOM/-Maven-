package com.smartpark.entity.model;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * 能耗记录实体
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("energy_record")
public class EnergyRecord extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 设备ID
     */
    private Long meterId;

    /**
     * 能耗类型
     */
    private Integer energyType;

    /**
     * 读数
     */
    private BigDecimal reading;

    /**
     * 能耗值
     */
    private BigDecimal consumption;

    /**
     * 数据时间
     */
    private LocalDateTime dataTime;

    /**
     * 数据状态
     */
    private Integer dataStatus;
}
