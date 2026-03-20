package com.smartpark.statistics.calculator;

import com.smartpark.entity.model.EnergyRecord;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 月能耗计算器
 */
@Slf4j
@Component
public class MonthlyEnergyCalculator {

    /**
     * 计算月总能耗
     */
    public BigDecimal calculateMonthlyConsumption(List<EnergyRecord> records) {
        return records.stream()
                .map(EnergyRecord::getConsumption)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    /**
     * 按日分组计算
     */
    public Map<Integer, BigDecimal> calculateByDay(List<EnergyRecord> records) {
        return records.stream()
                .collect(Collectors.groupingBy(
                        r -> r.getDataTime().getDayOfMonth(),
                        Collectors.reducing(BigDecimal.ZERO, EnergyRecord::getConsumption, BigDecimal::add)
                ));
    }

    /**
     * 计算月峰值日
     */
    public Integer calculatePeakDay(List<EnergyRecord> records) {
        Map<Integer, BigDecimal> dailyMap = calculateByDay(records);
        return dailyMap.entrySet().stream()
                .max(Map.Entry.comparingByValue())
                .map(Map.Entry::getKey)
                .orElse(null);
    }
}
