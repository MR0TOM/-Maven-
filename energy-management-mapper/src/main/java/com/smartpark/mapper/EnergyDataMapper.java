package com.smartpark.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.smartpark.model.do.EnergyDataDO;
import com.smartpark.model.query.EnergyDataQuery;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

/**
 * 能耗数据Mapper接口
 */
@Mapper
public interface EnergyDataMapper extends BaseMapper<EnergyDataDO> {

    /**
     * 根据条件查询能耗数据列表
     *
     * @param query 查询条件
     * @return 能耗数据列表
     */
    List<EnergyDataDO> selectByQuery(@Param("query") EnergyDataQuery query);

    /**
     * 统计总能耗
     *
     * @param parkId      园区ID
     * @param buildingId  建筑ID
     * @param energyType  能源类型
     * @param startTime   开始时间
     * @param endTime     结束时间
     * @return 总能耗
     */
    BigDecimal sumEnergyValue(
            @Param("parkId") Long parkId,
            @Param("buildingId") Long buildingId,
            @Param("energyType") Integer energyType,
            @Param("startTime") LocalDateTime startTime,
            @Param("endTime") LocalDateTime endTime);

    /**
     * 按小时统计能耗
     *
     * @param parkId      园区ID
     * @param energyType  能源类型
     * @param startTime   开始时间
     * @param endTime     结束时间
     * @return 小时能耗统计
     */
    List<Map<String, Object>> selectHourlyStatistics(
            @Param("parkId") Long parkId,
            @Param("energyType") Integer energyType,
            @Param("startTime") LocalDateTime startTime,
            @Param("endTime") LocalDateTime endTime);

    /**
     * 按天统计能耗
     *
     * @param parkId      园区ID
     * @param energyType  能源类型
     * @param startTime   开始时间
     * @param endTime     结束时间
     * @return 天能耗统计
     */
    List<Map<String, Object>> selectDailyStatistics(
            @Param("parkId") Long parkId,
            @Param("energyType") Integer energyType,
            @Param("startTime") LocalDateTime startTime,
            @Param("endTime") LocalDateTime endTime);

    /**
     * 按月统计能耗
     *
     * @param parkId      园区ID
     * @param energyType  能源类型
     * @param startTime   开始时间
     * @param endTime     结束时间
     * @return 月能耗统计
     */
    List<Map<String, Object>> selectMonthlyStatistics(
            @Param("parkId") Long parkId,
            @Param("energyType") Integer energyType,
            @Param("startTime") LocalDateTime startTime,
            @Param("endTime") LocalDateTime endTime);
}
