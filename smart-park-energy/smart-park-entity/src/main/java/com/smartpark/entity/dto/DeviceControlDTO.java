package com.smartpark.entity.dto;

import lombok.Data;

import java.io.Serializable;

/**
 * 设备控制 DTO
 */
@Data
public class DeviceControlDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 设备ID
     */
    private Long deviceId;

    /**
     * 控制命令
     */
    private String command;

    /**
     * 命令参数
     */
    private String params;
}
