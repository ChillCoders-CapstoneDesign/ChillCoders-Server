//package com.project.submate.config;
//
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.web.servlet.config.annotation.CorsRegistry;
//import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
//
//@Configuration
//public class CorsConfig {
//
//    @Bean
//    public WebMvcConfigurer corsConfigurer() {
//        return new WebMvcConfigurer() {
//
//            @Override
//            public void addCorsMappings(CorsRegistry registry) {
//                registry.addMapping("/**") // 전체 API 허용
//                        .allowedOrigins("http://localhost:3000") // 현재 프론트 도메인
//                        .allowedMethods("*")
//                        .allowedHeaders("*")
//                        .allowCredentials(true); // 인증정보 포함 허용
//            }
//        };
//    }
//}
