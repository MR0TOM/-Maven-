package com.smartpark.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.smartpark.common.constant.CommonConstants;
import com.smartpark.common.exception.BusinessException;
import com.smartpark.dal.mapper.BuildingMapper;
import com.smartpark.dal.mapper.DeviceMapper;
import com.smartpark.model.dto.BuildingDTO;
import com.smartpark.model.entity.Building;
import com.smartpark.model.entity.Device;
import com.smartpark.model.vo.BuildingVO;
import com.smartpark.common.result.PageResult;
import com.smartpark.service.BuildingService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BuildingServiceImpl implements BuildingService {

    private final BuildingMapper buildingMapper;
    private final DeviceMapper deviceMapper;

    @Override
    public Building getById(Long id) {
        return buildingMapper.selectById(id);
    }

    @Override
    public BuildingVO getDetailById(Long id) {
        Building building = buildingMapper.selectById(id);
        if (building == null) {
            throw new BusinessException("建筑信息不存在");
        }
        BuildingVO vo = new BuildingVO();
        BeanUtils.copyProperties(building, vo);
        
        LambdaQueryWrapper<Device> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Device::getBuildingId, id)
               .eq(Device::getDeleteFlag, CommonConstants.DELETE_FLAG_NO);
        Long deviceCount = deviceMapper.selectCount(wrapper);
        vo.setDeviceCount(deviceCount.intValue());
        
        return vo;
    }

    @Override
    public PageResult<BuildingVO> getPage(Integer pageNum, Integer pageSize, String buildingName, String status) {
        Page<Building> page = new Page<>(pageNum, pageSize);
        LambdaQueryWrapper<Building> wrapper = new LambdaQueryWrapper<>();
        wrapper.like(StringUtils.hasText(buildingName), Building::getBuildingName, buildingName)
               .eq(StringUtils.hasText(status), Building::getStatus, status)
               .eq(Building::getDeleteFlag, CommonConstants.DELETE_FLAG_NO)
               .orderByDesc(Building::getCreateTime);
        
        Page<Building> result = buildingMapper.selectPage(page, wrapper);
        
        List<BuildingVO> voList = result.getRecords().stream()
                .map(this::convertToVO)
                .collect(Collectors.toList());
        
        return PageResult.of(result.getTotal(), pageNum, pageSize, voList);
    }

    @Override
    public List<BuildingVO> getList(String buildingName, String status) {
        LambdaQueryWrapper<Building> wrapper = new LambdaQueryWrapper<>();
        wrapper.like(StringUtils.hasText(buildingName), Building::getBuildingName, buildingName)
               .eq(StringUtils.hasText(status), Building::getStatus, status)
               .eq(Building::getDeleteFlag, CommonConstants.DELETE_FLAG_NO)
               .orderByDesc(Building::getCreateTime);
        
        List<Building> list = buildingMapper.selectList(wrapper);
        return list.stream().map(this::convertToVO).collect(Collectors.toList());
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Long save(BuildingDTO dto) {
        LambdaQueryWrapper<Building> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Building::getBuildingCode, dto.getBuildingCode())
               .eq(Building::getDeleteFlag, CommonConstants.DELETE_FLAG_NO);
        if (buildingMapper.selectCount(wrapper) > 0) {
            throw new BusinessException("建筑编码已存在");
        }
        
        Building building = new Building();
        BeanUtils.copyProperties(dto, building);
        building.setStatus(CommonConstants.STATUS_ENABLE);
        buildingMapper.insert(building);
        return building.getId();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void update(BuildingDTO dto) {
        Building existBuilding = buildingMapper.selectById(dto.getId());
        if (existBuilding == null) {
            throw new BusinessException("建筑信息不存在");
        }
        
        if (!existBuilding.getBuildingCode().equals(dto.getBuildingCode())) {
            LambdaQueryWrapper<Building> wrapper = new LambdaQueryWrapper<>();
            wrapper.eq(Building::getBuildingCode, dto.getBuildingCode())
                   .ne(Building::getId, dto.getId())
                   .eq(Building::getDeleteFlag, CommonConstants.DELETE_FLAG_NO);
            if (buildingMapper.selectCount(wrapper) > 0) {
                throw new BusinessException("建筑编码已存在");
            }
        }
        
        Building building = new Building();
        BeanUtils.copyProperties(dto, building);
        buildingMapper.updateById(building);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteById(Long id) {
        Building building = new Building();
        building.setId(id);
        building.setDeleteFlag(CommonConstants.DELETE_FLAG_YES);
        buildingMapper.updateById(building);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteBatch(List<Long> ids) {
        ids.forEach(this::deleteById);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateStatus(Long id, String status) {
        Building building = new Building();
        building.setId(id);
        building.setStatus(status);
        buildingMapper.updateById(building);
    }

    private BuildingVO convertToVO(Building building) {
        BuildingVO vo = new BuildingVO();
        BeanUtils.copyProperties(building, vo);
        return vo;
    }
}
