package com.smartpark.dao.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.smartpark.entity.model.EnergyMeter;
import org.apache.ibatis.annotations.Mapper;

/**
 * 能耗计量设备 Mapper
 */
@Mapper
public interface EnergyMeterMapper extends BaseMapper<EnergyMeter> {
}
