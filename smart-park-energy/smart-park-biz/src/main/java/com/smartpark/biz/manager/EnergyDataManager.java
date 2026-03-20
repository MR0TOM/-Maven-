package com.smartpark.biz.manager;

import com.smartpark.biz.service.EnergyRecordService;
import com.smartpark.dao.repository.EnergyCacheRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * 能耗数据管理器
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class EnergyDataManager {

    private final EnergyRecordService energyRecordService;
    private final EnergyCacheRepository energyCacheRepository;

    /**
     * 获取总能耗（优先从缓存获取）
     */
    public BigDecimal getTotalConsumption(Long meterId, LocalDateTime startTime, LocalDateTime endTime) {
        // 尝试从缓存获取
        String cacheKey = meterId + ":" + startTime + ":" + endTime;
        String cached = energyCacheRepository.getEnergyData(meterId);
        
        if (cached != null) {
            return new BigDecimal(cached);
        }
        
        // 从数据库查询
        BigDecimal total = energyRecordService.getTotalConsumption(meterId, startTime, endTime);
        if (total != null) {
            energyCacheRepository.cacheEnergyData(meterId, total.toString());
        }
        
        return total != null ? total : BigDecimal.ZERO;
    }
}
