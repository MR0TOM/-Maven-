package com.smartpark.biz.service.impl;

import com.smartpark.biz.service.AlarmService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * 告警服务实现
 */
@Slf4j
@Service
public class AlarmServiceImpl implements AlarmService {

    @Override
    public void createAlarm(Long deviceId, Integer alarmLevel, String alarmContent) {
        log.info("创建告警 - 设备ID: {}, 级别: {}, 内容: {}", deviceId, alarmLevel, alarmContent);
        // TODO: 实现告警创建逻辑
    }

    @Override
    public void handleAlarm(Long alarmId, String handleResult) {
        log.info("处理告警 - 告警ID: {}, 处理结果: {}", alarmId, handleResult);
        // TODO: 实现告警处理逻辑
    }
}
