package com.smartpark.statistics.service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;

public interface EnergyAnalysisService {

    Map<String, Object> analyzeConsumptionPattern(Long buildingId, LocalDate startDate, LocalDate endDate);

    List<Map<String, Object>> getPeakValleyAnalysis(Long buildingId, LocalDate date);

    Map<String, BigDecimal> predictConsumption(Long buildingId, Integer days);

    Map<String, Object> getEnergyEfficiency(Long buildingId);

    List<Map<String, Object>> getAbnormalConsumption(Long buildingId, LocalDate startDate, LocalDate endDate);

    Map<String, Object> getCarbonEmission(Long buildingId, LocalDate startDate, LocalDate endDate);
}
