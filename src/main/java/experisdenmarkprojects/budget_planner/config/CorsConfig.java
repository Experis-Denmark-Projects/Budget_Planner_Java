package experisdenmarkprojects.budget_planner.config;

import org.springframework.web.filter.CorsFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

@Configuration
public class CorsConfig {
    @Bean
    public CorsFilter corsFilter(){
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        CorsConfiguration config = new CorsConfiguration();
        config.addAllowedOrigin("http://localhost:4200");
        config.addAllowedOrigin("https://budget-planner-app.azurewebsites.net");
        config.addAllowedHeader("Content-Type");
        config.addAllowedHeader("Authorization");
        config.setAllowCredentials(true);
        config.addAllowedMethod(HttpMethod.GET);
        config.addAllowedMethod(HttpMethod.POST);
        config.addAllowedMethod(HttpMethod.PUT);
        config.addAllowedMethod(HttpMethod.PATCH);
        config.addAllowedMethod(HttpMethod.DELETE);
        source.registerCorsConfiguration("/api/v1/public/**", config);
        source.registerCorsConfiguration("/api/v1/private/**", config);
        source.registerCorsConfiguration("/api/v1/admin/**", config);
        return new CorsFilter(source);
    }
}
