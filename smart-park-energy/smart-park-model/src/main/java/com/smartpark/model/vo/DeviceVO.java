package com.smartpark.model.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Schema(description = "设备信息VO")
public class DeviceVO implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Schema(description = "设备ID")
    private Long id;

    @Schema(description = "设备编码")
    private String deviceCode;

    @Schema(description = "设备名称")
    private String deviceName;

    @Schema(description = "建筑ID")
    private Long buildingId;

    @Schema(description = "建筑名称")
    private String buildingName;

    @Schema(description = "设备类型")
    private String deviceType;

    @Schema(description = "能源类型")
    private String energyType;

    @Schema(description = "制造商")
    private String manufacturer;

    @Schema(description = "型号")
    private String model;

    @Schema(description = "安装位置")
    private String location;

    @Schema(description = "安装日期")
    private LocalDateTime installDate;

    @Schema(description = "最后维护日期")
    private LocalDateTime lastMaintenanceDate;

    @Schema(description = "状态")
    private String status;

    @Schema(description = "额定功率")
    private BigDecimal ratedPower;

    @Schema(description = "通讯协议")
    private String communicationProtocol;

    @Schema(description = "当前读数")
    private BigDecimal currentValue;

    @Schema(description = "今日能耗")
    private BigDecimal todayConsumption;

    @Schema(description = "在线状态")
    private String onlineStatus;
}
