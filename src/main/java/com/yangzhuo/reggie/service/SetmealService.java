package com.yangzhuo.reggie.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.yangzhuo.reggie.dto.SetmealDto;
import com.yangzhuo.reggie.entity.Setmeal;
import com.yangzhuo.reggie.entity.SetmealDish;

import java.util.List;

public interface SetmealService extends IService<Setmeal> {
    /**
     * 新增套餐，同时需要保存套餐和菜品的关联关系
     * @param setmealDto
     */
    public void saveWithDish(SetmealDto setmealDto);

    /**
     * 删除套餐，同时删除套餐与菜品的关联数据
     * @param ids
     */
    public void removeWithDish(List<Long> ids);
}
