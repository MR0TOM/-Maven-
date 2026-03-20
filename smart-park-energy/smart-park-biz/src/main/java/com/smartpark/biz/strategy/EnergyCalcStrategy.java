package com.smartpark.biz.strategy;

import com.smartpark.entity.model.EnergyRecord;

import java.math.BigDecimal;
import java.util.List;

/**
 * 能耗计算策略接口
 */
public interface EnergyCalcStrategy {

    /**
     * 计算总能耗
     */
    BigDecimal calculateTotalConsumption(List<EnergyRecord> records);

    /**
     * 计算平均能耗
     */
    BigDecimal calculateAverageConsumption(List<EnergyRecord> records);

    /**
     * 获取策略类型
     */
    String getStrategyType();
}
