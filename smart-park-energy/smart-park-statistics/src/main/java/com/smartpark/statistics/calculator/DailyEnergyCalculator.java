package com.smartpark.statistics.calculator;

import com.smartpark.entity.model.EnergyRecord;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

/**
 * 日能耗计算器
 */
@Slf4j
@Component
public class DailyEnergyCalculator {

    /**
     * 计算日总能耗
     */
    public BigDecimal calculateDailyConsumption(List<EnergyRecord> records) {
        return records.stream()
                .map(EnergyRecord::getConsumption)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    /**
     * 计算日峰值
     */
    public BigDecimal calculateDailyPeak(List<EnergyRecord> records) {
        return records.stream()
                .map(EnergyRecord::getConsumption)
                .max(BigDecimal::compareTo)
                .orElse(BigDecimal.ZERO);
    }

    /**
     * 计算日谷值
     */
    public BigDecimal calculateDailyValley(List<EnergyRecord> records) {
        return records.stream()
                .map(EnergyRecord::getConsumption)
                .min(BigDecimal::compareTo)
                .orElse(BigDecimal.ZERO);
    }

    /**
     * 计算日平均值
     */
    public BigDecimal calculateDailyAverage(List<EnergyRecord> records) {
        if (records.isEmpty()) {
            return BigDecimal.ZERO;
        }
        BigDecimal total = calculateDailyConsumption(records);
        return total.divide(BigDecimal.valueOf(records.size()), 2, BigDecimal.ROUND_HALF_UP);
    }
}
