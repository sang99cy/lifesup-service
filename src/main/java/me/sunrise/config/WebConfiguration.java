package me.sunrise.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.http.CacheControl;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.servlet.config.annotation.*;
import org.springframework.web.servlet.resource.PathResourceResolver;

import java.util.concurrent.TimeUnit;

@Configuration
@EnableWebMvc
public class WebConfiguration implements WebMvcConfigurer {

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry){
        registry
                .addResourceHandler("/**")
                .addResourceLocations("/resources/","classpath:/static/")
                .addResourceLocations("D:\\DATN\\shopper-backend\\src\\main\\resources\\static")
                .addResourceLocations("D:\\DATN\\shopper-backend\\target\\classes\\static")
                .setCachePeriod(3600)
                .resourceChain(true)
                .addResolver(new PathResourceResolver());
    }

//    @Override
//    public void addResourceHandlers(ResourceHandlerRegistry registry) {
//
//        // Register resource handler for images
//        registry.addResourceHandler("/images/**").addResourceLocations("/WEB-INF/images/")
//                .setCacheControl(CacheControl.maxAge(2, TimeUnit.HOURS).cachePublic());
//    }
}
