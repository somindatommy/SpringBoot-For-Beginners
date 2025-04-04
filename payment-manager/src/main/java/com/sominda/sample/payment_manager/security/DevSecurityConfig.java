package com.sominda.sample.payment_manager.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Profile("Dev")
@Configuration
@EnableWebSecurity
public class DevSecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        http.authorizeHttpRequests(authr -> {
            authr.requestMatchers(HttpMethod.POST, "/api/v1/payments" ).permitAll();
            authr.anyRequest().permitAll();
        })
        .csrf(csrf -> csrf.disable());
        return http.build();
    }
}
