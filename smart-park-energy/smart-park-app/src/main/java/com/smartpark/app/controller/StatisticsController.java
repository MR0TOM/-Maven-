package com.smartpark.app.controller;

import com.smartpark.common.result.Result;
import com.smartpark.entity.vo.EnergyStatisticsVO;
import com.smartpark.entity.vo.EnergyTrendVO;
import com.smartpark.statistics.service.EnergyStatisticsService;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

/**
 * 统计控制器
 */
@RestController
@RequestMapping("/api/statistics")
@RequiredArgsConstructor
public class StatisticsController {

    private final EnergyStatisticsService energyStatisticsService;

    /**
     * 获取能耗统计概览
     */
    @GetMapping("/overview")
    public Result<EnergyStatisticsVO> getOverview(@RequestParam Integer energyType) {
        return Result.success(energyStatisticsService.getStatisticsOverview(energyType));
    }

    /**
     * 获取能耗趋势
     */
    @GetMapping("/trend")
    public Result<List<EnergyTrendVO>> getTrend(
            @RequestParam Integer energyType,
            @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate startDate,
            @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate endDate) {
        return Result.success(energyStatisticsService.getEnergyTrend(energyType, startDate, endDate));
    }

    /**
     * 获取区域能耗排名
     */
    @GetMapping("/ranking")
    public Result<List<EnergyStatisticsVO>> getRanking(
            @RequestParam Integer energyType,
            @RequestParam(defaultValue = "10") Integer topN) {
        return Result.success(energyStatisticsService.getAreaRanking(energyType, topN));
    }
}
