package com.smartpark.common.constant;

/**
 * 通用常量
 */
public class CommonConstants {

    private CommonConstants() {
    }

    /**
     * 成功标记
     */
    public static final Integer SUCCESS = 200;

    /**
     * 失败标记
     */
    public static final Integer FAIL = 500;

    /**
     * 未授权
     */
    public static final Integer UNAUTHORIZED = 401;

    /**
     * 未找到
     */
    public static final Integer NOT_FOUND = 404;

    /**
     * UTF-8 字符集
     */
    public static final String UTF8 = "UTF-8";

    /**
     * GBK 字符集
     */
    public static final String GBK = "GBK";

    /**
     * 时间格式
     */
    public static final String DATE_TIME_PATTERN = "yyyy-MM-dd HH:mm:ss";

    /**
     * 日期格式
     */
    public static final String DATE_PATTERN = "yyyy-MM-dd";

    /**
     * Redis Key前缀
     */
    public static final String REDIS_KEY_PREFIX = "smartpark:energy:";

    /**
     * 验证码Key前缀
     */
    public static final String CAPTCHA_KEY = REDIS_KEY_PREFIX + "captcha:";

    /**
     * Token Key前缀
     */
    public static final String TOKEN_KEY = REDIS_KEY_PREFIX + "token:";

    /**
     * Kafka Topic - 能耗数据采集
     */
    public static final String KAFKA_TOPIC_ENERGY_COLLECT = "energy_collect_topic";

    /**
     * Kafka Topic - 告警数据
     */
    public static final String KAFKA_TOPIC_ALARM = "alarm_topic";

    /**
     * 逻辑删除值
     */
    public static final Integer DELETED = 1;

    /**
     * 逻辑未删除值
     */
    public static final Integer NOT_DELETED = 0;

    /**
     * 启用状态
     */
    public static final Integer ENABLED = 1;

    /**
     * 禁用状态
     */
    public static final Integer DISABLED = 0;
}
