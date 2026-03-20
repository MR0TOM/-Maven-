package com.smartpark.statistics.task;

import com.smartpark.statistics.service.EnergyStatisticsService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Slf4j
@Component
@RequiredArgsConstructor
public class StatisticsScheduledTask {

    private final EnergyStatisticsService energyStatisticsService;

    @Scheduled(cron = "0 5 * * * ?")
    public void generateHourlyStatistics() {
        log.info("开始执行小时统计任务");
        LocalDate today = LocalDate.now();
        int currentHour = java.time.LocalTime.now().getHour();
        
        if (currentHour > 0) {
            energyStatisticsService.generateHourlyStatistics(today, currentHour - 1);
        }
        log.info("小时统计任务执行完成");
    }

    @Scheduled(cron = "0 10 0 * * ?")
    public void generateDailyStatistics() {
        log.info("开始执行日统计任务");
        LocalDate yesterday = LocalDate.now().minusDays(1);
        energyStatisticsService.generateDailyStatistics(yesterday);
        log.info("日统计任务执行完成");
    }

    @Scheduled(cron = "0 30 0 1 * ?")
    public void generateMonthlyStatistics() {
        log.info("开始执行月统计任务");
        LocalDate lastMonth = LocalDate.now().minusMonths(1);
        energyStatisticsService.generateMonthlyStatistics(lastMonth.getYear(), lastMonth.getMonthValue());
        log.info("月统计任务执行完成");
    }
}
