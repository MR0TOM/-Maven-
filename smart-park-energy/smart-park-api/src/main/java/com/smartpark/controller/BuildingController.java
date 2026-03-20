package com.smartpark.controller;

import com.smartpark.common.result.PageResult;
import com.smartpark.common.result.Result;
import com.smartpark.model.dto.BuildingDTO;
import com.smartpark.model.vo.BuildingVO;
import com.smartpark.service.BuildingService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "建筑管理", description = "建筑信息管理接口")
@RestController
@RequestMapping("/api/building")
@RequiredArgsConstructor
public class BuildingController {

    private final BuildingService buildingService;

    @Operation(summary = "根据ID查询建筑信息")
    @GetMapping("/{id}")
    public Result<BuildingVO> getById(@PathVariable Long id) {
        return Result.success(buildingService.getDetailById(id));
    }

    @Operation(summary = "分页查询建筑列表")
    @GetMapping("/page")
    public Result<PageResult<BuildingVO>> getPage(
            @Parameter(description = "页码") @RequestParam(defaultValue = "1") Integer pageNum,
            @Parameter(description = "每页大小") @RequestParam(defaultValue = "10") Integer pageSize,
            @Parameter(description = "建筑名称") @RequestParam(required = false) String buildingName,
            @Parameter(description = "状态") @RequestParam(required = false) String status) {
        return Result.success(buildingService.getPage(pageNum, pageSize, buildingName, status));
    }

    @Operation(summary = "查询建筑列表")
    @GetMapping("/list")
    public Result<List<BuildingVO>> getList(
            @Parameter(description = "建筑名称") @RequestParam(required = false) String buildingName,
            @Parameter(description = "状态") @RequestParam(required = false) String status) {
        return Result.success(buildingService.getList(buildingName, status));
    }

    @Operation(summary = "新增建筑")
    @PostMapping
    public Result<Long> save(@Valid @RequestBody BuildingDTO dto) {
        return Result.success(buildingService.save(dto));
    }

    @Operation(summary = "更新建筑")
    @PutMapping
    public Result<Void> update(@Valid @RequestBody BuildingDTO dto) {
        buildingService.update(dto);
        return Result.success();
    }

    @Operation(summary = "删除建筑")
    @DeleteMapping("/{id}")
    public Result<Void> delete(@PathVariable Long id) {
        buildingService.deleteById(id);
        return Result.success();
    }

    @Operation(summary = "批量删除建筑")
    @DeleteMapping("/batch")
    public Result<Void> deleteBatch(@RequestBody List<Long> ids) {
        buildingService.deleteBatch(ids);
        return Result.success();
    }

    @Operation(summary = "更新建筑状态")
    @PutMapping("/{id}/status")
    public Result<Void> updateStatus(
            @PathVariable Long id,
            @Parameter(description = "状态") @RequestParam String status) {
        buildingService.updateStatus(id, status);
        return Result.success();
    }
}
