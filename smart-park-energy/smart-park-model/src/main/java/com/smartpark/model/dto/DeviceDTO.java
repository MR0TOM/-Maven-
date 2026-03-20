package com.smartpark.model.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@Schema(description = "设备信息DTO")
public class DeviceDTO implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Schema(description = "设备ID")
    private Long id;

    @Schema(description = "设备编码", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotBlank(message = "设备编码不能为空")
    private String deviceCode;

    @Schema(description = "设备名称", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotBlank(message = "设备名称不能为空")
    private String deviceName;

    @Schema(description = "建筑ID", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "建筑ID不能为空")
    private Long buildingId;

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

    @Schema(description = "状态")
    private String status;

    @Schema(description = "额定功率")
    private String ratedPower;

    @Schema(description = "通讯协议")
    private String communicationProtocol;
}
