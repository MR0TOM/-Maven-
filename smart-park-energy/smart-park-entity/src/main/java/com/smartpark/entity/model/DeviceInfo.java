package com.smartpark.entity.model;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDateTime;

/**
 * 设备信息实体
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("device_info")
public class DeviceInfo extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 设备编号
     */
    private String deviceCode;

    /**
     * 设备名称
     */
    private String deviceName;

    /**
     * 设备类型
     */
    private Integer deviceType;

    /**
     * 设备型号
     */
    private String deviceModel;

    /**
     * 厂商
     */
    private String manufacturer;

    /**
     * 安装日期
     */
    private LocalDateTime installDate;

    /**
     * 质保到期日
     */
    private LocalDateTime warrantyDate;

    /**
     * 设备状态
     */
    private Integer status;

    /**
     * 在线状态
     */
    private Integer onlineStatus;

    /**
     * 最后通信时间
     */
    private LocalDateTime lastCommTime;
}
