package com.smartpark.statistics.scheduler;

import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * 统计定时任务
 */
@Slf4j
@Component
public class StatisticsScheduler {

    /**
     * 每小时执行统计
     */
    @Scheduled(cron = "0 0 * * * ?")
    public void hourlyStatistics() {
        log.info("执行小时统计任务");
        // TODO: 实现小时统计逻辑
    }

    /**
     * 每日凌晨执行日统计
     */
    @Scheduled(cron = "0 0 0 * * ?")
    public void dailyStatistics() {
        log.info("执行日统计任务");
        // TODO: 实现日统计逻辑
    }

    /**
     * 每月1号凌晨执行月统计
     */
    @Scheduled(cron = "0 0 0 1 * ?")
    public void monthlyStatistics() {
        log.info("执行月统计任务");
        // TODO: 实现月统计逻辑
    }
}
