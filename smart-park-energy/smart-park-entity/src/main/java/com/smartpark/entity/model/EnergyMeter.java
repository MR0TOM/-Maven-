package com.smartpark.entity.model;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 能耗计量设备实体
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("energy_meter")
public class EnergyMeter extends BaseEntity {

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
     * 所属区域
     */
    private String areaCode;

    /**
     * 安装位置
     */
    private String location;

    /**
     * 设备状态
     */
    private Integer status;

    /**
     * 经度
     */
    private Double longitude;

    /**
     * 纬度
     */
    private Double latitude;

    /**
     * 备注
     */
    private String remark;
}
