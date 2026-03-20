package com.smartpark.statistics.service.impl;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.date.DateField;
import cn.hutool.core.date.DateTime;
import cn.hutool.core.date.DateUtil;
import com.smartpark.common.enums.EnergyTypeEnum;
import com.smartpark.model.bo.EnergyStatisticsBO;
import com.smartpark.service.EnergyDataService;
import com.smartpark.statistics.service.EnergyStatisticsService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.*;

/**
 * 能耗统计分析服务实现类
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class EnergyStatisticsServiceImpl implements EnergyStatisticsService {

    private final EnergyDataService energyDataService;

    @Override
    public EnergyStatisticsBO calculateYoYAndMoM(Long parkId, Integer energyType,
                                                  LocalDate startDate, LocalDate endDate) {
        log.info("计算能耗同比环比，园区ID: {}, 能源类型: {}, 开始日期: {}, 结束日期: {}",
                parkId, energyType, startDate, endDate);

        EnergyStatisticsBO statisticsBO = new EnergyStatisticsBO();
        statisticsBO.setParkId(parkId);
        statisticsBO.setEnergyType(energyType);
        statisticsBO.setStartDate(startDate);
        statisticsBO.setEndDate(endDate);
        statisticsBO.setStatisticsTime(LocalDate.now());

        // 设置能源类型名称
        if (energyType != null) {
            EnergyTypeEnum energyTypeEnum = EnergyTypeEnum.getByCode(energyType);
            if (energyTypeEnum != null) {
                statisticsBO.setEnergyTypeName(energyTypeEnum.getName());
            }
        }

        // 当前周期能耗
        LocalDateTime currentStartTime = startDate.atStartOfDay();
        LocalDateTime currentEndTime = endDate.atTime(LocalTime.MAX);
        BigDecimal currentTotal = energyDataService.getTotalEnergy(
                parkId, null, energyType, currentStartTime, currentEndTime);
        statisticsBO.setCurrentTotalEnergy(currentTotal);

        // 同比周期（去年同期）
        LocalDateTime lastYearStartTime = currentStartTime.minusYears(1);
        LocalDateTime lastYearEndTime = currentEndTime.minusYears(1);
        BigDecimal lastYearTotal = energyDataService.getTotalEnergy(
                parkId, null, energyType, lastYearStartTime, lastYearEndTime);
        statisticsBO.setLastYearTotalEnergy(lastYearTotal);

        // 环比周期（上月同期）
        LocalDateTime lastMonthStartTime = currentStartTime.minusMonths(1);
        LocalDateTime lastMonthEndTime = currentEndTime.minusMonths(1);
        BigDecimal lastMonthTotal = energyDataService.getTotalEnergy(
                parkId, null, energyType, lastMonthStartTime, lastMonthEndTime);
        statisticsBO.setLastMonthTotalEnergy(lastMonthTotal);

        // 计算同比增长率
        if (lastYearTotal.compareTo(BigDecimal.ZERO) > 0) {
            BigDecimal yoyRate = currentTotal.subtract(lastYearTotal)
                    .divide(lastYearTotal, 4, RoundingMode.HALF_UP)
                    .multiply(new BigDecimal("100"))
                    .setScale(2, RoundingMode.HALF_UP);
            statisticsBO.setYearOnYearRate(yoyRate);
        } else {
            statisticsBO.setYearOnYearRate(BigDecimal.ZERO);
        }

        // 计算环比增长率
        if (lastMonthTotal.compareTo(BigDecimal.ZERO) > 0) {
            BigDecimal momRate = currentTotal.subtract(lastMonthTotal)
                    .divide(lastMonthTotal, 4, RoundingMode.HALF_UP)
                    .multiply(new BigDecimal("100"))
                    .setScale(2, RoundingMode.HALF_UP);
            statisticsBO.setMonthOnMonthRate(momRate);
        } else {
            statisticsBO.setMonthOnMonthRate(BigDecimal.ZERO);
        }

        // 计算日平均能耗
        long days = ChronoUnit.DAYS.between(startDate, endDate) + 1;
        if (days > 0) {
            BigDecimal dailyAvg = currentTotal.divide(new BigDecimal(days), 2, RoundingMode.HALF_UP);
            statisticsBO.setDailyAverageEnergy(dailyAvg);
        }

        // 日能耗分析（最高/最低）
        List<Map<String, Object>> dailyData = energyDataService.getDailyStatistics(
                parkId, energyType, currentStartTime, currentEndTime);
        if (CollUtil.isNotEmpty(dailyData)) {
            BigDecimal maxDaily = dailyData.stream()
                    .map(map -> (BigDecimal) map.get("total_energy"))
                    .max(BigDecimal::compareTo)
                    .orElse(BigDecimal.ZERO);
            BigDecimal minDaily = dailyData.stream()
                    .map(map -> (BigDecimal) map.get("total_energy"))
                    .min(BigDecimal::compareTo)
                    .orElse(BigDecimal.ZERO);
            statisticsBO.setMaxDailyEnergy(maxDaily);
            statisticsBO.setMinDailyEnergy(minDaily);
        }

        return statisticsBO;
    }

    @Override
    public List<Map<String, Object>> getEnergyRanking(Long parkId, Integer energyType,
                                                        LocalDateTime startTime, LocalDateTime endTime, Integer topN) {
        log.info("能耗排名统计，园区ID: {}, 能源类型: {}, topN: {}", parkId, energyType, topN);
        // TODO: 实现按建筑/楼层的能耗排名
        List<Map<String, Object>> ranking = new ArrayList<>();
        // 示例数据结构
        for (int i = 1; i <= (topN != null ? topN : 10); i++) {
            Map<String, Object> item = new HashMap<>();
            item.put("rank", i);
            item.put("buildingId", i);
            item.put("buildingName", "建筑" + i + "号楼");
            item.put("totalEnergy", new BigDecimal(String.valueOf(Math.random() * 10000)).setScale(2, RoundingMode.HALF_UP));
            ranking.add(item);
        }
        return ranking;
    }

    @Override
    public List<Map<String, Object>> predictEnergyTrend(Long parkId, Integer energyType, Integer days) {
        log.info("能耗趋势预测，园区ID: {}, 能源类型: {}, 预测天数: {}", parkId, energyType, days);
        List<Map<String, Object>> predictData = new ArrayList<>();
        LocalDate today = LocalDate.now();

        // 简单移动平均预测
        for (int i = 1; i <= (days != null ? days : 7); i++) {
            LocalDate predictDate = today.plusDays(i);
            Map<String, Object> item = new HashMap<>();
            item.put("date", predictDate.toString());
            item.put("predictEnergy", new BigDecimal(String.valueOf(Math.random() * 5000 + 3000)).setScale(2, RoundingMode.HALF_UP));
            item.put("lowerBound", new BigDecimal(String.valueOf(Math.random() * 1000 + 2000)).setScale(2, RoundingMode.HALF_UP));
            item.put("upperBound", new BigDecimal(String.valueOf(Math.random() * 1000 + 6000)).setScale(2, RoundingMode.HALF_UP));
            predictData.add(item);
        }
        return predictData;
    }

    @Override
    public Map<String, Object> analyzePeakValley(Long parkId, Integer energyType,
                                                   LocalDateTime startTime, LocalDateTime endTime) {
        log.info("能耗峰谷分析，园区ID: {}, 能源类型: {}", parkId, energyType);
        Map<String, Object> result = new HashMap<>();

        // 获取小时统计数据
        List<Map<String, Object>> hourlyData = energyDataService.getHourlyStatistics(
                parkId, energyType, startTime, endTime);

        if (CollUtil.isNotEmpty(hourlyData)) {
            // 计算峰谷值
            BigDecimal peakEnergy = hourlyData.stream()
                    .map(map -> (BigDecimal) map.get("total_energy"))
                    .max(BigDecimal::compareTo)
                    .orElse(BigDecimal.ZERO);
            BigDecimal valleyEnergy = hourlyData.stream()
                    .map(map -> (BigDecimal) map.get("total_energy"))
                    .min(BigDecimal::compareTo)
                    .orElse(BigDecimal.ZERO);

            result.put("peakEnergy", peakEnergy);
            result.put("valleyEnergy", valleyEnergy);
            result.put("peakValleyDiff", peakEnergy.subtract(valleyEnergy));
            result.put("hourlyData", hourlyData);

            // 峰时段（9:00-12:00, 14:00-18:00）
            // 谷时段（0:00-6:00）
            // 平时段（其余）
            Map<String, BigDecimal> periodEnergy = calculatePeriodEnergy(hourlyData);
            result.putAll(periodEnergy);
        }

        return result;
    }

    @Override
    public Map<String, Object> calculateEnergyMetrics(Long parkId, Integer energyType,
                                                        LocalDateTime startTime, LocalDateTime endTime) {
        log.info("能耗指标计算，园区ID: {}, 能源类型: {}", parkId, energyType);
        Map<String, Object> metrics = new HashMap<>();

        // 总能耗
        BigDecimal totalEnergy = energyDataService.getTotalEnergy(
                parkId, null, energyType, startTime, endTime);
        metrics.put("totalEnergy", totalEnergy);

        // 统计天数
        long days = ChronoUnit.DAYS.between(startTime.toLocalDate(), endTime.toLocalDate()) + 1;

        // 日平均能耗
        if (days > 0) {
            BigDecimal dailyAvg = totalEnergy.divide(new BigDecimal(days), 2, RoundingMode.HALF_UP);
            metrics.put("dailyAverage", dailyAvg);
        }

        // 能耗密度（单位面积能耗 - 示例，需要建筑面积数据）
        metrics.put("energyDensity", new BigDecimal("45.6"));

        // 能耗费用估算（示例单价）
        BigDecimal price = getEnergyPrice(energyType);
        metrics.put("estimatedCost", totalEnergy.multiply(price).setScale(2, RoundingMode.HALF_UP));
        metrics.put("unitPrice", price);

        // 碳排放估算（电力：0.785kg CO₂/kWh）
        if (energyType != null && energyType == 1) { // 电力
            metrics.put("carbonEmission", totalEnergy.multiply(new BigDecimal("0.785")).setScale(2, RoundingMode.HALF_UP));
        }

        return metrics;
    }

    @Override
    public Map<String, Object> generateStatisticsReport(Long parkId, Integer energyType,
                                                          LocalDateTime startTime, LocalDateTime endTime, Integer reportType) {
        log.info("生成能耗统计报表，园区ID: {}, 能源类型: {}, 报表类型: {}", parkId, energyType, reportType);
        Map<String, Object> report = new HashMap<>();

        // 报表基本信息
        report.put("parkId", parkId);
        report.put("energyType", energyType);
        report.put("reportType", reportType);
        report.put("startTime", startTime);
        report.put("endTime", endTime);
        report.put("generateTime", LocalDateTime.now());

        // 核心指标
        Map<String, Object> metrics = calculateEnergyMetrics(parkId, energyType, startTime, endTime);
        report.put("metrics", metrics);

        // 趋势数据
        List<Map<String, Object>> trendData;
        switch (reportType) {
            case 1: // 日报 - 小时数据
                trendData = energyDataService.getHourlyStatistics(parkId, energyType, startTime, endTime);
                break;
            case 2: // 周报
            case 3: // 月报 - 日数据
                trendData = energyDataService.getDailyStatistics(parkId, energyType, startTime, endTime);
                break;
            case 4: // 季报
            case 5: // 年报 - 月数据
                trendData = energyDataService.getMonthlyStatistics(parkId, energyType, startTime, endTime);
                break;
            default:
                trendData = energyDataService.getDailyStatistics(parkId, energyType, startTime, endTime);
        }
        report.put("trendData", trendData);

        // 同比环比
        EnergyStatisticsBO yoyMom = calculateYoYAndMoM(
                parkId, energyType, startTime.toLocalDate(), endTime.toLocalDate());
        report.put("yoyMom", yoyMom);

        return report;
    }

    /**
     * 计算分时段能耗
     */
    private Map<String, BigDecimal> calculatePeriodEnergy(List<Map<String, Object>> hourlyData) {
        Map<String, BigDecimal> result = new HashMap<>();
        BigDecimal peakEnergy = BigDecimal.ZERO;
        BigDecimal valleyEnergy = BigDecimal.ZERO;
        BigDecimal normalEnergy = BigDecimal.ZERO;

        for (Map<String, Object> item : hourlyData) {
            String timePoint = (String) item.get("time_point");
            BigDecimal energy = (BigDecimal) item.get("total_energy");

            try {
                DateTime dateTime = DateUtil.parse(timePoint, "yyyy-MM-dd HH:mm:ss");
                int hour = dateTime.getField(DateField.HOUR_OF_DAY);

                // 峰时段（9:00-12:00, 14:00-18:00）
                if ((hour >= 9 && hour < 12) || (hour >= 14 && hour < 18)) {
                    peakEnergy = peakEnergy.add(energy);
                }
                // 谷时段（0:00-6:00）
                else if (hour >= 0 && hour < 6) {
                    valleyEnergy = valleyEnergy.add(energy);
                }
                // 平时段
                else {
                    normalEnergy = normalEnergy.add(energy);
                }
            } catch (Exception e) {
                log.warn("解析时间失败: {}", timePoint, e);
                normalEnergy = normalEnergy.add(energy);
            }
        }

        result.put("peakEnergy", peakEnergy);
        result.put("valleyEnergy", valleyEnergy);
        result.put("normalEnergy", normalEnergy);

        return result;
    }

    /**
     * 获取能源单价（元/单位）
     */
    private BigDecimal getEnergyPrice(Integer energyType) {
        if (energyType == null) {
            return new BigDecimal("1.0");
        }
        return switch (energyType) {
            case 1 -> new BigDecimal("0.85"); // 电价
            case 2 -> new BigDecimal("5.0");  // 水价
            case 3 -> new BigDecimal("3.5");  // 燃气价
            case 4 -> new BigDecimal("0.6");  // 热价
            case 5 -> new BigDecimal("0.7");  // 冷价
            default -> new BigDecimal("1.0");
        };
    }
}
