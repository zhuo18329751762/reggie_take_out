package com.yangzhuo.reggie.dto;

import com.yangzhuo.reggie.entity.Dish;
import com.yangzhuo.reggie.entity.DishFlavor;
import lombok.Data;
import java.util.ArrayList;
import java.util.List;

@Data
public class DishDto extends Dish {

    //菜品对应口味数据
    private List<DishFlavor> flavors = new ArrayList<>();

    private String categoryName;

    private Integer copies;
}
