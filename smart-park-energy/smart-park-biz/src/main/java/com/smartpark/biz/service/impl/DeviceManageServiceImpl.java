package com.smartpark.biz.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.smartpark.biz.service.DeviceManageService;
import com.smartpark.dao.mapper.DeviceInfoMapper;
import com.smartpark.dao.repository.DeviceCacheRepository;
import com.smartpark.entity.model.DeviceInfo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

/**
 * 设备管理服务实现
 */
@Service
@RequiredArgsConstructor
public class DeviceManageServiceImpl extends ServiceImpl<DeviceInfoMapper, DeviceInfo> 
        implements DeviceManageService {

    private final DeviceCacheRepository deviceCacheRepository;

    @Override
    public void updateOnlineStatus(Long deviceId, Integer onlineStatus) {
        DeviceInfo deviceInfo = new DeviceInfo();
        deviceInfo.setId(deviceId);
        deviceInfo.setOnlineStatus(onlineStatus);
        deviceInfo.setLastCommTime(LocalDateTime.now());
        updateById(deviceInfo);
        
        // 更新缓存
        deviceCacheRepository.cacheDeviceStatus(deviceId, onlineStatus);
    }
}
