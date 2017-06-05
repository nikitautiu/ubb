package rest.services;

import org.springframework.context.annotation.Bean;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

/**
 * Created by vitiv on 6/5/17.
 */
public class CorsFactory {
    public static WebMvcConfigurer invoke() {
        return new WebMvcConfigurerAdapter() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/artists").allowedOrigins("http://localhost:8000");
            }
        };
    }

}
