package com.smartpark.model.entity;

import com.baomidou.mybatisplus.annotation.*;
import com.smartpark.common.domain.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serial;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@EqualsAndHashCode(callSuper = true)
@TableName("sp_device")
public class Device extends BaseEntity {

    @Serial
    private static final long serialVersionUID = 1L;

    @TableId(type = IdType.ASSIGN_ID)
    private Long id;

    private String deviceCode;

    private String deviceName;

    private Long buildingId;

    private String deviceType;

    private String energyType;

    private String manufacturer;

    private String model;

    private String location;

    private LocalDateTime installDate;

    private LocalDateTime lastMaintenanceDate;

    private String status;

    private BigDecimal ratedPower;

    private String communicationProtocol;

    @TableLogic
    private String deleteFlag;

    @Version
    private Integer version;
}
