package mg.itu.auth.configuration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.cors(cors -> cors.configurationSource(request -> {
            CorsConfiguration corsConfig = new org.springframework.web.cors.CorsConfiguration();
            corsConfig.addAllowedOrigin("*"); // Allow all origins
            corsConfig.addAllowedMethod("*"); // Allow all HTTP methods
            corsConfig.addAllowedHeader("*"); // Allow all headers
            corsConfig.setAllowCredentials(false); // Disable credentials
            corsConfig.setMaxAge(3600L); // Cache the preflight response for 1 hour
            return corsConfig;
        }));

        http.csrf(csrf -> csrf.disable()); // Disable CSRF (optional, but recommended for APIs)

        http.authorizeHttpRequests(auth -> auth
                .anyRequest().permitAll() // Allow all requests (adjust as needed)
        );

        return http.build();
    }

    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/**")
                        .allowedOrigins("*")
                        .allowedMethods("*")
                        .allowedHeaders("*")
                        .allowCredentials(false)
                        .maxAge(3600);
            }
        };
    }
}