package com.smartpark.entity.vo;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * 能耗趋势 VO
 */
@Data
public class EnergyTrendVO implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 时间
     */
    private LocalDateTime time;

    /**
     * 能耗值
     */
    private BigDecimal consumption;

    /**
     * 同比值
     */
    private BigDecimal yoyValue;
}
