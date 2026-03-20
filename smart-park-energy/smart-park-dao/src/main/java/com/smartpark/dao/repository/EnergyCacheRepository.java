package com.smartpark.dao.repository;

import com.smartpark.common.constant.CacheConstant;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.concurrent.TimeUnit;

/**
 * 能耗数据缓存仓库
 */
@Repository
@RequiredArgsConstructor
public class EnergyCacheRepository {

    private final RedisTemplate<String, Object> redisTemplate;

    /**
     * 缓存能耗数据
     */
    public void cacheEnergyData(Long meterId, String data) {
        String key = CacheConstant.ENERGY_DATA + meterId;
        redisTemplate.opsForValue().set(key, data, CacheConstant.DEFAULT_EXPIRE, TimeUnit.SECONDS);
    }

    /**
     * 获取缓存的能耗数据
     */
    public String getEnergyData(Long meterId) {
        String key = CacheConstant.ENERGY_DATA + meterId;
        Object value = redisTemplate.opsForValue().get(key);
        return value != null ? value.toString() : null;
    }

    /**
     * 缓存今日能耗
     */
    public void cacheTodayConsumption(Long meterId, BigDecimal consumption) {
        String key = CacheConstant.ENERGY_DATA + "today:" + meterId;
        redisTemplate.opsForValue().set(key, consumption.toString(), 86400, TimeUnit.SECONDS);
    }

    /**
     * 删除能耗缓存
     */
    public void deleteEnergyCache(Long meterId) {
        String key = CacheConstant.ENERGY_DATA + meterId;
        redisTemplate.delete(key);
    }
}
