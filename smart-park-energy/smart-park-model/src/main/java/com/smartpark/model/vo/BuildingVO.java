package com.smartpark.model.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Schema(description = "建筑信息VO")
public class BuildingVO implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Schema(description = "建筑ID")
    private Long id;

    @Schema(description = "建筑编码")
    private String buildingCode;

    @Schema(description = "建筑名称")
    private String buildingName;

    @Schema(description = "建筑类型")
    private String buildingType;

    @Schema(description = "楼层数")
    private Integer floors;

    @Schema(description = "总面积")
    private BigDecimal totalArea;

    @Schema(description = "地址")
    private String address;

    @Schema(description = "负责人")
    private String manager;

    @Schema(description = "负责人电话")
    private String managerPhone;

    @Schema(description = "状态")
    private String status;

    @Schema(description = "设备数量")
    private Integer deviceCount;

    @Schema(description = "今日能耗")
    private BigDecimal todayConsumption;

    @Schema(description = "本月能耗")
    private BigDecimal monthConsumption;

    @Schema(description = "创建时间")
    private LocalDateTime createTime;
}
