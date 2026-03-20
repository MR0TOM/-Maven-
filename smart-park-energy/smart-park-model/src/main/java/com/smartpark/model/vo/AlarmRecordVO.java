package com.smartpark.model.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@Schema(description = "告警记录VO")
public class AlarmRecordVO implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Schema(description = "告警ID")
    private Long id;

    @Schema(description = "告警编码")
    private String alarmCode;

    @Schema(description = "设备ID")
    private Long deviceId;

    @Schema(description = "设备名称")
    private String deviceName;

    @Schema(description = "建筑ID")
    private Long buildingId;

    @Schema(description = "建筑名称")
    private String buildingName;

    @Schema(description = "告警类型")
    private String alarmType;

    @Schema(description = "告警级别")
    private String alarmLevel;

    @Schema(description = "告警内容")
    private String alarmContent;

    @Schema(description = "告警时间")
    private LocalDateTime alarmTime;

    @Schema(description = "告警状态")
    private String alarmStatus;

    @Schema(description = "处理时间")
    private LocalDateTime handleTime;

    @Schema(description = "处理人")
    private String handleBy;

    @Schema(description = "处理结果")
    private String handleResult;

    @Schema(description = "备注")
    private String remark;
}
