package com.smartpark.statistics.aggregator;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 历史数据聚合器
 */
@Slf4j
@Component
public class HistoricalAggregator {

    private final Map<String, BigDecimal> historicalData = new ConcurrentHashMap<>();

    /**
     * 聚合历史数据
     */
    public void aggregateData(Long meterId, LocalDate date, BigDecimal consumption) {
        String key = generateKey(meterId, date);
        historicalData.merge(key, consumption, BigDecimal::add);
    }

    /**
     * 获取历史数据
     */
    public BigDecimal getHistoricalData(Long meterId, LocalDate date) {
        String key = generateKey(meterId, date);
        return historicalData.getOrDefault(key, BigDecimal.ZERO);
    }

    /**
     * 生成缓存Key
     */
    private String generateKey(Long meterId, LocalDate date) {
        return meterId + ":" + date.toString();
    }
}
