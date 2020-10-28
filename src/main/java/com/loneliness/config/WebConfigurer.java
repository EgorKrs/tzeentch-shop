package com.loneliness.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

@Configuration
public class WebConfigurer extends WebMvcConfigurerAdapter {

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry
                .addResourceHandler("js/**")
                .addResourceLocations("/resources/js/")
                .addResourceLocations( "classpath:/js/");
        registry
                .addResourceHandler("css/**")
                .addResourceLocations("resources/css/**")
        .addResourceLocations("classpath:/css/");
        registry
                .addResourceHandler("fonts/**")
                .addResourceLocations("/resources/fonts/")
                .addResourceLocations( "classpath:/fonts/");
        registry
                .addResourceHandler("images/**")
                .addResourceLocations("/resources/images/")
                .addResourceLocations( "classpath:/images/");
        registry
                .addResourceHandler("icons/**")
                .addResourceLocations("/resources/icons/")
                .addResourceLocations( "classpath:/icons/");
        registry
                .addResourceHandler("libs/**")
                .addResourceLocations("/resources/libs/")
                .addResourceLocations( "classpath:/libs/");
    }
}
