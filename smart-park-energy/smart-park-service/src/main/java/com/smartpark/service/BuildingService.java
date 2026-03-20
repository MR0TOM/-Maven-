package com.smartpark.service;

import com.smartpark.model.dto.BuildingDTO;
import com.smartpark.model.entity.Building;
import com.smartpark.model.vo.BuildingVO;
import com.smartpark.common.result.PageResult;

import java.util.List;

public interface BuildingService {

    Building getById(Long id);

    BuildingVO getDetailById(Long id);

    PageResult<BuildingVO> getPage(Integer pageNum, Integer pageSize, String buildingName, String status);

    List<BuildingVO> getList(String buildingName, String status);

    Long save(BuildingDTO dto);

    void update(BuildingDTO dto);

    void deleteById(Long id);

    void deleteBatch(List<Long> ids);

    void updateStatus(Long id, String status);
}
