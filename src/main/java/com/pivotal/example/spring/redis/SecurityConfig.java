package com.pivotal.example.spring.redis;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.web.server.SecurityWebFilterChain;

@Configuration
@EnableWebFluxSecurity
public class SecurityConfig {

    @Bean
    public SecurityWebFilterChain securityWebFilterChain(ServerHttpSecurity http) {
        return http.csrf().disable()
                .httpBasic()
                .and().authorizeExchange()
                .pathMatchers("/persons").permitAll()
                .pathMatchers("/persons/*").permitAll()
                .pathMatchers("/persons/contains/*").permitAll()
                .and().build();
    }
}
