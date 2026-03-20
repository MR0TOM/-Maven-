package com.smartpark.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.smartpark.common.constant.CommonConstants;
import com.smartpark.common.enums.EnergyTypeEnum;
import com.smartpark.config.kafka.KafkaConfig;
import com.smartpark.mapper.EnergyDataMapper;
import com.smartpark.model.do.EnergyDataDO;
import com.smartpark.model.dto.EnergyDataDTO;
import com.smartpark.model.query.EnergyDataQuery;
import com.smartpark.model.vo.EnergyDataVO;
import com.smartpark.service.EnergyDataService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 能耗数据服务实现类
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class EnergyDataServiceImpl extends ServiceImpl<EnergyDataMapper, EnergyDataDO> implements EnergyDataService {

    private final EnergyDataMapper energyDataMapper;
    private final KafkaConfig.KafkaProducer kafkaProducer;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean addEnergyData(EnergyDataDTO energyDataDTO) {
        EnergyDataDO energyDataDO = BeanUtil.copyProperties(energyDataDTO, EnergyDataDO.class);
        if (energyDataDO.getCollectTime() == null) {
            energyDataDO.setCollectTime(LocalDateTime.now());
        }
        if (energyDataDO.getStatus() == null) {
            energyDataDO.setStatus(0);
        }
        return save(energyDataDO);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean batchAddEnergyData(List<EnergyDataDTO> energyDataDTOList) {
        if (CollUtil.isEmpty(energyDataDTOList)) {
            return false;
        }
        List<EnergyDataDO> energyDataDOList = energyDataDTOList.stream()
                .map(dto -> {
                    EnergyDataDO energyDataDO = BeanUtil.copyProperties(dto, EnergyDataDO.class);
                    if (energyDataDO.getCollectTime() == null) {
                        energyDataDO.setCollectTime(LocalDateTime.now());
                    }
                    if (energyDataDO.getStatus() == null) {
                        energyDataDO.setStatus(0);
                    }
                    return energyDataDO;
                })
                .collect(Collectors.toList());
        return saveBatch(energyDataDOList);
    }

    @Override
    public Page<EnergyDataVO> queryEnergyDataPage(EnergyDataQuery query) {
        // 计算分页起始位置
        int offset = (query.getPageNum() - 1) * query.getPageSize();
        query.setPageNum(offset);

        // 查询数据
        List<EnergyDataDO> energyDataDOList = energyDataMapper.selectByQuery(query);
        List<EnergyDataVO> energyDataVOList = new ArrayList<>();

        if (CollUtil.isNotEmpty(energyDataDOList)) {
            energyDataVOList = energyDataDOList.stream()
                    .map(this::convertToVO)
                    .collect(Collectors.toList());
        }

        // 查询总条数
        long total = count();

        Page<EnergyDataVO> page = new Page<>(query.getPageNum(), query.getPageSize());
        page.setRecords(energyDataVOList);
        page.setTotal(total);

        return page;
    }

    @Override
    public EnergyDataVO getEnergyDataDetail(Long id) {
        EnergyDataDO energyDataDO = getById(id);
        if (energyDataDO == null) {
            return null;
        }
        return convertToVO(energyDataDO);
    }

    @Override
    public BigDecimal getTotalEnergy(Long parkId, Long buildingId, Integer energyType,
                                      LocalDateTime startTime, LocalDateTime endTime) {
        return energyDataMapper.sumEnergyValue(parkId, buildingId, energyType, startTime, endTime);
    }

    @Override
    public List<Map<String, Object>> getHourlyStatistics(Long parkId, Integer energyType,
                                                           LocalDateTime startTime, LocalDateTime endTime) {
        return energyDataMapper.selectHourlyStatistics(parkId, energyType, startTime, endTime);
    }

    @Override
    public List<Map<String, Object>> getDailyStatistics(Long parkId, Integer energyType,
                                                          LocalDateTime startTime, LocalDateTime endTime) {
        return energyDataMapper.selectDailyStatistics(parkId, energyType, startTime, endTime);
    }

    @Override
    public List<Map<String, Object>> getMonthlyStatistics(Long parkId, Integer energyType,
                                                            LocalDateTime startTime, LocalDateTime endTime) {
        return energyDataMapper.selectMonthlyStatistics(parkId, energyType, startTime, endTime);
    }

    @Override
    public void sendEnergyDataToKafka(EnergyDataDTO energyDataDTO) {
        try {
            kafkaProducer.send(CommonConstants.KAFKA_TOPIC_ENERGY_COLLECT, energyDataDTO);
            log.info("能耗数据已发送到Kafka，设备ID: {}", energyDataDTO.getDeviceId());
        } catch (Exception e) {
            log.error("发送能耗数据到Kafka失败，设备ID: {}", energyDataDTO.getDeviceId(), e);
        }
    }

    /**
     * 转换为VO对象
     */
    private EnergyDataVO convertToVO(EnergyDataDO energyDataDO) {
        EnergyDataVO vo = BeanUtil.copyProperties(energyDataDO, EnergyDataVO.class);

        // 设置能源类型名称
        if (vo.getEnergyType() != null) {
            EnergyTypeEnum energyTypeEnum = EnergyTypeEnum.getByCode(vo.getEnergyType());
            if (energyTypeEnum != null) {
                vo.setEnergyTypeName(energyTypeEnum.getName());
            }
        }

        // 设置状态名称
        if (vo.getStatus() != null) {
            vo.setStatusName(vo.getStatus() == 0 ? "正常" : "异常");
        }

        return vo;
    }
}
