package com.smartpark.service;

import com.smartpark.model.entity.AlarmRecord;
import com.smartpark.model.vo.AlarmRecordVO;
import com.smartpark.common.result.PageResult;

import java.util.List;

public interface AlarmService {

    AlarmRecord getById(Long id);

    AlarmRecordVO getDetailById(Long id);

    PageResult<AlarmRecordVO> getPage(Integer pageNum, Integer pageSize, Long buildingId, 
                                       Long deviceId, String alarmLevel, String alarmStatus);

    List<AlarmRecordVO> getUnhandledAlarms();

    Long createAlarm(AlarmRecord alarmRecord);

    void handleAlarm(Long id, String handleResult, String remark);

    void deleteBatch(List<Long> ids);

    void checkAndCreateAlarm(Long deviceId, String alarmType, String alarmContent);
}
