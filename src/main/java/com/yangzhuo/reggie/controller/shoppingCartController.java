package com.yangzhuo.reggie.controller;

import com.yangzhuo.reggie.common.R;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/shoppingCart")
public class shoppingCartController {

    @RequestMapping("/list")
    public String list(){
        System.out.println("测试");
        return null;
    }

}
