package com.smartpark.common.constant;

/**
 * 缓存常量
 */
public final class CacheConstant {

    private CacheConstant() {}

    /**
     * 缓存前缀
     */
    public static final String CACHE_PREFIX = "smart:park:";

    /**
     * 能耗数据缓存
     */
    public static final String ENERGY_DATA = CACHE_PREFIX + "energy:data:";

    /**
     * 设备状态缓存
     */
    public static final String DEVICE_STATUS = CACHE_PREFIX + "device:status:";

    /**
     * 用户缓存
     */
    public static final String USER_CACHE = CACHE_PREFIX + "user:";

    /**
     * 默认过期时间（秒）
     */
    public static final long DEFAULT_EXPIRE = 3600;
}
