package com.smartpark.entity.dto;

import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 能耗查询 DTO
 */
@Data
public class EnergyQueryDTO implements Serializable {

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
