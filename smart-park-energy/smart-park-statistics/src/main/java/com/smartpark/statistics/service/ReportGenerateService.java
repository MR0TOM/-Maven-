package com.smartpark.statistics.service;

import com.smartpark.entity.dto.EnergyReportDTO;

import java.time.LocalDate;
import java.util.List;

/**
 * 报表生成服务接口
 */
public interface ReportGenerateService {

    /**
     * 生成日报
     */
    EnergyReportDTO generateDailyReport(LocalDate date, String areaCode);

    /**
     * 生成月报
     */
    EnergyReportDTO generateMonthlyReport(Integer year, Integer month, String areaCode);

    /**
     * 生成自定义时间段报表
     */
    List<EnergyReportDTO> generateCustomReport(LocalDate startDate, LocalDate endDate, String areaCode);
}
