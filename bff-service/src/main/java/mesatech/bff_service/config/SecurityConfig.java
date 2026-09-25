package mesatech.bff_service.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                // Desactivamos CSRF (innecesario para APIs REST sin sesión con cookies)
                .csrf(csrf -> csrf.disable())

                // Exigimos autenticación para todas las rutas del BFF
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/api/bff/**").authenticated()
                        .anyRequest().authenticated()
                )

                // Habilitamos la validación de JWT via OAuth2 Resource Server
                .oauth2ResourceServer(oauth2 -> oauth2
                        .jwt(jwt -> {})
                );

        return http.build();
    }
}