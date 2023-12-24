package com.example.hci.config;

import com.example.hci.dao.dto.User;
import com.example.hci.exception.MyException;
import com.example.hci.service.IUserService;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Objects;
import java.util.Optional;

@Aspect
@Configuration
@Order(1)
public class AuthAspect {
    private final IUserService service;
    private final JwtConfig jwtConfig;
    @Autowired
    public AuthAspect(IUserService service, JwtConfig jwtConfig) {
        this.service = service;
        this.jwtConfig = jwtConfig;
    }
    @Before(value="execution(public * com.example.hci.controller.*.*.*(..)) && @annotation(authorized)")
    public void authCheck(JoinPoint joinPoint, Authorized authorized){
        try{
            HttpServletRequest httpServletRequest = ((ServletRequestAttributes) Objects.requireNonNull(RequestContextHolder.getRequestAttributes())).getRequest();
            String token = Optional.ofNullable(httpServletRequest.getHeader("token")).orElseThrow(() -> new MyException("A0002", "用户未获得第三方登录授权"));
            User user = service.auth(token);
            Object[] objects = joinPoint.getArgs();
            for(Object o : objects){
                if(o instanceof User){
                    BeanUtils.copyProperties(user, o);
                    break;
                }
            }
        }catch (MyException e){
            throw new MyException("A0004", "认证失败");
        }
    }
}
