package com.yangzhuo.reggie.dto;


import com.yangzhuo.reggie.entity.Setmeal;
import com.yangzhuo.reggie.entity.SetmealDish;
import lombok.Data;
import java.util.List;

@Data
public class SetmealDto extends Setmeal {

    private List<SetmealDish> setmealDishes;

    private String categoryName;
}
