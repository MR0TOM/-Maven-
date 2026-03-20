package com.smartpark.statistics.service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Map;

/**
 * 趋势分析服务接口
 */
public interface TrendAnalysisService {

    /**
     * 同比分析
     */
    Map<String, BigDecimal> yearOverYearAnalysis(Long meterId, LocalDate currentDate);

    /**
     * 环比分析
     */
    Map<String, BigDecimal> monthOverMonthAnalysis(Long meterId, LocalDate currentDate);

    /**
     * 预测下周期能耗
     */
    BigDecimal predictNextPeriod(Long meterId, Integer periodType);
}
