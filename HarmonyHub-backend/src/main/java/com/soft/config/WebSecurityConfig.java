package com.soft.config;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.soft.filters.JwtAuthenticationFilter;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class WebSecurityConfig {

	@Bean
    public SecurityFilterChain filterChain(HttpSecurity http,JwtAuthenticationFilter jwtFilter) throws Exception {
        http
        .csrf(csrf -> csrf.disable())
        //This are the public Endpoints anyone can access them without any JWT Tokens
        .authorizeHttpRequests(auth ->auth.requestMatchers("/user/register-user", "/user/login-user").permitAll()

        //This are the admin endpoints that can be only accessed by the ADMINS 
        .requestMatchers("/admin/**").hasRole("ADMIN")

        //This are the Users and connection endpoints that can be accessed only by the user not even by the ADMINS also
        .requestMatchers("/user/**","/connection/**").hasRole("USER")

        //anyRequest().authenticated() means except all this endpoints they require validation that is jwt token
        .anyRequest().authenticated()).addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    //This is used to return the Encrypted password
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

}
