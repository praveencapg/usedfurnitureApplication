package com.product.controller;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class ImageConfig implements WebMvcConfigurer {

    // Override the addResourceHandlers method to configure resource handling
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        // Add a resource handler for URLs starting with "/images/**"
        registry.addResourceHandler("/images/**")
                // Specify the physical directory where the images are located
                .addResourceLocations("C:/Users/prsurava/Downloads/");
    }
}
