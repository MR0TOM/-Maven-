package com.smartpark.statistics.scheduler;

import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * 报表定时任务
 */
@Slf4j
@Component
public class ReportScheduler {

    /**
     * 每日生成日报
     */
    @Scheduled(cron = "0 30 1 * * ?")
    public void generateDailyReport() {
        log.info("生成日报");
        // TODO: 实现日报生成逻辑
    }

    /**
     * 每月1号生成月报
     */
    @Scheduled(cron = "0 0 2 1 * ?")
    public void generateMonthlyReport() {
        log.info("生成月报");
        // TODO: 实现月报生成逻辑
    }
}
