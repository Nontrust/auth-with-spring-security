package com.nontrust.authwithspringsecurity.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import java.security.SecureRandom;

@EnableWebSecurity
@Configuration
public class SecurityConfig {
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        // @formatter:on
        return http
                .httpBasic()
                    .disable()
                .csrf()
                    .disable()
                .formLogin()
                    .disable()
                // JWT 사용됨으로 Session 미사용
                .sessionManagement()
                    .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
//                .authorizeHttpRequests(auth ->auth
//                    .requestMatchers("/").permitAll()
//                    .anyRequest().denyAll()
//                )
                .build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        String saltKey = "leaked_salt";
        int factor = 4;

        return new BCryptPasswordEncoder(factor, new SecureRandom(saltKey.getBytes())); // 2^factor
    }
}
