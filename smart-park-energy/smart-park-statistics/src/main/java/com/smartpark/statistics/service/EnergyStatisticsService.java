package com.smartpark.statistics.service;

import com.smartpark.entity.vo.EnergyStatisticsVO;
import com.smartpark.entity.vo.EnergyTrendVO;

import java.time.LocalDate;
import java.util.List;

/**
 * 能耗统计服务接口
 */
public interface EnergyStatisticsService {

    /**
     * 获取能耗统计概览
     */
    EnergyStatisticsVO getStatisticsOverview(Integer energyType);

    /**
     * 获取能耗趋势
     */
    List<EnergyTrendVO> getEnergyTrend(Integer energyType, LocalDate startDate, LocalDate endDate);

    /**
     * 获取区域能耗排名
     */
    List<EnergyStatisticsVO> getAreaRanking(Integer energyType, Integer topN);
}
