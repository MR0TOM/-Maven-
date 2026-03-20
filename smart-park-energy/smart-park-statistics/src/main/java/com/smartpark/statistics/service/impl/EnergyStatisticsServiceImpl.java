package com.smartpark.statistics.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.smartpark.common.constant.CommonConstants;
import com.smartpark.dal.mapper.BuildingMapper;
import com.smartpark.dal.mapper.DeviceMapper;
import com.smartpark.dal.mapper.EnergyDataMapper;
import com.smartpark.dal.mapper.EnergyStatisticsMapper;
import com.smartpark.model.entity.Building;
import com.smartpark.model.entity.Device;
import com.smartpark.model.entity.EnergyData;
import com.smartpark.model.entity.EnergyStatistics;
import com.smartpark.model.vo.EnergyStatisticsVO;
import com.smartpark.statistics.service.EnergyStatisticsService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class EnergyStatisticsServiceImpl implements EnergyStatisticsService {

    private final EnergyStatisticsMapper energyStatisticsMapper;
    private final EnergyDataMapper energyDataMapper;
    private final DeviceMapper deviceMapper;
    private final BuildingMapper buildingMapper;
    private final RedisTemplate<String, Object> redisTemplate;

    @Override
    public EnergyStatistics getById(Long id) {
        return energyStatisticsMapper.selectById(id);
    }

    @Override
    public List<EnergyStatisticsVO> getByBuildingId(Long buildingId, LocalDate startDate, LocalDate endDate) {
        LambdaQueryWrapper<EnergyStatistics> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(EnergyStatistics::getBuildingId, buildingId)
               .ge(startDate != null, EnergyStatistics::getStatisticsDate, startDate)
               .le(endDate != null, EnergyStatistics::getStatisticsDate, endDate)
               .eq(EnergyStatistics::getDeleteFlag, CommonConstants.DELETE_FLAG_NO)
               .orderByDesc(EnergyStatistics::getStatisticsDate);
        
        List<EnergyStatistics> list = energyStatisticsMapper.selectList(wrapper);
        return list.stream().map(this::convertToVO).collect(Collectors.toList());
    }

    @Override
    public List<EnergyStatisticsVO> getByDeviceId(Long deviceId, LocalDate startDate, LocalDate endDate) {
        LambdaQueryWrapper<EnergyStatistics> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(EnergyStatistics::getDeviceId, deviceId)
               .ge(startDate != null, EnergyStatistics::getStatisticsDate, startDate)
               .le(endDate != null, EnergyStatistics::getStatisticsDate, endDate)
               .eq(EnergyStatistics::getDeleteFlag, CommonConstants.DELETE_FLAG_NO)
               .orderByDesc(EnergyStatistics::getStatisticsDate);
        
        List<EnergyStatistics> list = energyStatisticsMapper.selectList(wrapper);
        return list.stream().map(this::convertToVO).collect(Collectors.toList());
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    @Async("taskExecutor")
    public void generateDailyStatistics(LocalDate date) {
        log.info("开始生成日统计数据: {}", date);
        
        List<Building> buildings = buildingMapper.selectList(
                new LambdaQueryWrapper<Building>()
                        .eq(Building::getDeleteFlag, CommonConstants.DELETE_FLAG_NO)
        );
        
        for (Building building : buildings) {
            generateBuildingDailyStatistics(building.getId(), date);
        }
        
        log.info("日统计数据生成完成: {}", date);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void generateHourlyStatistics(LocalDate date, Integer hour) {
        log.info("开始生成小时统计数据: {} {}:00", date, hour);
        
        LocalDateTime startTime = LocalDateTime.of(date, LocalTime.of(hour, 0, 0));
        LocalDateTime endTime = startTime.plusHours(1);
        
        List<Device> devices = deviceMapper.selectList(
                new LambdaQueryWrapper<Device>()
                        .eq(Device::getDeleteFlag, CommonConstants.DELETE_FLAG_NO)
        );
        
        for (Device device : devices) {
            generateDeviceHourlyStatistics(device, startTime, endTime);
        }
        
        log.info("小时统计数据生成完成: {} {}:00", date, hour);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    @Async("taskExecutor")
    public void generateMonthlyStatistics(Integer year, Integer month) {
        log.info("开始生成月统计数据: {}-{}", year, month);
        
        LocalDate startDate = LocalDate.of(year, month, 1);
        LocalDate endDate = startDate.plusMonths(1).minusDays(1);
        
        List<Building> buildings = buildingMapper.selectList(
                new LambdaQueryWrapper<Building>()
                        .eq(Building::getDeleteFlag, CommonConstants.DELETE_FLAG_NO)
        );
        
        for (Building building : buildings) {
            generateBuildingMonthlyStatistics(building.getId(), startDate, endDate);
        }
        
        log.info("月统计数据生成完成: {}-{}", year, month);
    }

    @Override
    public EnergyStatisticsVO getRealtimeStatistics(Long buildingId) {
        String cacheKey = "smart:park:statistics:realtime:" + buildingId;
        Object cached = redisTemplate.opsForValue().get(cacheKey);
        if (cached != null) {
            return (EnergyStatisticsVO) cached;
        }
        
        LocalDate today = LocalDate.now();
        LocalDateTime startOfDay = today.atStartOfDay();
        LocalDateTime now = LocalDateTime.now();
        
        LambdaQueryWrapper<Device> deviceWrapper = new LambdaQueryWrapper<>();
        deviceWrapper.eq(Device::getBuildingId, buildingId)
                     .eq(Device::getDeleteFlag, CommonConstants.DELETE_FLAG_NO);
        List<Device> devices = deviceMapper.selectList(deviceWrapper);
        
        BigDecimal totalConsumption = BigDecimal.ZERO;
        BigDecimal maxPower = BigDecimal.ZERO;
        
        for (Device device : devices) {
            LambdaQueryWrapper<EnergyData> dataWrapper = new LambdaQueryWrapper<>();
            dataWrapper.eq(EnergyData::getDeviceId, device.getId())
                       .ge(EnergyData::getCollectTime, startOfDay)
                       .le(EnergyData::getCollectTime, now)
                       .eq(EnergyData::getDeleteFlag, CommonConstants.DELETE_FLAG_NO);
            
            List<EnergyData> dataList = energyDataMapper.selectList(dataWrapper);
            
            for (EnergyData data : dataList) {
                if (data.getTotalValue() != null) {
                    totalConsumption = totalConsumption.add(data.getTotalValue());
                }
                if (data.getPower() != null && data.getPower().compareTo(maxPower) > 0) {
                    maxPower = data.getPower();
                }
            }
        }
        
        EnergyStatisticsVO vo = new EnergyStatisticsVO();
        vo.setBuildingId(buildingId);
        vo.setStatisticsDate(today);
        vo.setStatisticsType("REALTIME");
        vo.setTotalConsumption(totalConsumption);
        vo.setMaxPower(maxPower);
        
        Building building = buildingMapper.selectById(buildingId);
        if (building != null) {
            vo.setBuildingName(building.getBuildingName());
        }
        
        redisTemplate.opsForValue().set(cacheKey, vo, 5, TimeUnit.MINUTES);
        
        return vo;
    }

    @Override
    public List<EnergyStatisticsVO> getConsumptionTrend(Long buildingId, String statisticsType,
                                                         LocalDate startDate, LocalDate endDate) {
        LambdaQueryWrapper<EnergyStatistics> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(EnergyStatistics::getBuildingId, buildingId)
               .eq(EnergyStatistics::getStatisticsType, statisticsType)
               .ge(startDate != null, EnergyStatistics::getStatisticsDate, startDate)
               .le(endDate != null, EnergyStatistics::getStatisticsDate, endDate)
               .eq(EnergyStatistics::getDeleteFlag, CommonConstants.DELETE_FLAG_NO)
               .orderByAsc(EnergyStatistics::getStatisticsDate);
        
        List<EnergyStatistics> list = energyStatisticsMapper.selectList(wrapper);
        return list.stream().map(this::convertToVO).collect(Collectors.toList());
    }

    @Override
    public EnergyStatisticsVO getComparison(Long buildingId, String comparisonType) {
        LocalDate today = LocalDate.now();
        LocalDate compareDate;
        
        if ("YOY".equals(comparisonType)) {
            compareDate = today.minusYears(1);
        } else {
            compareDate = today.minusMonths(1);
        }
        
        EnergyStatistics current = getStatisticsByDate(buildingId, today);
        EnergyStatistics previous = getStatisticsByDate(buildingId, compareDate);
        
        EnergyStatisticsVO vo = convertToVO(current);
        
        if (current != null && previous != null && previous.getTotalConsumption() != null) {
            BigDecimal change = current.getTotalConsumption()
                    .subtract(previous.getTotalConsumption())
                    .divide(previous.getTotalConsumption(), 4, RoundingMode.HALF_UP)
                    .multiply(new BigDecimal("100"));
            
            if ("YOY".equals(comparisonType)) {
                vo.setYoyChangeRate(change);
            } else {
                vo.setMomChangeRate(change);
            }
        }
        
        return vo;
    }

    private void generateBuildingDailyStatistics(Long buildingId, LocalDate date) {
        LocalDateTime startOfDay = date.atStartOfDay();
        LocalDateTime endOfDay = date.atTime(23, 59, 59);
        
        List<Device> devices = deviceMapper.selectList(
                new LambdaQueryWrapper<Device>()
                        .eq(Device::getBuildingId, buildingId)
                        .eq(Device::getDeleteFlag, CommonConstants.DELETE_FLAG_NO)
        );
        
        BigDecimal totalConsumption = BigDecimal.ZERO;
        BigDecimal peakConsumption = BigDecimal.ZERO;
        BigDecimal valleyConsumption = BigDecimal.ZERO;
        BigDecimal flatConsumption = BigDecimal.ZERO;
        BigDecimal maxPower = BigDecimal.ZERO;
        BigDecimal totalPower = BigDecimal.ZERO;
        int dataCount = 0;
        
        for (Device device : devices) {
            LambdaQueryWrapper<EnergyData> wrapper = new LambdaQueryWrapper<>();
            wrapper.eq(EnergyData::getDeviceId, device.getId())
                   .ge(EnergyData::getCollectTime, startOfDay)
                   .le(EnergyData::getCollectTime, endOfDay)
                   .eq(EnergyData::getDeleteFlag, CommonConstants.DELETE_FLAG_NO);
            
            List<EnergyData> dataList = energyDataMapper.selectList(wrapper);
            
            for (EnergyData data : dataList) {
                if (data.getTotalValue() != null) {
                    totalConsumption = totalConsumption.add(data.getTotalValue());
                }
                if (data.getPower() != null) {
                    totalPower = totalPower.add(data.getPower());
                    dataCount++;
                    if (data.getPower().compareTo(maxPower) > 0) {
                        maxPower = data.getPower();
                    }
                }
            }
        }
        
        EnergyStatistics statistics = new EnergyStatistics();
        statistics.setBuildingId(buildingId);
        statistics.setStatisticsDate(date);
        statistics.setStatisticsType("DAILY");
        statistics.setTotalConsumption(totalConsumption);
        statistics.setPeakConsumption(peakConsumption);
        statistics.setValleyConsumption(valleyConsumption);
        statistics.setFlatConsumption(flatConsumption);
        statistics.setMaxPower(maxPower);
        
        if (dataCount > 0) {
            statistics.setAvgPower(totalPower.divide(new BigDecimal(dataCount), 4, RoundingMode.HALF_UP));
        }
        
        energyStatisticsMapper.insert(statistics);
    }

    private void generateDeviceHourlyStatistics(Device device, LocalDateTime startTime, LocalDateTime endTime) {
        LambdaQueryWrapper<EnergyData> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(EnergyData::getDeviceId, device.getId())
               .ge(EnergyData::getCollectTime, startTime)
               .lt(EnergyData::getCollectTime, endTime)
               .eq(EnergyData::getDeleteFlag, CommonConstants.DELETE_FLAG_NO);
        
        List<EnergyData> dataList = energyDataMapper.selectList(wrapper);
        
        if (dataList.isEmpty()) {
            return;
        }
        
        BigDecimal totalConsumption = BigDecimal.ZERO;
        BigDecimal maxPower = BigDecimal.ZERO;
        BigDecimal totalPower = BigDecimal.ZERO;
        
        for (EnergyData data : dataList) {
            if (data.getTotalValue() != null) {
                totalConsumption = totalConsumption.add(data.getTotalValue());
            }
            if (data.getPower() != null) {
                totalPower = totalPower.add(data.getPower());
                if (data.getPower().compareTo(maxPower) > 0) {
                    maxPower = data.getPower();
                }
            }
        }
        
        EnergyStatistics statistics = new EnergyStatistics();
        statistics.setDeviceId(device.getId());
        statistics.setBuildingId(device.getBuildingId());
        statistics.setStatisticsDate(startTime.toLocalDate());
        statistics.setStatisticsType("HOURLY");
        statistics.setTotalConsumption(totalConsumption);
        statistics.setMaxPower(maxPower);
        
        if (!dataList.isEmpty()) {
            statistics.setAvgPower(totalPower.divide(new BigDecimal(dataList.size()), 4, RoundingMode.HALF_UP));
        }
        
        energyStatisticsMapper.insert(statistics);
    }

    private void generateBuildingMonthlyStatistics(Long buildingId, LocalDate startDate, LocalDate endDate) {
        LambdaQueryWrapper<EnergyStatistics> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(EnergyStatistics::getBuildingId, buildingId)
               .eq(EnergyStatistics::getStatisticsType, "DAILY")
               .ge(EnergyStatistics::getStatisticsDate, startDate)
               .le(EnergyStatistics::getStatisticsDate, endDate)
               .eq(EnergyStatistics::getDeleteFlag, CommonConstants.DELETE_FLAG_NO);
        
        List<EnergyStatistics> dailyStats = energyStatisticsMapper.selectList(wrapper);
        
        if (dailyStats.isEmpty()) {
            return;
        }
        
        BigDecimal totalConsumption = dailyStats.stream()
                .map(EnergyStatistics::getTotalConsumption)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        
        BigDecimal maxPower = dailyStats.stream()
                .map(EnergyStatistics::getMaxPower)
                .max(BigDecimal::compareTo)
                .orElse(BigDecimal.ZERO);
        
        EnergyStatistics statistics = new EnergyStatistics();
        statistics.setBuildingId(buildingId);
        statistics.setStatisticsDate(startDate);
        statistics.setStatisticsType("MONTHLY");
        statistics.setTotalConsumption(totalConsumption);
        statistics.setMaxPower(maxPower);
        
        energyStatisticsMapper.insert(statistics);
    }

    private EnergyStatistics getStatisticsByDate(Long buildingId, LocalDate date) {
        LambdaQueryWrapper<EnergyStatistics> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(EnergyStatistics::getBuildingId, buildingId)
               .eq(EnergyStatistics::getStatisticsDate, date)
               .eq(EnergyStatistics::getStatisticsType, "DAILY")
               .eq(EnergyStatistics::getDeleteFlag, CommonConstants.DELETE_FLAG_NO)
               .last("LIMIT 1");
        
        return energyStatisticsMapper.selectOne(wrapper);
    }

    private EnergyStatisticsVO convertToVO(EnergyStatistics statistics) {
        EnergyStatisticsVO vo = new EnergyStatisticsVO();
        BeanUtils.copyProperties(statistics, vo);
        
        if (statistics.getBuildingId() != null) {
            Building building = buildingMapper.selectById(statistics.getBuildingId());
            if (building != null) {
                vo.setBuildingName(building.getBuildingName());
            }
        }
        
        if (statistics.getDeviceId() != null) {
            Device device = deviceMapper.selectById(statistics.getDeviceId());
            if (device != null) {
                vo.setDeviceName(device.getDeviceName());
            }
        }
        
        return vo;
    }
}
