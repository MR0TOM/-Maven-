package com.smartpark.config.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Data
@Component
@ConfigurationProperties(prefix = "smart-park.kafka")
public class KafkaTopicProperties {

    private String energyDataTopic = "smart-park-energy-data";
    private String alarmTopic = "smart-park-alarm";
    private String statisticsTopic = "smart-park-statistics";
    private String deviceStatusTopic = "smart-park-device-status";
}
