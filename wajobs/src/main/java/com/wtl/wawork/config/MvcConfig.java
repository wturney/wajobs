package com.wtl.wawork.config;

import java.util.List;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import com.wtl.wawork.core.util.WaJobsObjectMapper;

@Configuration
@EnableWebMvc
@ComponentScan(basePackages = { "com.wtl.wawork.rest.controller", "com.wtl.wawork.web.controller" })
public class MvcConfig extends WebMvcConfigurerAdapter {

    @Override
    public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
        super.configureMessageConverters(converters);
        MappingJackson2HttpMessageConverter converter = new MappingJackson2HttpMessageConverter();
        converter.setObjectMapper(new WaJobsObjectMapper());
        converters.add(converter);
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        super.addResourceHandlers(registry);
        registry.addResourceHandler("client/**").addResourceLocations("/resources/");
//        registry.addResourceHandler("/**.tpl.html").addResourceLocations("/resources/");
//        registry.addResourceHandler("/**.html").addResourceLocations("/resources/");
//        registry.addResourceHandler("/client/js/**").addResourceLocations("/resources/js/");
//        registry.addResourceHandler("/css/**").addResourceLocations("/resources/css/");
//        registry.addResourceHandler("/img/**").addResourceLocations("/resources/img/");
    }
}
