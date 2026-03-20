package com.smartpark.biz.listener;

import com.smartpark.biz.service.DeviceManageService;
import com.smartpark.common.constant.KafkaConstant;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

/**
 * 设备状态 Kafka 监听器
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class DeviceStatusListener {

    private final DeviceManageService deviceManageService;

    @KafkaListener(topics = KafkaConstant.TOPIC_DEVICE_STATUS, 
                   groupId = KafkaConstant.CONSUMER_GROUP_DEVICE)
    public void onDeviceStatus(String message) {
        log.info("接收到设备状态: {}", message);
        // TODO: 解析消息并更新设备状态
    }
}
