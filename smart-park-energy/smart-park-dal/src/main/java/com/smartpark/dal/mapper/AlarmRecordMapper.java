package com.smartpark.dal.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.smartpark.model.entity.AlarmRecord;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface AlarmRecordMapper extends BaseMapper<AlarmRecord> {
}
