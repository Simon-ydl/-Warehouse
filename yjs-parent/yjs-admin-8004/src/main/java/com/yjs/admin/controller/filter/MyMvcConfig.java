package com.yjs.admin.controller.filter;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @ClassName: MyMvcConfig
 * @Auther：admin
 * @Description:
 * @Date: 2019/12/15 17:29
 * @Version: 1.0.0
 **/
@Configuration
public class MyMvcConfig implements WebMvcConfigurer {

    /**
     * 拦截与放行
     * @return
     */
    @Bean //将组件注册在容器中
    public WebMvcConfigurer webMvcConfigurerAdapter(){
        return new WebMvcConfigurer(){

            //注册拦截器
            @Override
            public void addInterceptors(InterceptorRegistry registry) {
                registry.addInterceptor(new AdminInterceptor())
                        .addPathPatterns("/**").excludePathPatterns("/toLogin","/static/**","/user/code");
            }
        };
    }
}
