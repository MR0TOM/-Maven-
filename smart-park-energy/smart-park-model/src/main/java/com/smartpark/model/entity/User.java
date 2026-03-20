package com.smartpark.model.entity;

import com.baomidou.mybatisplus.annotation.*;
import com.smartpark.common.domain.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serial;

@Data
@EqualsAndHashCode(callSuper = true)
@TableName("sp_user")
public class User extends BaseEntity {

    @Serial
    private static final long serialVersionUID = 1L;

    @TableId(type = IdType.ASSIGN_ID)
    private Long id;

    private String username;

    private String password;

    private String realName;

    private String phone;

    private String email;

    private String avatar;

    private String gender;

    private Long deptId;

    private String status;

    @TableLogic
    private String deleteFlag;
}
