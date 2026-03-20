package com.smartpark.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.smartpark.common.constant.CommonConstants;
import com.smartpark.common.exception.BusinessException;
import com.smartpark.dal.mapper.BuildingMapper;
import com.smartpark.dal.mapper.DeviceMapper;
import com.smartpark.model.dto.DeviceDTO;
import com.smartpark.model.entity.Building;
import com.smartpark.model.entity.Device;
import com.smartpark.model.vo.DeviceVO;
import com.smartpark.common.result.PageResult;
import com.smartpark.service.DeviceService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class DeviceServiceImpl implements DeviceService {

    private final DeviceMapper deviceMapper;
    private final BuildingMapper buildingMapper;

    @Override
    public Device getById(Long id) {
        return deviceMapper.selectById(id);
    }

    @Override
    public DeviceVO getDetailById(Long id) {
        Device device = deviceMapper.selectById(id);
        if (device == null) {
            throw new BusinessException("设备信息不存在");
        }
        return convertToVO(device);
    }

    @Override
    public PageResult<DeviceVO> getPage(Integer pageNum, Integer pageSize, Long buildingId, 
                                         String deviceType, String status) {
        Page<Device> page = new Page<>(pageNum, pageSize);
        LambdaQueryWrapper<Device> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(buildingId != null, Device::getBuildingId, buildingId)
               .eq(StringUtils.hasText(deviceType), Device::getDeviceType, deviceType)
               .eq(StringUtils.hasText(status), Device::getStatus, status)
               .eq(Device::getDeleteFlag, CommonConstants.DELETE_FLAG_NO)
               .orderByDesc(Device::getCreateTime);
        
        Page<Device> result = deviceMapper.selectPage(page, wrapper);
        
        List<DeviceVO> voList = result.getRecords().stream()
                .map(this::convertToVO)
                .collect(Collectors.toList());
        
        return PageResult.of(result.getTotal(), pageNum, pageSize, voList);
    }

    @Override
    public List<DeviceVO> getByBuildingId(Long buildingId) {
        LambdaQueryWrapper<Device> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Device::getBuildingId, buildingId)
               .eq(Device::getDeleteFlag, CommonConstants.DELETE_FLAG_NO)
               .orderByAsc(Device::getDeviceCode);
        
        List<Device> list = deviceMapper.selectList(wrapper);
        return list.stream().map(this::convertToVO).collect(Collectors.toList());
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Long save(DeviceDTO dto) {
        LambdaQueryWrapper<Device> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Device::getDeviceCode, dto.getDeviceCode())
               .eq(Device::getDeleteFlag, CommonConstants.DELETE_FLAG_NO);
        if (deviceMapper.selectCount(wrapper) > 0) {
            throw new BusinessException("设备编码已存在");
        }
        
        Building building = buildingMapper.selectById(dto.getBuildingId());
        if (building == null) {
            throw new BusinessException("建筑信息不存在");
        }
        
        Device device = new Device();
        BeanUtils.copyProperties(dto, device);
        device.setStatus(CommonConstants.STATUS_ENABLE);
        deviceMapper.insert(device);
        return device.getId();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void update(DeviceDTO dto) {
        Device existDevice = deviceMapper.selectById(dto.getId());
        if (existDevice == null) {
            throw new BusinessException("设备信息不存在");
        }
        
        if (!existDevice.getDeviceCode().equals(dto.getDeviceCode())) {
            LambdaQueryWrapper<Device> wrapper = new LambdaQueryWrapper<>();
            wrapper.eq(Device::getDeviceCode, dto.getDeviceCode())
                   .ne(Device::getId, dto.getId())
                   .eq(Device::getDeleteFlag, CommonConstants.DELETE_FLAG_NO);
            if (deviceMapper.selectCount(wrapper) > 0) {
                throw new BusinessException("设备编码已存在");
            }
        }
        
        Device device = new Device();
        BeanUtils.copyProperties(dto, device);
        deviceMapper.updateById(device);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteById(Long id) {
        Device device = new Device();
        device.setId(id);
        device.setDeleteFlag(CommonConstants.DELETE_FLAG_YES);
        deviceMapper.updateById(device);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteBatch(List<Long> ids) {
        ids.forEach(this::deleteById);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateStatus(Long id, String status) {
        Device device = new Device();
        device.setId(id);
        device.setStatus(status);
        deviceMapper.updateById(device);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateOnlineStatus(Long id, String onlineStatus) {
        Device device = new Device();
        device.setId(id);
        deviceMapper.updateById(device);
    }

    private DeviceVO convertToVO(Device device) {
        DeviceVO vo = new DeviceVO();
        BeanUtils.copyProperties(device, vo);
        
        Building building = buildingMapper.selectById(device.getBuildingId());
        if (building != null) {
            vo.setBuildingName(building.getBuildingName());
        }
        
        return vo;
    }
}
