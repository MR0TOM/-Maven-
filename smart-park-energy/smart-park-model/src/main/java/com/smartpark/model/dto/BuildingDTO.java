package com.smartpark.model.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;

@Data
@Schema(description = "建筑信息DTO")
public class BuildingDTO implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Schema(description = "建筑ID")
    private Long id;

    @Schema(description = "建筑编码", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotBlank(message = "建筑编码不能为空")
    @Size(max = 50, message = "建筑编码长度不能超过50个字符")
    private String buildingCode;

    @Schema(description = "建筑名称", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotBlank(message = "建筑名称不能为空")
    @Size(max = 100, message = "建筑名称长度不能超过100个字符")
    private String buildingName;

    @Schema(description = "建筑类型")
    private String buildingType;

    @Schema(description = "楼层数")
    private Integer floors;

    @Schema(description = "总面积")
    private String totalArea;

    @Schema(description = "地址")
    private String address;

    @Schema(description = "负责人")
    private String manager;

    @Schema(description = "负责人电话")
    private String managerPhone;

    @Schema(description = "状态")
    private String status;
}
