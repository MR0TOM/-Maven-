package com.smartpark.config.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Data
@Component
@ConfigurationProperties(prefix = "smart-park.energy")
public class EnergyProperties {

    private Integer dataRetentionDays = 365;
    private Integer statisticsBatchSize = 1000;
    private Integer alarmCheckInterval = 60;
    private Boolean enableAutoStatistics = true;
    private Boolean enableAlarmNotification = true;
}
