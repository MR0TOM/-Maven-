package com.smartpark.dal.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.smartpark.model.entity.Device;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface DeviceMapper extends BaseMapper<Device> {
}
