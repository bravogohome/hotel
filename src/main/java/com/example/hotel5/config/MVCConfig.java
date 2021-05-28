package com.example.hotel5.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class MVCConfig implements WebMvcConfigurer {
    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/").setViewName("login");
        registry.setOrder(Ordered.HIGHEST_PRECEDENCE);

        registry.addViewController("/index/register").setViewName("register");
        registry.addViewController("/index/login").setViewName("login");

        registry.addViewController("/jump/index").setViewName("index");
        registry.addViewController("/jump/rooms").setViewName("rooms");
        registry.addViewController("/jump/contact").setViewName("contact");

        /*首页三个图片的跳转*/
        registry.addViewController("/jump/details1").setViewName("details1");
        registry.addViewController("/jump/details2").setViewName("details2");
        registry.addViewController("/jump/details3").setViewName("details3");

        registry.addViewController("/jump/person").setViewName("person");

        registry.addViewController("/jump/manageUser").setViewName("manageUser");
        registry.addViewController("/jump/adminLogin").setViewName("adminLogin");

        registry.addViewController("/jump/roomDetail").setViewName("roomDetail");
    }
}
