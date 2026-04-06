package com.modelhub.config;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Value("${app.cors.allowed-origins:http://localhost:3000,http://127.0.0.1:3000}")
    private String allowedOrigins;

    @Value("${app.cors.allowed-methods:GET,POST,PUT,DELETE,OPTIONS}")
    private String allowedMethods;

    @Value("${app.cors.allowed-headers:*}")
    private String allowedHeaders;

    @Value("${app.cors.allow-credentials:false}")
    private boolean allowCredentials;

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        String[] origins = splitAndTrim(allowedOrigins);
        CorsRegistration registration = registry.addMapping("/api/**")
                .allowedMethods(splitAndTrim(allowedMethods))
                .allowedHeaders(splitAndTrim(allowedHeaders))
                .allowCredentials(allowCredentials)
                .maxAge(3600);

        if (Arrays.stream(origins).anyMatch(origin -> origin.contains("*"))) {
            registration.allowedOriginPatterns(origins);
            return;
        }

        registration.allowedOrigins(origins);
    }

    private String[] splitAndTrim(String value) {
        return Arrays.stream(value.split(","))
                .map(String::trim)
                .filter(item -> !item.isEmpty())
                .toArray(String[]::new);
    }

}
