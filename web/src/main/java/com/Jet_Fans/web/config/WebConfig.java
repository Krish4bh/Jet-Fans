package com.Jet_Fans.web.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        // Maps /Product-Imgs/** URLs to your actual folder path on disk
        registry.addResourceHandler("/Product-Imgs/**")
                .addResourceLocations("file:D:/Projects/Jet-Fans/web/src/main/resources/static/Product-Imgs/");
    }
}
