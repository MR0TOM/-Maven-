package com.smartpark.statistics.service;

import com.smartpark.model.entity.EnergyStatistics;
import com.smartpark.model.vo.EnergyStatisticsVO;

import java.time.LocalDate;
import java.util.List;

public interface EnergyStatisticsService {

    EnergyStatistics getById(Long id);

    List<EnergyStatisticsVO> getByBuildingId(Long buildingId, LocalDate startDate, LocalDate endDate);

    List<EnergyStatisticsVO> getByDeviceId(Long deviceId, LocalDate startDate, LocalDate endDate);

    void generateDailyStatistics(LocalDate date);

    void generateHourlyStatistics(LocalDate date, Integer hour);

    void generateMonthlyStatistics(Integer year, Integer month);

    EnergyStatisticsVO getRealtimeStatistics(Long buildingId);

    List<EnergyStatisticsVO> getConsumptionTrend(Long buildingId, String statisticsType, 
                                                   LocalDate startDate, LocalDate endDate);

    EnergyStatisticsVO getComparison(Long buildingId, String comparisonType);
}
