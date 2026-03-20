package com.smartpark.biz.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.smartpark.entity.model.EnergyRecord;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * 能耗记录服务接口
 */
public interface EnergyRecordService extends IService<EnergyRecord> {

    /**
     * 查询指定时间范围内的总能耗
     */
    BigDecimal getTotalConsumption(Long meterId, LocalDateTime startTime, LocalDateTime endTime);
}
