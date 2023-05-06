package com.yangzhuo.reggie.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yangzhuo.reggie.entity.OrderDetail;
import com.yangzhuo.reggie.mapper.OrderDetailMapper;
import com.yangzhuo.reggie.service.OrderDetailService;
import org.springframework.stereotype.Service;

@Service
public class OrderDetailServiceImpl extends ServiceImpl<OrderDetailMapper, OrderDetail> implements OrderDetailService {
}
