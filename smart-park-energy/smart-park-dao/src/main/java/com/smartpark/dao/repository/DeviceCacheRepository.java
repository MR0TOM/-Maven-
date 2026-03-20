package com.smartpark.dao.repository;

import com.smartpark.common.constant.CacheConstant;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

import java.util.concurrent.TimeUnit;

/**
 * 设备缓存仓库
 */
@Repository
@RequiredArgsConstructor
public class DeviceCacheRepository {

    private final RedisTemplate<String, Object> redisTemplate;

    /**
     * 缓存设备状态
     */
    public void cacheDeviceStatus(Long deviceId, Integer status) {
        String key = CacheConstant.DEVICE_STATUS + deviceId;
        redisTemplate.opsForValue().set(key, status, CacheConstant.DEFAULT_EXPIRE, TimeUnit.SECONDS);
    }

    /**
     * 获取设备状态
     */
    public Integer getDeviceStatus(Long deviceId) {
        String key = CacheConstant.DEVICE_STATUS + deviceId;
        Object value = redisTemplate.opsForValue().get(key);
        return value != null ? (Integer) value : null;
    }

    /**
     * 删除设备缓存
     */
    public void deleteDeviceCache(Long deviceId) {
        String key = CacheConstant.DEVICE_STATUS + deviceId;
        redisTemplate.delete(key);
    }
}
