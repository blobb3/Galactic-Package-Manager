package ch.zhaw.devops.gpm.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.lang.NonNull;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class CorsConfig implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(@NonNull CorsRegistry registry) {
        registry.addMapping("/**")
                // Stelle sicher, dass hier keine Wildcard '*' verwendet wird
                .allowedOrigins("http://localhost:3000")
                // Alternativ verwende allowedOriginPatterns
                //.allowedOriginPatterns("*") // Dies funktioniert mit allowCredentials
                .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS")
                .allowedHeaders("*")
                .allowCredentials(true);
    }
}