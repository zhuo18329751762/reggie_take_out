package com.yangzhuo.reggie.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.yangzhuo.reggie.common.R;
import com.yangzhuo.reggie.entity.User;
import com.yangzhuo.reggie.service.UserService;
import com.yangzhuo.reggie.utils.SMSUtils;
import com.yangzhuo.reggie.utils.ValidateCodeUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@Slf4j
@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserService userService;
    @Autowired
    private RedisTemplate redisTemplate;

    /**
     * 发送手机验证码
     * @param user
     * @return
     */
    @PostMapping("/sendMsg")
    public R<String> sendMsg(@RequestBody User user, HttpSession session){
        //获取手机号
        String phone = user.getPhone();
        if(StringUtils.isNotEmpty(phone)){
            //生成随机的4位验证码
            String code = ValidateCodeUtils.generateValidateCode(4).toString();
            log.info("code= {}",code);
            //调用阿里云短信服务API完成发送信息
            //SMSUtils.sendMessage("瑞吉外卖","SMS_460710537",phone,code);
            //需要将生成的验证码保存到session中
            //session.setAttribute(phone,code);

            //优化 将生成的验证码保存到redis中，并且设置有效期为5分钟
            redisTemplate.opsForValue().set(phone,code,5, TimeUnit.MINUTES);

            return R.success("手机验证码短信发送成功");
        }
        return R.error("短信发送失败");
    }

    /**
     * 移动端用户登录
     * @param map
     * @return
     */
    @PostMapping("/login")
    public R<User> login(@RequestBody Map map, HttpSession session){
        //获取手机号
        String phone = map.get("phone").toString();
        //获取验证码
        String code = map.get("code").toString();
        //从Session中获取保存的验证码
        //Object codeInSession = session.getAttribute(phone);

        // 优化 从redis中获取验证码
         String codeInSession = (String) redisTemplate.opsForValue().get(phone);

        //进行验证码的比对，(页面提交的和Session中保存的验证码比对)
        if(codeInSession!=null&&codeInSession.equals(code)){
            //如果比对成功，说明登录成功
            LambdaQueryWrapper<User> queryWrapper=new LambdaQueryWrapper<>();
            queryWrapper.eq(User::getPhone,phone);
            User user = userService.getOne(queryWrapper);
            if(user==null){
                //判断当前手机号对应的用户是否为新用户，如果是新用户就自动完成注册并保存
                user=new User();
                user.setPhone(phone);
                user.setStatus(1);
                userService.save(user);
            }
            session.setAttribute("user",user.getId());
            //优化 如果登陆成功，就删除redis中的验证码
            redisTemplate.delete(phone);
            return R.success(user);
        }
        return R.error("登陆失败");
    }
}
