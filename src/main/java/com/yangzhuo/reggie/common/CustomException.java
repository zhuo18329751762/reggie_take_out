package com.yangzhuo.reggie.common;

/**
 * 自定义业务异常
 */
public class CustomException extends RuntimeException{
    public CustomException(String message){
        //调用父类的构造方法
        super(message);
    }
}
