package com.smartpark.api.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.smartpark.common.utils.Result;
import com.smartpark.model.dto.EnergyDataDTO;
import com.smartpark.model.query.EnergyDataQuery;
import com.smartpark.model.vo.EnergyDataVO;
import com.smartpark.service.EnergyDataService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

/**
 * 能耗数据控制器
 */
@Slf4j
@RestController
@RequestMapping("/energy/data")
@RequiredArgsConstructor
@Tag(name = "能耗数据管理", description = "能耗数据的增删改查、统计分析")
public class EnergyDataController {

    private final EnergyDataService energyDataService;

    /**
     * 新增能耗数据
     */
    @PostMapping
    @Operation(summary = "新增能耗数据", description = "单条新增能耗采集数据")
    public Result<Void> addEnergyData(@Valid @RequestBody EnergyDataDTO energyDataDTO) {
        log.info("新增能耗数据，设备ID: {}", energyDataDTO.getDeviceId());
        boolean result = energyDataService.addEnergyData(energyDataDTO);
        return Result.status(result);
    }

    /**
     * 批量新增能耗数据
     */
    @PostMapping("/batch")
    @Operation(summary = "批量新增能耗数据", description = "批量新增能耗采集数据")
    public Result<Void> batchAddEnergyData(@Valid @RequestBody List<EnergyDataDTO> energyDataDTOList) {
        log.info("批量新增能耗数据，数量: {}", energyDataDTOList.size());
        boolean result = energyDataService.batchAddEnergyData(energyDataDTOList);
        return Result.status(result);
    }

    /**
     * 分页查询能耗数据
     */
    @PostMapping("/page")
    @Operation(summary = "分页查询能耗数据", description = "根据条件分页查询能耗数据列表")
    public Result<Page<EnergyDataVO>> queryEnergyDataPage(@RequestBody EnergyDataQuery query) {
        log.info("分页查询能耗数据，查询条件: {}", query);
        Page<EnergyDataVO> page = energyDataService.queryEnergyDataPage(query);
        return Result.success(page);
    }

    /**
     * 查询能耗数据详情
     */
    @GetMapping("/{id}")
    @Operation(summary = "查询能耗数据详情", description = "根据ID查询能耗数据详情")
    @Parameter(name = "id", description = "能耗数据ID", required = true)
    public Result<EnergyDataVO> getEnergyDataDetail(@PathVariable Long id) {
        log.info("查询能耗数据详情，ID: {}", id);
        EnergyDataVO vo = energyDataService.getEnergyDataDetail(id);
        return Result.success(vo);
    }

    /**
     * 统计总能耗
     */
    @GetMapping("/total")
    @Operation(summary = "统计总能耗", description = "根据条件统计总能耗")
    public Result<BigDecimal> getTotalEnergy(
            @Parameter(description = "园区ID") @RequestParam(required = false) Long parkId,
            @Parameter(description = "建筑ID") @RequestParam(required = false) Long buildingId,
            @Parameter(description = "能源类型") @RequestParam(required = false) Integer energyType,
            @Parameter(description = "开始时间") @RequestParam(required = false) LocalDateTime startTime,
            @Parameter(description = "结束时间") @RequestParam(required = false) LocalDateTime endTime) {
        log.info("统计总能耗，园区ID: {}, 建筑ID: {}, 能源类型: {}", parkId, buildingId, energyType);
        BigDecimal totalEnergy = energyDataService.getTotalEnergy(parkId, buildingId, energyType, startTime, endTime);
        return Result.success(totalEnergy);
    }

    /**
     * 按小时统计能耗
     */
    @GetMapping("/statistics/hourly")
    @Operation(summary = "按小时统计能耗", description = "按小时维度统计能耗数据")
    public Result<List<Map<String, Object>>> getHourlyStatistics(
            @Parameter(description = "园区ID") @RequestParam(required = false) Long parkId,
            @Parameter(description = "能源类型") @RequestParam(required = false) Integer energyType,
            @Parameter(description = "开始时间") @RequestParam(required = false) LocalDateTime startTime,
            @Parameter(description = "结束时间") @RequestParam(required = false) LocalDateTime endTime) {
        log.info("按小时统计能耗，园区ID: {}, 能源类型: {}", parkId, energyType);
        List<Map<String, Object>> list = energyDataService.getHourlyStatistics(parkId, energyType, startTime, endTime);
        return Result.success(list);
    }

    /**
     * 按天统计能耗
     */
    @GetMapping("/statistics/daily")
    @Operation(summary = "按天统计能耗", description = "按天维度统计能耗数据")
    public Result<List<Map<String, Object>>> getDailyStatistics(
            @Parameter(description = "园区ID") @RequestParam(required = false) Long parkId,
            @Parameter(description = "能源类型") @RequestParam(required = false) Integer energyType,
            @Parameter(description = "开始时间") @RequestParam(required = false) LocalDateTime startTime,
            @Parameter(description = "结束时间") @RequestParam(required = false) LocalDateTime endTime) {
        log.info("按天统计能耗，园区ID: {}, 能源类型: {}", parkId, energyType);
        List<Map<String, Object>> list = energyDataService.getDailyStatistics(parkId, energyType, startTime, endTime);
        return Result.success(list);
    }

    /**
     * 按月统计能耗
     */
    @GetMapping("/statistics/monthly")
    @Operation(summary = "按月统计能耗", description = "按月维度统计能耗数据")
    public Result<List<Map<String, Object>>> getMonthlyStatistics(
            @Parameter(description = "园区ID") @RequestParam(required = false) Long parkId,
            @Parameter(description = "能源类型") @RequestParam(required = false) Integer energyType,
            @Parameter(description = "开始时间") @RequestParam(required = false) LocalDateTime startTime,
            @Parameter(description = "结束时间") @RequestParam(required = false) LocalDateTime endTime) {
        log.info("按月统计能耗，园区ID: {}, 能源类型: {}", parkId, energyType);
        List<Map<String, Object>> list = energyDataService.getMonthlyStatistics(parkId, energyType, startTime, endTime);
        return Result.success(list);
    }

    /**
     * 发送能耗数据到Kafka
     */
    @PostMapping("/kafka/send")
    @Operation(summary = "发送能耗数据到Kafka", description = "将能耗数据发送到Kafka消息队列")
    public Result<Void> sendEnergyDataToKafka(@Valid @RequestBody EnergyDataDTO energyDataDTO) {
        log.info("发送能耗数据到Kafka，设备ID: {}", energyDataDTO.getDeviceId());
        energyDataService.sendEnergyDataToKafka(energyDataDTO);
        return Result.success();
    }
}
