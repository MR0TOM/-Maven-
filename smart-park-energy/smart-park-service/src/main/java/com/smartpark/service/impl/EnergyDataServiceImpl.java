package com.smartpark.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.smartpark.common.constant.CommonConstants;
import com.smartpark.dal.mapper.BuildingMapper;
import com.smartpark.dal.mapper.DeviceMapper;
import com.smartpark.dal.mapper.EnergyDataMapper;
import com.smartpark.model.dto.EnergyDataQueryDTO;
import com.smartpark.model.entity.Building;
import com.smartpark.model.entity.Device;
import com.smartpark.model.entity.EnergyData;
import com.smartpark.model.vo.EnergyDataVO;
import com.smartpark.common.result.PageResult;
import com.smartpark.service.EnergyDataService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class EnergyDataServiceImpl implements EnergyDataService {

    private final EnergyDataMapper energyDataMapper;
    private final DeviceMapper deviceMapper;
    private final BuildingMapper buildingMapper;

    @Override
    public EnergyData getById(Long id) {
        return energyDataMapper.selectById(id);
    }

    @Override
    public PageResult<EnergyDataVO> getPage(EnergyDataQueryDTO query) {
        Page<EnergyData> page = new Page<>(query.getPageNum(), query.getPageSize());
        LambdaQueryWrapper<EnergyData> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(query.getDeviceId() != null, EnergyData::getDeviceId, query.getDeviceId())
               .ge(query.getStartTime() != null, EnergyData::getCollectTime, query.getStartTime())
               .le(query.getEndTime() != null, EnergyData::getCollectTime, query.getEndTime())
               .eq(EnergyData::getDeleteFlag, CommonConstants.DELETE_FLAG_NO)
               .orderByDesc(EnergyData::getCollectTime);
        
        Page<EnergyData> result = energyDataMapper.selectPage(page, wrapper);
        
        List<EnergyDataVO> voList = result.getRecords().stream()
                .map(this::convertToVO)
                .collect(Collectors.toList());
        
        return PageResult.of(result.getTotal(), query.getPageNum(), query.getPageSize(), voList);
    }

    @Override
    public List<EnergyDataVO> getByDeviceId(Long deviceId, Integer limit) {
        LambdaQueryWrapper<EnergyData> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(EnergyData::getDeviceId, deviceId)
               .eq(EnergyData::getDeleteFlag, CommonConstants.DELETE_FLAG_NO)
               .orderByDesc(EnergyData::getCollectTime)
               .last("LIMIT " + limit);
        
        List<EnergyData> list = energyDataMapper.selectList(wrapper);
        return list.stream().map(this::convertToVO).collect(Collectors.toList());
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void save(EnergyData energyData) {
        energyDataMapper.insert(energyData);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    @Async("energyDataExecutor")
    public void saveBatch(List<EnergyData> energyDataList) {
        for (EnergyData energyData : energyDataList) {
            energyDataMapper.insert(energyData);
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteByDeviceId(Long deviceId) {
        LambdaQueryWrapper<EnergyData> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(EnergyData::getDeviceId, deviceId);
        energyDataMapper.delete(wrapper);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void cleanHistoryData(Integer retentionDays) {
        LocalDateTime cutoffTime = LocalDateTime.now().minusDays(retentionDays);
        LambdaQueryWrapper<EnergyData> wrapper = new LambdaQueryWrapper<>();
        wrapper.lt(EnergyData::getCollectTime, cutoffTime);
        energyDataMapper.delete(wrapper);
    }

    private EnergyDataVO convertToVO(EnergyData energyData) {
        EnergyDataVO vo = new EnergyDataVO();
        BeanUtils.copyProperties(energyData, vo);
        
        Device device = deviceMapper.selectById(energyData.getDeviceId());
        if (device != null) {
            vo.setDeviceName(device.getDeviceName());
            Building building = buildingMapper.selectById(device.getBuildingId());
            if (building != null) {
                vo.setBuildingName(building.getBuildingName());
            }
        }
        
        return vo;
    }
}
