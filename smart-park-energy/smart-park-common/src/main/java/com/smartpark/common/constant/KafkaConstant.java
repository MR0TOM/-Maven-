package com.smartpark.common.constant;

/**
 * Kafka 常量
 */
public final class KafkaConstant {

    private KafkaConstant() {}

    /**
     * 能耗数据 Topic
     */
    public static final String TOPIC_ENERGY_DATA = "smart-park-energy-data";

    /**
     * 设备状态 Topic
     */
    public static final String TOPIC_DEVICE_STATUS = "smart-park-device-status";

    /**
     * 告警 Topic
     */
    public static final String TOPIC_ALARM = "smart-park-alarm";

    /**
     * 消费者组
     */
    public static final String CONSUMER_GROUP_ENERGY = "energy-consumer-group";
    public static final String CONSUMER_GROUP_DEVICE = "device-consumer-group";
    public static final String CONSUMER_GROUP_ALARM = "alarm-consumer-group";
}
