package com.attendease.attendease_api.security;

import com.attendease.attendease_api.constant.APIRequestURL;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class securityConfigs {

    private final JwtRequestFilter jwtRequestFilter;

    @Bean
    private SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        httpSecurity
                .csrf(csrf -> csrf.disable())  // disable CSRF for simplicity
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers(APIRequestURL.apiBaseUrl+APIRequestURL.authControllerMapping+APIRequestURL.LoginUrl).permitAll() // allow login API without auth
                        .anyRequest().authenticated() // all other endpoints need authentication
                )
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS));

        httpSecurity.addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class);
        return httpSecurity.build();
    }
}
