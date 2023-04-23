package com.zsstudying.interceptor;

import com.alibaba.fastjson2.JSON;
import com.zsstudying.common.utils.JwtUtils;
import com.zsstudying.common.vo.Result;
import lombok.extern.log4j.Log4j;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author ZiShao
 * @since 2023-04-08 17:30
 **/
@Component
@Slf4j
public class JwtValidateInterceptor implements HandlerInterceptor {
    @Autowired
    private JwtUtils jwtUtils;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        String token = request.getHeader("X-Token");
        log.debug(request.getRequestURI() + "需要验证" + token);
        if (token != null) {
            try {
                jwtUtils.parseToken(token);
                log.debug(request.getRequestURI() + "验证通过");
                return true;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        log.debug(request.getRequestURI() + "验证失败,禁止通过。");
        response.setContentType("application/json;charset=utf-8");
        Result<Object> fail = Result.fail(20003, "jwt无效,请重新登录");
        response.getWriter().write(JSON.toJSONString(fail));
        // 拦截
        return false;
    }
}
