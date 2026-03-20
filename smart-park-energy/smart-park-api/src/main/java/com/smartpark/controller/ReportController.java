package com.smartpark.controller;

import com.smartpark.common.result.Result;
import com.smartpark.statistics.service.EnergyAnalysisService;
import com.smartpark.statistics.service.EnergyReportService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;

@Tag(name = "能耗分析报表", description = "能耗分析与报表接口")
@RestController
@RequestMapping("/api/report")
@RequiredArgsConstructor
public class ReportController {

    private final EnergyReportService energyReportService;
    private final EnergyAnalysisService energyAnalysisService;

    @Operation(summary = "获取仪表盘数据")
    @GetMapping("/dashboard/{buildingId}")
    public Result<Map<String, Object>> getDashboardData(@PathVariable Long buildingId) {
        return Result.success(energyReportService.getDashboardData(buildingId));
    }

    @Operation(summary = "获取能耗排名")
    @GetMapping("/ranking")
    public Result<Map<String, BigDecimal>> getConsumptionRanking(
            @Parameter(description = "能源类型") @RequestParam String energyType,
            @Parameter(description = "排名数量") @RequestParam(defaultValue = "10") Integer limit) {
        return Result.success(energyReportService.getConsumptionRanking(energyType, limit));
    }

    @Operation(summary = "获取能耗成本分析")
    @GetMapping("/cost/{buildingId}")
    public Result<Map<String, Object>> getEnergyCostAnalysis(
            @PathVariable Long buildingId,
            @Parameter(description = "开始日期") @RequestParam 
            @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate startDate,
            @Parameter(description = "结束日期") @RequestParam 
            @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate endDate) {
        return Result.success(energyReportService.getEnergyCostAnalysis(buildingId, startDate, endDate));
    }

    @Operation(summary = "分析能耗模式")
    @GetMapping("/pattern/{buildingId}")
    public Result<Map<String, Object>> analyzeConsumptionPattern(
            @PathVariable Long buildingId,
            @Parameter(description = "开始日期") @RequestParam 
            @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate startDate,
            @Parameter(description = "结束日期") @RequestParam 
            @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate endDate) {
        return Result.success(energyAnalysisService.analyzeConsumptionPattern(buildingId, startDate, endDate));
    }

    @Operation(summary = "获取峰谷分析")
    @GetMapping("/peak-valley/{buildingId}")
    public Result<List<Map<String, Object>>> getPeakValleyAnalysis(
            @PathVariable Long buildingId,
            @Parameter(description = "日期") @RequestParam 
            @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate date) {
        return Result.success(energyAnalysisService.getPeakValleyAnalysis(buildingId, date));
    }

    @Operation(summary = "预测能耗")
    @GetMapping("/predict/{buildingId}")
    public Result<Map<String, BigDecimal>> predictConsumption(
            @PathVariable Long buildingId,
            @Parameter(description = "预测天数") @RequestParam(defaultValue = "7") Integer days) {
        return Result.success(energyAnalysisService.predictConsumption(buildingId, days));
    }

    @Operation(summary = "获取能效分析")
    @GetMapping("/efficiency/{buildingId}")
    public Result<Map<String, Object>> getEnergyEfficiency(@PathVariable Long buildingId) {
        return Result.success(energyAnalysisService.getEnergyEfficiency(buildingId));
    }

    @Operation(summary = "获取异常能耗")
    @GetMapping("/abnormal/{buildingId}")
    public Result<List<Map<String, Object>>> getAbnormalConsumption(
            @PathVariable Long buildingId,
            @Parameter(description = "开始日期") @RequestParam 
            @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate startDate,
            @Parameter(description = "结束日期") @RequestParam 
            @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate endDate) {
        return Result.success(energyAnalysisService.getAbnormalConsumption(buildingId, startDate, endDate));
    }

    @Operation(summary = "获取碳排放分析")
    @GetMapping("/carbon/{buildingId}")
    public Result<Map<String, Object>> getCarbonEmission(
            @PathVariable Long buildingId,
            @Parameter(description = "开始日期") @RequestParam 
            @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate startDate,
            @Parameter(description = "结束日期") @RequestParam 
            @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate endDate) {
        return Result.success(energyAnalysisService.getCarbonEmission(buildingId, startDate, endDate));
    }

    @Operation(summary = "导出日报表")
    @GetMapping("/export/daily/{buildingId}/{date}")
    public void exportDailyReport(
            @PathVariable Long buildingId,
            @PathVariable @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate date,
            HttpServletResponse response) {
        byte[] data = energyReportService.generateDailyReport(buildingId, date);
        setExportResponse(response, "daily_report_" + date + ".xlsx", data);
    }

    @Operation(summary = "导出月报表")
    @GetMapping("/export/monthly/{buildingId}/{year}/{month}")
    public void exportMonthlyReport(
            @PathVariable Long buildingId,
            @PathVariable Integer year,
            @PathVariable Integer month,
            HttpServletResponse response) {
        byte[] data = energyReportService.generateMonthlyReport(buildingId, year, month);
        setExportResponse(response, "monthly_report_" + year + "_" + month + ".xlsx", data);
    }

    private void setExportResponse(HttpServletResponse response, String fileName, byte[] data) {
        try {
            response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
            response.setHeader("Content-Disposition", "attachment;filename=" + fileName);
            response.getOutputStream().write(data);
            response.getOutputStream().flush();
        } catch (Exception e) {
            throw new RuntimeException("导出报表失败", e);
        }
    }
}
