package com.smartpark.model.entity;

import com.baomidou.mybatisplus.annotation.*;
import com.smartpark.common.domain.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serial;
import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@EqualsAndHashCode(callSuper = true)
@TableName("sp_energy_statistics")
public class EnergyStatistics extends BaseEntity {

    @Serial
    private static final long serialVersionUID = 1L;

    @TableId(type = IdType.ASSIGN_ID)
    private Long id;

    private Long buildingId;

    private Long deviceId;

    private LocalDate statisticsDate;

    private String statisticsType;

    private String energyType;

    private BigDecimal totalConsumption;

    private BigDecimal peakConsumption;

    private BigDecimal valleyConsumption;

    private BigDecimal flatConsumption;

    private BigDecimal maxPower;

    private BigDecimal avgPower;

    private BigDecimal cost;

    private String unit;

    @TableLogic
    private String deleteFlag;
}
