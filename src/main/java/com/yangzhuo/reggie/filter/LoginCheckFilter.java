package com.yangzhuo.reggie.filter;


import com.alibaba.fastjson.JSON;
import com.yangzhuo.reggie.common.R;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.AntPathMatcher;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 检查用户是否已经完成登录
 */
@WebFilter(filterName = "loginCheckFilter",urlPatterns = "/*")//添加过滤器注释
@Slf4j
public class LoginCheckFilter implements Filter {
    //路径匹配器，支持通配符
    public static final AntPathMatcher PATH_MATCHER=new AntPathMatcher();
    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request=(HttpServletRequest)servletRequest;
        HttpServletResponse response=(HttpServletResponse)servletResponse;
        // 1 获取本次请求的url
        String requestURI = request.getRequestURI();
        log.info("拦截到请求:{}",request.getRequestURI());
        //将不需要处理的请求路径用数组保存起来
        String[] urls=new String[]{
                "/employee/login",
                "/employee/logout",
                "/backend/**",
                "/front/**"
        };
        // 2 判断本次请求是否需要处理
        boolean check = check(urls, requestURI);
        // 3 如果不需要处理，直接放行
        if(check){
            filterChain.doFilter(request,response);
            log.info("本次不需要处理");
            return;
        }
        // 4 判断登录状态，如果已经登陆，则直接放行
        if(request.getSession().getAttribute("employee")!=null){
            filterChain.doFilter(request,response);
            log.info("以登录");
            return;
        }
        // 5 如果没有登陆，通过输出流方式向客户端响应数据
        log.info("用户未登录");
        response.getWriter().write(JSON.toJSONString(R.error("NOTLOGIN")));
        return;
    }

    /**
     * 路径匹配，检查此次请求是否需要放行
     * @param requestUPI
     * @param urls
     * @return
     */
    public boolean check(String[] urls,String requestUPI){
        for (String url : urls) {
            boolean match = PATH_MATCHER.match(url, requestUPI);
            if(match){
                return true;
            }
        }
        return false;
    }
}
