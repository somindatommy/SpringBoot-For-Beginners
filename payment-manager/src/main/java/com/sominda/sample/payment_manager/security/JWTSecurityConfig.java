package com.sominda.sample.payment_manager.security;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import org.springframework.security.oauth2.server.resource.authentication.JwtGrantedAuthoritiesConverter;
import org.springframework.security.web.SecurityFilterChain;

import java.util.Collection;

@Profile("JWT")
@Configuration
@EnableWebSecurity
public class JWTSecurityConfig {

    private final String MANAGER_ROLE = "Manager";
    private final String CASHIER_ROLE = "Cashier";

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        http.authorizeHttpRequests(authr -> authr
                                .requestMatchers(HttpMethod.GET, "/api/v1/payments/status/*").permitAll()
                                .requestMatchers(HttpMethod.GET, "/api/v1/payments/authdata").authenticated()
                                .requestMatchers(HttpMethod.GET, "/api/v1/payments").hasAuthority(MANAGER_ROLE)
                                .requestMatchers(HttpMethod.GET, "/api/v1/payments/*").hasAuthority(CASHIER_ROLE)
                                .requestMatchers(HttpMethod.POST, "/api/v1/payments").hasAuthority(CASHIER_ROLE)
                                .anyRequest().authenticated()
                                  )
                .oauth2ResourceServer(oauth2 -> oauth2.jwt(Customizer.withDefaults()));
        return http.build();
    }

    @Bean
    public JwtAuthenticationConverter jwtAuthenticationConverter() {

        JwtGrantedAuthoritiesConverter grantedAuthoritiesConverter = new JwtGrantedAuthoritiesConverter();
        // Need to set the authorize claim name if we are moving away from the default scope or authorities claims.
        grantedAuthoritiesConverter.setAuthoritiesClaimName("roles");
        // Get rid of any prefix expectations. Eg: SCOPE_Manager.
        grantedAuthoritiesConverter.setAuthorityPrefix("");

        JwtAuthenticationConverter jwtAuthenticationConverter = new JwtAuthenticationConverter();
        jwtAuthenticationConverter.setJwtGrantedAuthoritiesConverter(jwt -> {
            Collection<GrantedAuthority> authorities = grantedAuthoritiesConverter.convert(jwt);
            Logger logger = LoggerFactory.getLogger(JwtAuthenticationConverter.class);
            logger.info("Extracted Authorities: {}", authorities);
            return authorities;
        });

        return jwtAuthenticationConverter;
    }
}
