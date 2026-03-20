package com.smartpark.controller;

import com.smartpark.common.result.Result;
import com.smartpark.model.vo.EnergyStatisticsVO;
import com.smartpark.statistics.service.EnergyStatisticsService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@Tag(name = "能耗统计", description = "能耗统计分析接口")
@RestController
@RequestMapping("/api/statistics")
@RequiredArgsConstructor
public class StatisticsController {

    private final EnergyStatisticsService energyStatisticsService;

    @Operation(summary = "根据建筑ID查询能耗统计")
    @GetMapping("/building/{buildingId}")
    public Result<List<EnergyStatisticsVO>> getByBuildingId(
            @PathVariable Long buildingId,
            @Parameter(description = "开始日期") @RequestParam(required = false) 
            @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate startDate,
            @Parameter(description = "结束日期") @RequestParam(required = false) 
            @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate endDate) {
        return Result.success(energyStatisticsService.getByBuildingId(buildingId, startDate, endDate));
    }

    @Operation(summary = "根据设备ID查询能耗统计")
    @GetMapping("/device/{deviceId}")
    public Result<List<EnergyStatisticsVO>> getByDeviceId(
            @PathVariable Long deviceId,
            @Parameter(description = "开始日期") @RequestParam(required = false) 
            @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate startDate,
            @Parameter(description = "结束日期") @RequestParam(required = false) 
            @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate endDate) {
        return Result.success(energyStatisticsService.getByDeviceId(deviceId, startDate, endDate));
    }

    @Operation(summary = "获取实时能耗统计")
    @GetMapping("/realtime/{buildingId}")
    public Result<EnergyStatisticsVO> getRealtimeStatistics(@PathVariable Long buildingId) {
        return Result.success(energyStatisticsService.getRealtimeStatistics(buildingId));
    }

    @Operation(summary = "获取能耗趋势")
    @GetMapping("/trend/{buildingId}")
    public Result<List<EnergyStatisticsVO>> getConsumptionTrend(
            @PathVariable Long buildingId,
            @Parameter(description = "统计类型") @RequestParam String statisticsType,
            @Parameter(description = "开始日期") @RequestParam(required = false) 
            @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate startDate,
            @Parameter(description = "结束日期") @RequestParam(required = false) 
            @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate endDate) {
        return Result.success(energyStatisticsService.getConsumptionTrend(buildingId, statisticsType, startDate, endDate));
    }

    @Operation(summary = "获取能耗对比")
    @GetMapping("/comparison/{buildingId}")
    public Result<EnergyStatisticsVO> getComparison(
            @PathVariable Long buildingId,
            @Parameter(description = "对比类型(YOY/MOM)") @RequestParam String comparisonType) {
        return Result.success(energyStatisticsService.getComparison(buildingId, comparisonType));
    }

    @Operation(summary = "生成日统计")
    @PostMapping("/daily/{date}")
    public Result<Void> generateDailyStatistics(
            @Parameter(description = "日期") @PathVariable 
            @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate date) {
        energyStatisticsService.generateDailyStatistics(date);
        return Result.success();
    }

    @Operation(summary = "生成月统计")
    @PostMapping("/monthly/{year}/{month}")
    public Result<Void> generateMonthlyStatistics(
            @PathVariable Integer year,
            @PathVariable Integer month) {
        energyStatisticsService.generateMonthlyStatistics(year, month);
        return Result.success();
    }
}
