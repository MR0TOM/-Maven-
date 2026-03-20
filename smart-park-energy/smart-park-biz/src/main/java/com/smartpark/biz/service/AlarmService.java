package com.smartpark.biz.service;

/**
 * 告警服务接口
 */
public interface AlarmService {

    /**
     * 创建告警
     */
    void createAlarm(Long deviceId, Integer alarmLevel, String alarmContent);

    /**
     * 处理告警
     */
    void handleAlarm(Long alarmId, String handleResult);
}
