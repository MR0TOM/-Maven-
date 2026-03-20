package com.smartpark.model.query;

import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 能耗数据查询条件
 */
@Data
public class EnergyDataQuery implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 园区ID
     */
    private Long parkId;

    /**
     * 建筑ID
     */
    private Long buildingId;

    /**
     * 楼层
     */
    private Integer floor;

    /**
     * 能源类型
     */
    private Integer energyType;

    /**
     * 设备ID
     */
    private String deviceId;

    /**
     * 开始时间
     */
    private LocalDateTime startTime;

    /**
     * 结束时间
     */
    private LocalDateTime endTime;

    /**
     * 数据状态
     */
    private Integer status;

    /**
     * 页码
     */
    private Integer pageNum = 1;

    /**
     * 每页条数
     */
    private Integer pageSize = 10;

    /**
     * 排序字段
     */
    private String orderBy = "collect_time";

    /**
     * 排序方向(asc/desc)
     */
    private String orderDirection = "desc";
}
