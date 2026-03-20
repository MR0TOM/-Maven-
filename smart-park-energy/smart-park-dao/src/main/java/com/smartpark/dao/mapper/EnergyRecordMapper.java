package com.smartpark.dao.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.smartpark.entity.model.EnergyRecord;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * 能耗记录 Mapper
 */
@Mapper
public interface EnergyRecordMapper extends BaseMapper<EnergyRecord> {

    /**
     * 查询指定时间范围内的总能耗
     */
    @Select("SELECT SUM(consumption) FROM energy_record " +
            "WHERE meter_id = #{meterId} AND data_time BETWEEN #{startTime} AND #{endTime} AND deleted = 0")
    BigDecimal selectTotalConsumption(@Param("meterId") Long meterId,
                                      @Param("startTime") LocalDateTime startTime,
                                      @Param("endTime") LocalDateTime endTime);
}
