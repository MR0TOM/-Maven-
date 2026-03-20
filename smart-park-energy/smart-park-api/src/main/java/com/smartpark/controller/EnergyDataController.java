package com.smartpark.controller;

import com.smartpark.common.result.PageResult;
import com.smartpark.common.result.Result;
import com.smartpark.model.dto.EnergyDataQueryDTO;
import com.smartpark.model.vo.EnergyDataVO;
import com.smartpark.service.EnergyDataService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "能耗数据管理", description = "能耗数据管理接口")
@RestController
@RequestMapping("/api/energy/data")
@RequiredArgsConstructor
public class EnergyDataController {

    private final EnergyDataService energyDataService;

    @Operation(summary = "根据ID查询能耗数据")
    @GetMapping("/{id}")
    public Result<EnergyDataVO> getById(@PathVariable Long id) {
        return Result.success(null);
    }

    @Operation(summary = "分页查询能耗数据")
    @GetMapping("/page")
    public Result<PageResult<EnergyDataVO>> getPage(EnergyDataQueryDTO query) {
        return Result.success(energyDataService.getPage(query));
    }

    @Operation(summary = "根据设备ID查询能耗数据")
    @GetMapping("/device/{deviceId}")
    public Result<List<EnergyDataVO>> getByDeviceId(
            @PathVariable Long deviceId,
            @Parameter(description = "数据条数") @RequestParam(defaultValue = "100") Integer limit) {
        return Result.success(energyDataService.getByDeviceId(deviceId, limit));
    }
}
