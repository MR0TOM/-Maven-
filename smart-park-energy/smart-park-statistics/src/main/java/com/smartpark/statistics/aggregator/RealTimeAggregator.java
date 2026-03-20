package com.smartpark.statistics.aggregator;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 实时数据聚合器
 */
@Slf4j
@Component
public class RealTimeAggregator {

    private final Map<Long, BigDecimal> realTimeData = new ConcurrentHashMap<>();

    /**
     * 更新实时数据
     */
    public void updateData(Long meterId, BigDecimal consumption) {
        realTimeData.put(meterId, consumption);
    }

    /**
     * 获取实时数据
     */
    public BigDecimal getRealTimeData(Long meterId) {
        return realTimeData.getOrDefault(meterId, BigDecimal.ZERO);
    }

    /**
     * 获取所有实时数据
     */
    public Map<Long, BigDecimal> getAllRealTimeData() {
        return new ConcurrentHashMap<>(realTimeData);
    }

    /**
     * 清除数据
     */
    public void clearData(Long meterId) {
        realTimeData.remove(meterId);
    }
}
