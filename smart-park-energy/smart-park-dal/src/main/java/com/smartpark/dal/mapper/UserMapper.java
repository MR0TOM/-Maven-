package com.smartpark.dal.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.smartpark.model.entity.User;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserMapper extends BaseMapper<User> {
}
