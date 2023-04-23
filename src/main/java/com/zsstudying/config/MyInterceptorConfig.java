package com.zsstudying.config;

import com.zsstudying.interceptor.JwtValidateInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @author ZiShao
 * @since 2023-04-08 17:22
 **/
@Configuration
public class MyInterceptorConfig implements WebMvcConfigurer {
    @Autowired
    private JwtValidateInterceptor jwtValidateInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        InterceptorRegistration registration = registry.addInterceptor(jwtValidateInterceptor);
        registration.addPathPatterns("/**").excludePathPatterns(
                "/user/login",
                "/user/info",
                "/user/logout",
                "/error",
                "/swagger-ui/**",
                "/swagger-resources/**",
                "/v3/**"

        );

    }
}
