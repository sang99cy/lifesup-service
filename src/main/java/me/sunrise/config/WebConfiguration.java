package me.sunrise.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.http.CacheControl;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;

import java.util.concurrent.TimeUnit;

@Configuration
public class WebConfiguration extends WebMvcConfigurationSupport {

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry){
        registry.addResourceHandler("/**")
                .addResourceLocations("classpath:/static/")
                .setCachePeriod(0);
    }

//    @Override
//    public void addResourceHandlers(ResourceHandlerRegistry registry) {
//
//        // Register resource handler for images
//        registry.addResourceHandler("/images/**").addResourceLocations("/WEB-INF/images/")
//                .setCacheControl(CacheControl.maxAge(2, TimeUnit.HOURS).cachePublic());
//    }
}
