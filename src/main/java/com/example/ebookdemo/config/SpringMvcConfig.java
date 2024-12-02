package com.example.ebookdemo.config;


import com.example.ebookdemo.interceptor.LoginInterceptor;
import jakarta.annotation.Resource;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class SpringMvcConfig implements WebMvcConfigurer {

//    @Resource
//    LogInterceptor loginInterceptor;

    @Resource
    LoginInterceptor loginInterceptor;

//    @Resource
//    ActionInterceptor actionInterceptor;

    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(loginInterceptor)
                .addPathPatterns("/**")
                .excludePathPatterns(
                        "/user/login",
                        "/user/loginout/**",
                        "/ebook/lists/**",
                        "/category/allData",
                        "/user/lists/**",
//                        "/user/save/**",
//                        "/user/delete/**",
                        "/user/reset-password/**",
                        "/ebook/save/**",
                        "/ebook/delete/",
                        "/category/allData",
                        "/document/allData",
                        "/document/findContent/**",
//                        "/document/save/**",
                        "/document/allData",
                        "/document/all/**",
                        "/document/findContent/**",
                        "/category/allData",
//                        "/category/save/**",
//                        "/category/delete/**",
                        "/category/allData"
                );

//        registry.addInterceptor(actionInterceptor)
//                .addPathPatterns(
//                        "/*/save",
//                        "/*/delete/**",
//                        "/*/reset-password");
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/file/**").addResourceLocations("file:D:/file/wiki/");
    }
}
