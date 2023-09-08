package com.example.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@RequiredArgsConstructor
public class Security {

    @Bean
    public SecurityFilterChain config(HttpSecurity http) throws Exception{

        http.formLogin(Customizer.withDefaults());

        return http.build();
    }

}
