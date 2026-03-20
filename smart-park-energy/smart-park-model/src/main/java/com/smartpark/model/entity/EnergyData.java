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
@TableName("sp_energy_data")
public class EnergyData extends BaseEntity {

    @Serial
    private static final long serialVersionUID = 1L;

    @TableId(type = IdType.ASSIGN_ID)
    private Long id;

    private Long deviceId;

    private LocalDateTime collectTime;

    private BigDecimal currentValue;

    private BigDecimal totalValue;

    private BigDecimal power;

    private BigDecimal voltage;

    private BigDecimal current;

    private BigDecimal powerFactor;

    private String energyType;

    private String unit;

    private String dataQuality;

    private String status;

    @TableLogic
    private String deleteFlag;
}
