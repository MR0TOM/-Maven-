package com.smartpark.statistics.service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;

public interface EnergyReportService {

    byte[] generateDailyReport(Long buildingId, LocalDate date);

    byte[] generateWeeklyReport(Long buildingId, LocalDate startDate);

    byte[] generateMonthlyReport(Long buildingId, Integer year, Integer month);

    byte[] generateYearlyReport(Long buildingId, Integer year);

    Map<String, Object> getDashboardData(Long buildingId);

    Map<String, BigDecimal> getConsumptionRanking(String energyType, Integer limit);

    Map<String, Object> getEnergyCostAnalysis(Long buildingId, LocalDate startDate, LocalDate endDate);
}
