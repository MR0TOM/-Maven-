package com.smartpark.biz.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.smartpark.biz.service.EnergyRecordService;
import com.smartpark.dao.mapper.EnergyRecordMapper;
import com.smartpark.entity.model.EnergyRecord;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * 能耗记录服务实现
 */
@Service
@RequiredArgsConstructor
public class EnergyRecordServiceImpl extends ServiceImpl<EnergyRecordMapper, EnergyRecord> 
        implements EnergyRecordService {

    private final EnergyRecordMapper energyRecordMapper;

    @Override
    public BigDecimal getTotalConsumption(Long meterId, LocalDateTime startTime, LocalDateTime endTime) {
        return energyRecordMapper.selectTotalConsumption(meterId, startTime, endTime);
    }
}
