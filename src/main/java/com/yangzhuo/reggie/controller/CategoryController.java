package com.yangzhuo.reggie.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yangzhuo.reggie.common.R;
import com.yangzhuo.reggie.entity.Category;
import com.yangzhuo.reggie.entity.Employee;
import com.yangzhuo.reggie.service.CategoryService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * 分类管理
 */
@RestController
@RequestMapping("/category")
@Slf4j
public class CategoryController {
    @Autowired
    private CategoryService categoryService;

    /**
     * 新增菜品分类或套餐
     * @param category
     * @return
     */
    @PostMapping
    public R<String> save(@RequestBody Category category){
        log.info("category {}",category);
        categoryService.save(category);
        return R.success("新增分类成功");
    }


    /**
     * 分页查询
     * @param page
     * @param pageSize
     * @return
     */
    @GetMapping("/page")
    public R<Page> page(int page, int pageSize){
        log.info("page={}pageSize={},name={}",page,pageSize);
        //构造分页构造器
        Page<Category> pageInfo=new Page(page,pageSize);
        //构造条件构造器
        LambdaQueryWrapper<Category> queryWrapper=new LambdaQueryWrapper();
        //添加排序条件
        queryWrapper.orderByAsc(Category::getSort);
        //执行查询
        categoryService.page(pageInfo,queryWrapper);
        return R.success(pageInfo);
    }


    /**
     * 根据id来删除分类
     * @param ids
     * @return
     */
    @DeleteMapping()
    public R<String> deleteById(Long ids){
        log.info("删除分类，ids= {}",ids);

        //categoryService.removeById(ids);
        //删除前要先检查菜品和套餐的关系

        return R.success("分类信息删除成功");
    }

}
