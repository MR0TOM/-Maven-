package com.smartpark.biz.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.smartpark.biz.service.EnergyMeterService;
import com.smartpark.dao.mapper.EnergyMeterMapper;
import com.smartpark.entity.model.EnergyMeter;
import org.springframework.stereotype.Service;

/**
 * 能耗计量设备服务实现
 */
@Service
public class EnergyMeterServiceImpl extends ServiceImpl<EnergyMeterMapper, EnergyMeter> 
        implements EnergyMeterService {
}
