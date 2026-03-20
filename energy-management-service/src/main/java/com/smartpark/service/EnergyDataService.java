package com.smartpark.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.smartpark.model.do.EnergyDataDO;
import com.smartpark.model.dto.EnergyDataDTO;
import com.smartpark.model.query.EnergyDataQuery;
import com.smartpark.model.vo.EnergyDataVO;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

/**
 * 能耗数据服务接口
 */
public interface EnergyDataService extends IService<EnergyDataDO> {

    /**
     * 新增能耗数据
     *
     * @param energyDataDTO 能耗数据DTO
     * @return 是否成功
     */
    boolean addEnergyData(EnergyDataDTO energyDataDTO);

    /**
     * 批量新增能耗数据
     *
     * @param energyDataDTOList 能耗数据DTO列表
     * @return 是否成功
     */
    boolean batchAddEnergyData(List<EnergyDataDTO> energyDataDTOList);

    /**
     * 分页查询能耗数据
     *
     * @param query 查询条件
     * @return 分页结果
     */
    Page<EnergyDataVO> queryEnergyDataPage(EnergyDataQuery query);

    /**
     * 根据ID查询能耗数据详情
     *
     * @param id 主键ID
     * @return 能耗数据VO
     */
    EnergyDataVO getEnergyDataDetail(Long id);

    /**
     * 统计总能耗
     *
     * @param parkId      园区ID
     * @param buildingId  建筑ID
     * @param energyType  能源类型
     * @param startTime   开始时间
     * @param endTime     结束时间
     * @return 总能耗
     */
    BigDecimal getTotalEnergy(Long parkId, Long buildingId, Integer energyType,
                               LocalDateTime startTime, LocalDateTime endTime);

    /**
     * 按小时统计能耗
     *
     * @param parkId      园区ID
     * @param energyType  能源类型
     * @param startTime   开始时间
     * @param endTime     结束时间
     * @return 小时能耗统计
     */
    List<Map<String, Object>> getHourlyStatistics(Long parkId, Integer energyType,
                                                   LocalDateTime startTime, LocalDateTime endTime);

    /**
     * 按天统计能耗
     *
     * @param parkId      园区ID
     * @param energyType  能源类型
     * @param startTime   开始时间
     * @param endTime     结束时间
     * @return 天能耗统计
     */
    List<Map<String, Object>> getDailyStatistics(Long parkId, Integer energyType,
                                                  LocalDateTime startTime, LocalDateTime endTime);

    /**
     * 按月统计能耗
     *
     * @param parkId      园区ID
     * @param energyType  能源类型
     * @param startTime   开始时间
     * @param endTime     结束时间
     * @return 月能耗统计
     */
    List<Map<String, Object>> getMonthlyStatistics(Long parkId, Integer energyType,
                                                    LocalDateTime startTime, LocalDateTime endTime);

    /**
     * 发送能耗数据到Kafka
     *
     * @param energyDataDTO 能耗数据
     */
    void sendEnergyDataToKafka(EnergyDataDTO energyDataDTO);
}
