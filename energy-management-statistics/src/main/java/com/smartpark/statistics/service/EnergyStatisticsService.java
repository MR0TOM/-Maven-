package com.smartpark.statistics.service;

import com.smartpark.model.bo.EnergyStatisticsBO;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

/**
 * 能耗统计分析服务接口
 */
public interface EnergyStatisticsService {

    /**
     * 计算能耗同比环比
     *
     * @param parkId     园区ID
     * @param energyType 能源类型
     * @param startDate  开始日期
     * @param endDate    结束日期
     * @return 统计结果
     */
    EnergyStatisticsBO calculateYoYAndMoM(Long parkId, Integer energyType,
                                           LocalDate startDate, LocalDate endDate);

    /**
     * 能耗排名统计
     *
     * @param parkId     园区ID
     * @param energyType 能源类型
     * @param startTime  开始时间
     * @param endTime    结束时间
     * @param topN       前N名
     * @return 排名列表
     */
    List<Map<String, Object>> getEnergyRanking(Long parkId, Integer energyType,
                                                LocalDateTime startTime, LocalDateTime endTime, Integer topN);

    /**
     * 能耗趋势预测
     *
     * @param parkId     园区ID
     * @param energyType 能源类型
     * @param days       预测天数
     * @return 预测数据
     */
    List<Map<String, Object>> predictEnergyTrend(Long parkId, Integer energyType, Integer days);

    /**
     * 能耗峰谷分析
     *
     * @param parkId     园区ID
     * @param energyType 能源类型
     * @param startTime  开始时间
     * @param endTime    结束时间
     * @return 峰谷分析结果
     */
    Map<String, Object> analyzePeakValley(Long parkId, Integer energyType,
                                            LocalDateTime startTime, LocalDateTime endTime);

    /**
     * 能耗指标计算
     *
     * @param parkId     园区ID
     * @param energyType 能源类型
     * @param startTime  开始时间
     * @param endTime    结束时间
     * @return 指标数据
     */
    Map<String, Object> calculateEnergyMetrics(Long parkId, Integer energyType,
                                                LocalDateTime startTime, LocalDateTime endTime);

    /**
     * 生成能耗统计报表
     *
     * @param parkId     园区ID
     * @param energyType 能源类型
     * @param startTime  开始时间
     * @param endTime    结束时间
     * @param reportType 报表类型(1:日报,2:周报,3:月报,4:季报,5:年报)
     * @return 报表数据
     */
    Map<String, Object> generateStatisticsReport(Long parkId, Integer energyType,
                                                  LocalDateTime startTime, LocalDateTime endTime, Integer reportType);
}
