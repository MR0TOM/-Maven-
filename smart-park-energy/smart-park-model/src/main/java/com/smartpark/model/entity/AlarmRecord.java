package com.smartpark.model.entity;

import com.baomidou.mybatisplus.annotation.*;
import com.smartpark.common.domain.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serial;
import java.time.LocalDateTime;

@Data
@EqualsAndHashCode(callSuper = true)
@TableName("sp_alarm_record")
public class AlarmRecord extends BaseEntity {

    @Serial
    private static final long serialVersionUID = 1L;

    @TableId(type = IdType.ASSIGN_ID)
    private Long id;

    private String alarmCode;

    private Long deviceId;

    private Long buildingId;

    private String alarmType;

    private String alarmLevel;

    private String alarmContent;

    private LocalDateTime alarmTime;

    private String alarmStatus;

    private LocalDateTime handleTime;

    private String handleBy;

    private String handleResult;

    private String remark;

    @TableLogic
    private String deleteFlag;
}
