package com.mileworks.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

/**
 * 自定义资源映射
 * @author long-laptop
 * 2017.11.3
 */
@Configuration
public class ResourcesConfig extends WebMvcConfigurerAdapter {
	
	//默认配置的 /** 映射到 /static （或/public、/resources、/META-INF/resources） ,优先级 META/resources > resources > static > public 
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/statics/**").addResourceLocations("classpath:/statics/");
        registry.addResourceHandler("/swagger/**").addResourceLocations("classpath:/swagger/");
    }
    
}
