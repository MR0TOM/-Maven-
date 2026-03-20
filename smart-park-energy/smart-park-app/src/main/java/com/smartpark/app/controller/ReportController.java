package com.smartpark.app.controller;

import com.smartpark.common.result.Result;
import com.smartpark.entity.dto.EnergyReportDTO;
import com.smartpark.statistics.service.ReportGenerateService;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

/**
 * 报表控制器
 */
@RestController
@RequestMapping("/api/report")
@RequiredArgsConstructor
public class ReportController {

    private final ReportGenerateService reportGenerateService;

    /**
     * 生成日报
     */
    @GetMapping("/daily")
    public Result<EnergyReportDTO> generateDailyReport(
            @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate date,
            @RequestParam(required = false) String areaCode) {
        return Result.success(reportGenerateService.generateDailyReport(date, areaCode));
    }

    /**
     * 生成月报
     */
    @GetMapping("/monthly")
    public Result<EnergyReportDTO> generateMonthlyReport(
            @RequestParam Integer year,
            @RequestParam Integer month,
            @RequestParam(required = false) String areaCode) {
        return Result.success(reportGenerateService.generateMonthlyReport(year, month, areaCode));
    }

    /**
     * 生成自定义报表
     */
    @GetMapping("/custom")
    public Result<List<EnergyReportDTO>> generateCustomReport(
            @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate startDate,
            @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate endDate,
            @RequestParam(required = false) String areaCode) {
        return Result.success(reportGenerateService.generateCustomReport(startDate, endDate, areaCode));
    }
}
