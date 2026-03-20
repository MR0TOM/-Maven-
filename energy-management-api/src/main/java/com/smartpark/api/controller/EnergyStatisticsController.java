package com.smartpark.api.controller;

import com.smartpark.common.utils.Result;
import com.smartpark.model.bo.EnergyStatisticsBO;
import com.smartpark.statistics.service.EnergyStatisticsService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

/**
 * 能耗统计分析控制器
 */
@Slf4j
@RestController
@RequestMapping("/energy/statistics")
@RequiredArgsConstructor
@Tag(name = "能耗统计分析", description = "能耗同比环比、趋势预测、排名分析等统计功能")
public class EnergyStatisticsController {

    private final EnergyStatisticsService energyStatisticsService;

    /**
     * 计算能耗同比环比
     */
    @GetMapping("/yoy-mom")
    @Operation(summary = "能耗同比环比分析", description = "计算指定时间段的能耗同比、环比增长率")
    public Result<EnergyStatisticsBO> calculateYoYAndMoM(
            @Parameter(description = "园区ID", required = true) @RequestParam Long parkId,
            @Parameter(description = "能源类型") @RequestParam(required = false) Integer energyType,
            @Parameter(description = "开始日期", required = true) @RequestParam LocalDate startDate,
            @Parameter(description = "结束日期", required = true) @RequestParam LocalDate endDate) {
        log.info("计算能耗同比环比，园区ID: {}, 能源类型: {}, 开始日期: {}, 结束日期: {}",
                parkId, energyType, startDate, endDate);
        EnergyStatisticsBO result = energyStatisticsService.calculateYoYAndMoM(
                parkId, energyType, startDate, endDate);
        return Result.success(result);
    }

    /**
     * 能耗排名统计
     */
    @GetMapping("/ranking")
    @Operation(summary = "能耗排名统计", description = "按建筑/区域进行能耗排名统计")
    public Result<List<Map<String, Object>>> getEnergyRanking(
            @Parameter(description = "园区ID", required = true) @RequestParam Long parkId,
            @Parameter(description = "能源类型") @RequestParam(required = false) Integer energyType,
            @Parameter(description = "开始时间") @RequestParam(required = false) LocalDateTime startTime,
            @Parameter(description = "结束时间") @RequestParam(required = false) LocalDateTime endTime,
            @Parameter(description = "排名数量") @RequestParam(defaultValue = "10") Integer topN) {
        log.info("能耗排名统计，园区ID: {}, 能源类型: {}", parkId, energyType);
        List<Map<String, Object>> result = energyStatisticsService.getEnergyRanking(
                parkId, energyType, startTime, endTime, topN);
        return Result.success(result);
    }

    /**
     * 能耗趋势预测
     */
    @GetMapping("/predict")
    @Operation(summary = "能耗趋势预测", description = "基于历史数据进行能耗趋势预测")
    public Result<List<Map<String, Object>>> predictEnergyTrend(
            @Parameter(description = "园区ID", required = true) @RequestParam Long parkId,
            @Parameter(description = "能源类型") @RequestParam(required = false) Integer energyType,
            @Parameter(description = "预测天数") @RequestParam(defaultValue = "7") Integer days) {
        log.info("能耗趋势预测，园区ID: {}, 能源类型: {}, 预测天数: {}", parkId, energyType, days);
        List<Map<String, Object>> result = energyStatisticsService.predictEnergyTrend(parkId, energyType, days);
        return Result.success(result);
    }

    /**
     * 能耗峰谷分析
     */
    @GetMapping("/peak-valley")
    @Operation(summary = "能耗峰谷分析", description = "分析能耗的峰谷时段分布")
    public Result<Map<String, Object>> analyzePeakValley(
            @Parameter(description = "园区ID", required = true) @RequestParam Long parkId,
            @Parameter(description = "能源类型") @RequestParam(required = false) Integer energyType,
            @Parameter(description = "开始时间") @RequestParam(required = false) LocalDateTime startTime,
            @Parameter(description = "结束时间") @RequestParam(required = false) LocalDateTime endTime) {
        log.info("能耗峰谷分析，园区ID: {}, 能源类型: {}", parkId, energyType);
        Map<String, Object> result = energyStatisticsService.analyzePeakValley(
                parkId, energyType, startTime, endTime);
        return Result.success(result);
    }

    /**
     * 能耗指标计算
     */
    @GetMapping("/metrics")
    @Operation(summary = "能耗指标计算", description = "计算各类能耗指标（总能耗、日平均、能耗密度等）")
    public Result<Map<String, Object>> calculateEnergyMetrics(
            @Parameter(description = "园区ID", required = true) @RequestParam Long parkId,
            @Parameter(description = "能源类型") @RequestParam(required = false) Integer energyType,
            @Parameter(description = "开始时间") @RequestParam(required = false) LocalDateTime startTime,
            @Parameter(description = "结束时间") @RequestParam(required = false) LocalDateTime endTime) {
        log.info("能耗指标计算，园区ID: {}, 能源类型: {}", parkId, energyType);
        Map<String, Object> result = energyStatisticsService.calculateEnergyMetrics(
                parkId, energyType, startTime, endTime);
        return Result.success(result);
    }

    /**
     * 生成能耗统计报表
     */
    @GetMapping("/report")
    @Operation(summary = "生成能耗统计报表", description = "生成日报/周报/月报/季报/年报")
    public Result<Map<String, Object>> generateStatisticsReport(
            @Parameter(description = "园区ID", required = true) @RequestParam Long parkId,
            @Parameter(description = "能源类型") @RequestParam(required = false) Integer energyType,
            @Parameter(description = "开始时间") @RequestParam(required = false) LocalDateTime startTime,
            @Parameter(description = "结束时间") @RequestParam(required = false) LocalDateTime endTime,
            @Parameter(description = "报表类型(1:日报,2:周报,3:月报,4:季报,5:年报)", required = true) @RequestParam Integer reportType) {
        log.info("生成能耗统计报表，园区ID: {}, 能源类型: {}, 报表类型: {}",
                parkId, energyType, reportType);
        Map<String, Object> result = energyStatisticsService.generateStatisticsReport(
                parkId, energyType, startTime, endTime, reportType);
        return Result.success(result);
    }
}
