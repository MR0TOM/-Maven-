package com.smartpark.common.constant;

public final class RedisConstants {
    
    private RedisConstants() {
    }

    public static final String CACHE_PREFIX = "smart:park:";
    
    public static final String TOKEN_PREFIX = CACHE_PREFIX + "token:";
    public static final String USER_PREFIX = CACHE_PREFIX + "user:";
    public static final String MENU_PREFIX = CACHE_PREFIX + "menu:";
    public static final String DICT_PREFIX = CACHE_PREFIX + "dict:";
    public static final String CONFIG_PREFIX = CACHE_PREFIX + "config:";
    
    public static final String ENERGY_DATA_PREFIX = CACHE_PREFIX + "energy:";
    public static final String DEVICE_STATUS_PREFIX = CACHE_PREFIX + "device:status:";
    public static final String STATISTICS_PREFIX = CACHE_PREFIX + "statistics:";
    
    public static final long DEFAULT_EXPIRE_TIME = 3600L;
    public static final long TOKEN_EXPIRE_TIME = 7200L;
    public static final long CACHE_EXPIRE_TIME = 1800L;
}
