package com.smartpark.model.do;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 设备信息实体类
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("device")
public class DeviceDO extends BaseDO {

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
     * 设备类型(1:电表,2:水表,3:燃气表,4:热表,5:冷表)
     */
    private Integer deviceType;

    /**
     * 设备型号
     */
    private String deviceModel;

    /**
     * 厂商名称
     */
    private String manufacturer;

    /**
     * 园区ID
     */
    private Long parkId;

    /**
     * 建筑ID
     */
    private Long buildingId;

    /**
     * 楼层
     */
    private Integer floor;

    /**
     * 安装位置
     */
    private String location;

    /**
     * 通信协议
     */
    private String protocol;

    /**
     * IP地址
     */
    private String ipAddress;

    /**
     * 端口号
     */
    private Integer port;

    /**
     * 设备状态(0:离线,1:在线,2:故障)
     */
    private Integer status;

    /**
     * 备注
     */
    private String remark;
}
