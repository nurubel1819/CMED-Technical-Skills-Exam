package com.example.prescription.management.system.configuration;

import com.example.prescription.management.system.jwt.JwtAuthFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration {


    @Autowired
    JwtAuthFilter jwtAuthFilter;
    public static final String[] USER_ROLES = {
            "/user/**",
    };
    public static final String[] ADMIN_URLS = {
            "/admin/**",
    };
    public static final String[] PATIENT_URLS = {
            "/users/**",
    };
    public static final String[] DOCTOR_URLS = {
            "/doctor/**"
    };

    public static final String[] SWAGGER_URLS = {
            "/swagger-ui/**",
            "/v3/api-docs/**",
            "/swagger-resources/**",
            "/webjars/**",
            "/swagger-ui.html"
    };
    public static final String[] PUBLIC_URLS = {
            "/",
            "/registration-doctor",
            "/public/**",
            "/css/**",
            "/image/**",
            "/uploads/**",
            "/registration",
            "/login",
            "/user-logout",
    };

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception
    {
        System.out.println("inside Security Filer Chain");
        http.
                csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers(USER_ROLES).hasAnyRole("USER")
                        .requestMatchers(PATIENT_URLS).hasAnyRole("USER","ADMIN")
                        .requestMatchers(ADMIN_URLS).hasAnyRole("ADMIN")
                        .requestMatchers(DOCTOR_URLS).hasAnyRole("DOCTOR","ADMIN")
                        .requestMatchers(PUBLIC_URLS).permitAll()
                        .requestMatchers(SWAGGER_URLS).permitAll()
                        .anyRequest().authenticated())
                .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class)
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                /*.exceptionHandling(ex -> ex
                        .authenticationEntryPoint((request, response, authException) -> {
                            response.sendRedirect("/login");
                        })
                )*/
        ;
        return http.build();
    }
}
