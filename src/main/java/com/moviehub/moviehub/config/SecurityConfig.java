package com.moviehub.moviehub.config;

import java.util.Arrays;
import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import com.moviehub.moviehub.security.jwt.JwtAuthenticationEntryPoint;
import com.moviehub.moviehub.security.jwt.JwtAuthenticationFilter;

import lombok.RequiredArgsConstructor;

@Configuration(proxyBeanMethods = false)
@EnableWebSecurity
@EnableMethodSecurity(securedEnabled = true, jsr250Enabled = true, prePostEnabled = true)
@RequiredArgsConstructor
public class SecurityConfig {

    private final JwtAuthenticationEntryPoint unauthorizedHandler;
    private final JwtAuthenticationFilter authenticationJwtTokenFilter;

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authConfig) throws Exception {
        return authConfig.getAuthenticationManager();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(12);
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.cors(Customizer.withDefaults())
                .csrf(AbstractHttpConfigurer::disable)
                .exceptionHandling(exception -> exception.authenticationEntryPoint(unauthorizedHandler))
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(auth -> auth
                        // Authentication endpoints - public
                        .requestMatchers("/api/auth/login", "/api/auth/register")
                        .permitAll()

                        // Movie GET endpoints - public read access
                        .requestMatchers(
                                "GET", "/api/movies",
                                "GET", "/api/movies/*",
                                "GET", "/api/movies/search",
                                "GET", "/api/movies/now-showing",
                                "GET", "/api/movies/top-rated")
                        .permitAll()

                        // Movie POST endpoints - require ADMIN or MANAGER role
                        .requestMatchers("POST", "/api/movies")
                        .hasAnyAuthority("ROLE_ADMIN", "ROLE_MANAGER")

                        // Movie PUT endpoints - require ADMIN or MANAGER role
                        .requestMatchers("PUT", "/api/movies/*")
                        .hasAnyAuthority("ROLE_ADMIN", "ROLE_MANAGER")

                        // Movie DELETE endpoints - require ADMIN role only
                        .requestMatchers("DELETE", "/api/movies/*")
                        .hasAuthority("ADMIN")

                        // Swagger documentation - public
                        .requestMatchers("/swagger-ui/**", "/api-docs/**", "/swagger-ui.html/**", "/v3/api-docs/**")
                        .permitAll()

                        // Public endpoints
                        .requestMatchers("/api/public/**")
                        .permitAll()

                        // Actuator endpoints
                        .requestMatchers("/actuator/**")
                        .permitAll()

                        // All other requests require authentication
                        .anyRequest()
                        .authenticated());

        http.addFilterBefore(authenticationJwtTokenFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOriginPatterns(List.of("*"));
        configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE", "OPTIONS"));
        configuration.setAllowedHeaders(Arrays.asList("*"));
        configuration.setAllowCredentials(true);
        configuration.setMaxAge(3600L);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }
}
