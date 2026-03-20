package com.smartpark.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.smartpark.common.constant.CommonConstants;
import com.smartpark.common.exception.BusinessException;
import com.smartpark.common.util.IdGenerator;
import com.smartpark.dal.mapper.AlarmRecordMapper;
import com.smartpark.dal.mapper.BuildingMapper;
import com.smartpark.dal.mapper.DeviceMapper;
import com.smartpark.model.entity.AlarmRecord;
import com.smartpark.model.entity.Building;
import com.smartpark.model.entity.Device;
import com.smartpark.model.vo.AlarmRecordVO;
import com.smartpark.common.result.PageResult;
import com.smartpark.service.AlarmService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AlarmServiceImpl implements AlarmService {

    private final AlarmRecordMapper alarmRecordMapper;
    private final DeviceMapper deviceMapper;
    private final BuildingMapper buildingMapper;

    @Override
    public AlarmRecord getById(Long id) {
        return alarmRecordMapper.selectById(id);
    }

    @Override
    public AlarmRecordVO getDetailById(Long id) {
        AlarmRecord alarmRecord = alarmRecordMapper.selectById(id);
        if (alarmRecord == null) {
            throw new BusinessException("告警记录不存在");
        }
        return convertToVO(alarmRecord);
    }

    @Override
    public PageResult<AlarmRecordVO> getPage(Integer pageNum, Integer pageSize, Long buildingId,
                                              Long deviceId, String alarmLevel, String alarmStatus) {
        Page<AlarmRecord> page = new Page<>(pageNum, pageSize);
        LambdaQueryWrapper<AlarmRecord> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(buildingId != null, AlarmRecord::getBuildingId, buildingId)
               .eq(deviceId != null, AlarmRecord::getDeviceId, deviceId)
               .eq(StringUtils.hasText(alarmLevel), AlarmRecord::getAlarmLevel, alarmLevel)
               .eq(StringUtils.hasText(alarmStatus), AlarmRecord::getAlarmStatus, alarmStatus)
               .eq(AlarmRecord::getDeleteFlag, CommonConstants.DELETE_FLAG_NO)
               .orderByDesc(AlarmRecord::getAlarmTime);
        
        Page<AlarmRecord> result = alarmRecordMapper.selectPage(page, wrapper);
        
        List<AlarmRecordVO> voList = result.getRecords().stream()
                .map(this::convertToVO)
                .collect(Collectors.toList());
        
        return PageResult.of(result.getTotal(), pageNum, pageSize, voList);
    }

    @Override
    public List<AlarmRecordVO> getUnhandledAlarms() {
        LambdaQueryWrapper<AlarmRecord> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(AlarmRecord::getAlarmStatus, "UNHANDLED")
               .eq(AlarmRecord::getDeleteFlag, CommonConstants.DELETE_FLAG_NO)
               .orderByDesc(AlarmRecord::getAlarmLevel)
               .orderByDesc(AlarmRecord::getAlarmTime);
        
        List<AlarmRecord> list = alarmRecordMapper.selectList(wrapper);
        return list.stream().map(this::convertToVO).collect(Collectors.toList());
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Long createAlarm(AlarmRecord alarmRecord) {
        alarmRecord.setAlarmCode("ALM" + IdGenerator.randomCode(12));
        alarmRecord.setAlarmStatus("UNHANDLED");
        alarmRecord.setAlarmTime(LocalDateTime.now());
        alarmRecordMapper.insert(alarmRecord);
        return alarmRecord.getId();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void handleAlarm(Long id, String handleResult, String remark) {
        AlarmRecord alarmRecord = alarmRecordMapper.selectById(id);
        if (alarmRecord == null) {
            throw new BusinessException("告警记录不存在");
        }
        
        alarmRecord.setAlarmStatus("HANDLED");
        alarmRecord.setHandleTime(LocalDateTime.now());
        alarmRecord.setHandleResult(handleResult);
        alarmRecord.setRemark(remark);
        alarmRecordMapper.updateById(alarmRecord);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteBatch(List<Long> ids) {
        ids.forEach(id -> {
            AlarmRecord alarmRecord = new AlarmRecord();
            alarmRecord.setId(id);
            alarmRecord.setDeleteFlag(CommonConstants.DELETE_FLAG_YES);
            alarmRecordMapper.updateById(alarmRecord);
        });
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void checkAndCreateAlarm(Long deviceId, String alarmType, String alarmContent) {
        Device device = deviceMapper.selectById(deviceId);
        if (device == null) {
            return;
        }
        
        AlarmRecord alarmRecord = new AlarmRecord();
        alarmRecord.setDeviceId(deviceId);
        alarmRecord.setBuildingId(device.getBuildingId());
        alarmRecord.setAlarmType(alarmType);
        alarmRecord.setAlarmLevel("WARNING");
        alarmRecord.setAlarmContent(alarmContent);
        
        createAlarm(alarmRecord);
    }

    private AlarmRecordVO convertToVO(AlarmRecord alarmRecord) {
        AlarmRecordVO vo = new AlarmRecordVO();
        BeanUtils.copyProperties(alarmRecord, vo);
        
        Device device = deviceMapper.selectById(alarmRecord.getDeviceId());
        if (device != null) {
            vo.setDeviceName(device.getDeviceName());
        }
        
        Building building = buildingMapper.selectById(alarmRecord.getBuildingId());
        if (building != null) {
            vo.setBuildingName(building.getBuildingName());
        }
        
        return vo;
    }
}
