package com.yangzhuo.reggie.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yangzhuo.reggie.dto.DishDto;
import com.yangzhuo.reggie.entity.Dish;
import com.yangzhuo.reggie.entity.DishFlavor;
import com.yangzhuo.reggie.mapper.DishMapper;
import com.yangzhuo.reggie.service.DishFlavorService;
import com.yangzhuo.reggie.service.DishService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class DishServiceImpl extends ServiceImpl<DishMapper, Dish> implements DishService {
    @Autowired
    private DishFlavorService dishFlavorService;
    /**
     * 新增菜品，同时保存对应的口味数据
     * @param dishDto
     */
    @Transactional
    public void saveWithFlavor(DishDto dishDto) {
        // 1 保存菜品的基本信息到dish表中
        this.save(dishDto);
        //2.1 获取存储到数据库中的id
        Long dishId = dishDto.getId();
        // 2.2 菜品口味
        List<DishFlavor> flavors = dishDto.getFlavors();
        // 2.3 遍历flavors数组，给每个对象设置id
        //使用流来进行遍历
        flavors.stream().map((item)->{
            item.setDishId(dishId);
            return item;
        }).collect(Collectors.toList());

        //保存菜品口味表到菜品口味表dish_flavor
        dishFlavorService.saveBatch(flavors);
    }
}
