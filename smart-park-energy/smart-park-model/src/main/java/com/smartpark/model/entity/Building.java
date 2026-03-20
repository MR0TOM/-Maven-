package com.smartpark.model.entity;

import com.baomidou.mybatisplus.annotation.*;
import com.smartpark.common.domain.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serial;
import java.math.BigDecimal;

@Data
@EqualsAndHashCode(callSuper = true)
@TableName("sp_building")
public class Building extends BaseEntity {

    @Serial
    private static final long serialVersionUID = 1L;

    @TableId(type = IdType.ASSIGN_ID)
    private Long id;

    private String buildingCode;

    private String buildingName;

    private String buildingType;

    private Integer floors;

    private BigDecimal totalArea;

    private String address;

    private String manager;

    private String managerPhone;

    private String status;

    @TableLogic
    private String deleteFlag;

    @Version
    private Integer version;
}
