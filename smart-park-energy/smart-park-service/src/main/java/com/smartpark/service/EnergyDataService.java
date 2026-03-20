package com.smartpark.service;

import com.smartpark.model.dto.EnergyDataQueryDTO;
import com.smartpark.model.entity.EnergyData;
import com.smartpark.model.vo.EnergyDataVO;
import com.smartpark.common.result.PageResult;

import java.util.List;

public interface EnergyDataService {

    EnergyData getById(Long id);

    PageResult<EnergyDataVO> getPage(EnergyDataQueryDTO query);

    List<EnergyDataVO> getByDeviceId(Long deviceId, Integer limit);

    void save(EnergyData energyData);

    void saveBatch(List<EnergyData> energyDataList);

    void deleteByDeviceId(Long deviceId);

    void cleanHistoryData(Integer retentionDays);
}
