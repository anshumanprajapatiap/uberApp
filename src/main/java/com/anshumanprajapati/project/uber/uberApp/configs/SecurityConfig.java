package com.anshumanprajapati.project.uber.uberApp.configs;

import org.springframework.context.annotation.Configuration;
//import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//import org.springframework.security.web.SecurityFilterChain;
//import static org.springframework.security.config.Customizer.withDefaults;

@Configuration
public class SecurityConfig {

//    @Bean
//    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
//        http
//                .authorizeHttpRequests(authorize -> authorize
//                        // This is the crucial part
//                        .requestMatchers(
//                                "/swagger-ui.html",      // The main HTML page
//                                "/swagger-ui/**",        // The CSS, JS, and other assets
//                                "/v3/api-docs/**",       // The OpenAPI JSON specification
//                                "/api/v1/auth/**",      // Allow public access to your signup endpoint
//                                "/api/*"
//                        ).permitAll()
//                        // Secure all other endpoints
//                        .anyRequest().authenticated()
//                )
//                .csrf(csrf -> csrf.disable()) // Often needed for APIs
//                .httpBasic(withDefaults());   // Or your preferred auth method
//
//        return http.build();
//    }
}