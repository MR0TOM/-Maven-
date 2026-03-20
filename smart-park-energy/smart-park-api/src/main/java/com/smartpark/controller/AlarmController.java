package com.smartpark.controller;

import com.smartpark.common.result.PageResult;
import com.smartpark.common.result.Result;
import com.smartpark.model.vo.AlarmRecordVO;
import com.smartpark.service.AlarmService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "告警管理", description = "告警记录管理接口")
@RestController
@RequestMapping("/api/alarm")
@RequiredArgsConstructor
public class AlarmController {

    private final AlarmService alarmService;

    @Operation(summary = "根据ID查询告警记录")
    @GetMapping("/{id}")
    public Result<AlarmRecordVO> getById(@PathVariable Long id) {
        return Result.success(alarmService.getDetailById(id));
    }

    @Operation(summary = "分页查询告警记录")
    @GetMapping("/page")
    public Result<PageResult<AlarmRecordVO>> getPage(
            @Parameter(description = "页码") @RequestParam(defaultValue = "1") Integer pageNum,
            @Parameter(description = "每页大小") @RequestParam(defaultValue = "10") Integer pageSize,
            @Parameter(description = "建筑ID") @RequestParam(required = false) Long buildingId,
            @Parameter(description = "设备ID") @RequestParam(required = false) Long deviceId,
            @Parameter(description = "告警级别") @RequestParam(required = false) String alarmLevel,
            @Parameter(description = "告警状态") @RequestParam(required = false) String alarmStatus) {
        return Result.success(alarmService.getPage(pageNum, pageSize, buildingId, deviceId, alarmLevel, alarmStatus));
    }

    @Operation(summary = "查询未处理告警列表")
    @GetMapping("/unhandled")
    public Result<List<AlarmRecordVO>> getUnhandledAlarms() {
        return Result.success(alarmService.getUnhandledAlarms());
    }

    @Operation(summary = "处理告警")
    @PutMapping("/{id}/handle")
    public Result<Void> handleAlarm(
            @PathVariable Long id,
            @Parameter(description = "处理结果") @RequestParam String handleResult,
            @Parameter(description = "备注") @RequestParam(required = false) String remark) {
        alarmService.handleAlarm(id, handleResult, remark);
        return Result.success();
    }

    @Operation(summary = "批量删除告警记录")
    @DeleteMapping("/batch")
    public Result<Void> deleteBatch(@RequestBody List<Long> ids) {
        alarmService.deleteBatch(ids);
        return Result.success();
    }
}
