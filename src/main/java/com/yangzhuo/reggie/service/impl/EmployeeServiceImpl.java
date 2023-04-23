package com.yangzhuo.reggie.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yangzhuo.reggie.entity.Employee;
import com.yangzhuo.reggie.mapper.EmployeeMapper;
import com.yangzhuo.reggie.service.EmployeeService;
import org.springframework.stereotype.Service;

@Service
public class EmployeeServiceImpl extends ServiceImpl<EmployeeMapper,Employee> implements EmployeeService {
}
