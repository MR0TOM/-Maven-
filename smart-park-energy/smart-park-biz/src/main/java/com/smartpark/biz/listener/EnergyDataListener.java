package com.smartpark.biz.listener;

import com.smartpark.common.constant.KafkaConstant;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

/**
 * 能耗数据 Kafka 监听器
 */
@Slf4j
@Component
public class EnergyDataListener {

    @KafkaListener(topics = KafkaConstant.TOPIC_ENERGY_DATA, 
                   groupId = KafkaConstant.CONSUMER_GROUP_ENERGY)
    public void onEnergyData(String message) {
        log.info("接收到能耗数据: {}", message);
        // TODO: 处理能耗数据，保存到数据库
    }
}
