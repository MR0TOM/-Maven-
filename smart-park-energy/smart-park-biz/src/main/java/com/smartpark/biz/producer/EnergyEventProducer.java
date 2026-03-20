package com.smartpark.biz.producer;

import com.smartpark.common.constant.KafkaConstant;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

/**
 * 能耗事件 Kafka 生产者
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class EnergyEventProducer {

    private final KafkaTemplate<String, String> kafkaTemplate;

    /**
     * 发送能耗数据
     */
    public void sendEnergyData(String data) {
        kafkaTemplate.send(KafkaConstant.TOPIC_ENERGY_DATA, data)
                .whenComplete((result, ex) -> {
                    if (ex == null) {
                        log.debug("能耗数据发送成功: {}", data);
                    } else {
                        log.error("能耗数据发送失败: {}", ex.getMessage());
                    }
                });
    }

    /**
     * 发送告警事件
     */
    public void sendAlarm(String alarmData) {
        kafkaTemplate.send(KafkaConstant.TOPIC_ALARM, alarmData)
                .whenComplete((result, ex) -> {
                    if (ex == null) {
                        log.debug("告警发送成功: {}", alarmData);
                    } else {
                        log.error("告警发送失败: {}", ex.getMessage());
                    }
                });
    }
}
