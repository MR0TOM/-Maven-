package com.smartpark.biz.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.smartpark.entity.model.DeviceInfo;

/**
 * 设备管理服务接口
 */
public interface DeviceManageService extends IService<DeviceInfo> {

    /**
     * 更新设备在线状态
     */
    void updateOnlineStatus(Long deviceId, Integer onlineStatus);
}
