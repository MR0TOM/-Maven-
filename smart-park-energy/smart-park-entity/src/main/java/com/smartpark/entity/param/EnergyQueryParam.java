package com.smartpark.entity.param;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDateTime;

/**
 * 能耗查询参数
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class EnergyQueryParam extends PageParam {

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
     * 开始时间
     */
    private LocalDateTime startTime;

    /**
     * 结束时间
     */
    private LocalDateTime endTime;

    /**
     * 区域编码
     */
    private String areaCode;
}
