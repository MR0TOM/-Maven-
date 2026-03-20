package com.smartpark.app.controller;

import com.smartpark.biz.service.EnergyMeterService;
import com.smartpark.biz.service.EnergyRecordService;
import com.smartpark.common.result.PageResult;
import com.smartpark.common.result.Result;
import com.smartpark.entity.model.EnergyMeter;
import com.smartpark.entity.model.EnergyRecord;
import com.smartpark.entity.param.EnergyQueryParam;
import lombok.RequiredArgsConstructor;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.web.bind.annotation.*;

/**
 * 能耗管理控制器
 */
@RestController
@RequestMapping("/api/energy")
@RequiredArgsConstructor
public class EnergyController {

    private final EnergyMeterService energyMeterService;
    private final EnergyRecordService energyRecordService;

    /**
     * 分页查询计量设备
     */
    @GetMapping("/meters")
    public Result<PageResult<EnergyMeter>> listMeters(@ParameterObject EnergyQueryParam param) {
        return Result.success(PageResult.of(
                param.getPage(),
                param.getSize(),
                energyMeterService.count(),
                energyMeterService.list()
        ));
    }

    /**
     * 获取计量设备详情
     */
    @GetMapping("/meters/{id}")
    public Result<EnergyMeter> getMeter(@PathVariable Long id) {
        return Result.success(energyMeterService.getById(id));
    }

    /**
     * 分页查询能耗记录
     */
    @GetMapping("/records")
    public Result<PageResult<EnergyRecord>> listRecords(@ParameterObject EnergyQueryParam param) {
        return Result.success(PageResult.of(
                param.getPage(),
                param.getSize(),
                energyRecordService.count(),
                energyRecordService.list()
        ));
    }
}
