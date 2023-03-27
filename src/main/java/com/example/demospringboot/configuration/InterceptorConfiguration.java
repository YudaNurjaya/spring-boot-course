package com.example.demospringboot.configuration;

import com.example.demospringboot.interceptor.SimpleInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
@Configuration
public class InterceptorConfiguration implements WebMvcConfigurer {
    @Autowired
    SimpleInterceptor simpleInterceptor;
    public void addInterceptors(InterceptorRegistry registry){
        registry.addInterceptor(simpleInterceptor);
    }
}
