package com.smartpark.service;

import com.smartpark.model.dto.DeviceDTO;
import com.smartpark.model.entity.Device;
import com.smartpark.model.vo.DeviceVO;
import com.smartpark.common.result.PageResult;

import java.util.List;

public interface DeviceService {

    Device getById(Long id);

    DeviceVO getDetailById(Long id);

    PageResult<DeviceVO> getPage(Integer pageNum, Integer pageSize, Long buildingId, String deviceType, String status);

    List<DeviceVO> getByBuildingId(Long buildingId);

    Long save(DeviceDTO dto);

    void update(DeviceDTO dto);

    void deleteById(Long id);

    void deleteBatch(List<Long> ids);

    void updateStatus(Long id, String status);

    void updateOnlineStatus(Long id, String onlineStatus);
}
