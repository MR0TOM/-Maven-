package com.smartpark.app.controller;

import com.smartpark.biz.service.DeviceManageService;
import com.smartpark.common.result.Result;
import com.smartpark.entity.dto.DeviceControlDTO;
import com.smartpark.entity.model.DeviceInfo;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 设备管理控制器
 */
@RestController
@RequestMapping("/api/device")
@RequiredArgsConstructor
public class DeviceController {

    private final DeviceManageService deviceManageService;

    /**
     * 获取所有设备
     */
    @GetMapping("/list")
    public Result<List<DeviceInfo>> listDevices() {
        return Result.success(deviceManageService.list());
    }

    /**
     * 获取设备详情
     */
    @GetMapping("/{id}")
    public Result<DeviceInfo> getDevice(@PathVariable Long id) {
        return Result.success(deviceManageService.getById(id));
    }

    /**
     * 添加设备
     */
    @PostMapping
    public Result<Void> addDevice(@RequestBody DeviceInfo deviceInfo) {
        deviceManageService.save(deviceInfo);
        return Result.success();
    }

    /**
     * 更新设备
     */
    @PutMapping("/{id}")
    public Result<Void> updateDevice(@PathVariable Long id, @RequestBody DeviceInfo deviceInfo) {
        deviceInfo.setId(id);
        deviceManageService.updateById(deviceInfo);
        return Result.success();
    }

    /**
     * 删除设备
     */
    @DeleteMapping("/{id}")
    public Result<Void> deleteDevice(@PathVariable Long id) {
        deviceManageService.removeById(id);
        return Result.success();
    }

    /**
     * 设备控制
     */
    @PostMapping("/control")
    public Result<Void> controlDevice(@RequestBody DeviceControlDTO controlDTO) {
        // TODO: 实现设备控制
        return Result.success();
    }
}
