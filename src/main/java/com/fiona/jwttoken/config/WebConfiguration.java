package com.fiona.jwttoken.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.ArrayList;
import java.util.List;

/**
 * @author fiona fung
 */
@Configuration
public class WebConfiguration {
    /**
     * API拦截器
     */
    @Autowired
    private ApiInterceptor apiInterceptor;

    /**
     * 实例化WebMvcConfigurer接口
     *
     * @return
     */
    @Bean
    public WebMvcConfigurer webMvcConfigurer() {
        return new WebMvcConfigurer() {
            /**
             * 添加拦截器
             * @param registry
             */
            @Override
            public void addInterceptors(InterceptorRegistry registry) {
                registry.addInterceptor(apiInterceptor).addPathPatterns("/**").excludePathPatterns("/login")
                .excludePathPatterns("/test").excludePathPatterns("/test2");
            }

            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/**")
                        .allowedOrigins("*")
                        .allowCredentials(true)
                        .allowedMethods("GET", "POST", "DELETE", "PUT")
                        .maxAge(3600);
            }

            private CorsConfiguration buildConfig() {
                CorsConfiguration corsConfiguration = new CorsConfiguration();
                List<String> list = new ArrayList<>();
                list.add("*");
                corsConfiguration.setAllowedOrigins(list);
                corsConfiguration.addAllowedOrigin("*"); // 1
                corsConfiguration.addAllowedHeader("*"); // 2
                corsConfiguration.addAllowedMethod("*"); // 3
                return corsConfiguration;
            }

            @Bean
            public CorsFilter corsFilter() {
                UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
                source.registerCorsConfiguration("/**", buildConfig()); // 4
                return new CorsFilter(source);
            }
        };
    }
}