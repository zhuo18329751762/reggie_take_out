package com.yangzhuo.reggie.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.yangzhuo.reggie.entity.Dish;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface DishMapper extends BaseMapper<Dish> {
}
