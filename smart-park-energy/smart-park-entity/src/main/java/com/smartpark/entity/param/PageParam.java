package com.smartpark.entity.param;

import com.smartpark.common.constant.CommonConstant;
import lombok.Data;

import java.io.Serializable;

/**
 * 分页参数
 */
@Data
public class PageParam implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 页码
     */
    private Long page = CommonConstant.DEFAULT_PAGE;

    /**
     * 每页条数
     */
    private Long size = CommonConstant.DEFAULT_SIZE;

    /**
     * 获取偏移量
     */
    public Long getOffset() {
        return (page - 1) * size;
    }
}
