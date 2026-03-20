package com.smartpark.common.domain;

import lombok.Data;

import java.io.Serial;
import java.io.Serializable;

@Data
public class PageQuery implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    private Integer pageNum = 1;
    private Integer pageSize = 10;
    private String orderBy;
    private String orderDirection = "DESC";

    public Integer getOffset() {
        return (pageNum - 1) * pageSize;
    }
}
