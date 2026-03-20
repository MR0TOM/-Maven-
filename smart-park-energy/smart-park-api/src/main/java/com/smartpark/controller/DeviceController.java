package com.smartpark.controller;

import com.smartpark.common.result.PageResult;
import com.smartpark.common.result.Result;
import com.smartpark.model.dto.DeviceDTO;
import com.smartpark.model.vo.DeviceVO;
import com.smartpark.service.DeviceService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "设备管理", description = "设备信息管理接口")
@RestController
@RequestMapping("/api/device")
@RequiredArgsConstructor
public class DeviceController {

    private final DeviceService deviceService;

    @Operation(summary = "根据ID查询设备信息")
    @GetMapping("/{id}")
    public Result<DeviceVO> getById(@PathVariable Long id) {
        return Result.success(deviceService.getDetailById(id));
    }

    @Operation(summary = "分页查询设备列表")
    @GetMapping("/page")
    public Result<PageResult<DeviceVO>> getPage(
            @Parameter(description = "页码") @RequestParam(defaultValue = "1") Integer pageNum,
            @Parameter(description = "每页大小") @RequestParam(defaultValue = "10") Integer pageSize,
            @Parameter(description = "建筑ID") @RequestParam(required = false) Long buildingId,
            @Parameter(description = "设备类型") @RequestParam(required = false) String deviceType,
            @Parameter(description = "状态") @RequestParam(required = false) String status) {
        return Result.success(deviceService.getPage(pageNum, pageSize, buildingId, deviceType, status));
    }

    @Operation(summary = "根据建筑ID查询设备列表")
    @GetMapping("/building/{buildingId}")
    public Result<List<DeviceVO>> getByBuildingId(@PathVariable Long buildingId) {
        return Result.success(deviceService.getByBuildingId(buildingId));
    }

    @Operation(summary = "新增设备")
    @PostMapping
    public Result<Long> save(@Valid @RequestBody DeviceDTO dto) {
        return Result.success(deviceService.save(dto));
    }

    @Operation(summary = "更新设备")
    @PutMapping
    public Result<Void> update(@Valid @RequestBody DeviceDTO dto) {
        deviceService.update(dto);
        return Result.success();
    }

    @Operation(summary = "删除设备")
    @DeleteMapping("/{id}")
    public Result<Void> delete(@PathVariable Long id) {
        deviceService.deleteById(id);
        return Result.success();
    }

    @Operation(summary = "批量删除设备")
    @DeleteMapping("/batch")
    public Result<Void> deleteBatch(@RequestBody List<Long> ids) {
        deviceService.deleteBatch(ids);
        return Result.success();
    }

    @Operation(summary = "更新设备状态")
    @PutMapping("/{id}/status")
    public Result<Void> updateStatus(
            @PathVariable Long id,
            @Parameter(description = "状态") @RequestParam String status) {
        deviceService.updateStatus(id, status);
        return Result.success();
    }
}
