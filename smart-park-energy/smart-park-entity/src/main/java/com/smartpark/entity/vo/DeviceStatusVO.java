package com.smartpark.entity.vo;

import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 设备状态 VO
 */
@Data
public class DeviceStatusVO implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 设备ID
     */
    private Long deviceId;

    /**
     * 设备编号
     */
    private String deviceCode;

    /**
     * 设备名称
     */
    private String deviceName;

    /**
     * 在线状态
     */
    private Integer onlineStatus;

    /**
     * 设备状态
     */
    private Integer deviceStatus;

    /**
     * 最后通信时间
     */
    private LocalDateTime lastCommTime;

    /**
     * 运行时长（小时）
     */
    private Long runningHours;
}
