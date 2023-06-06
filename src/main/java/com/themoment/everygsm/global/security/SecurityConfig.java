package com.themoment.everygsm.global.security;

import com.themoment.everygsm.global.filter.JwtRequestFilter;
import com.themoment.everygsm.global.security.handler.CustomAccessDeniedHandler;
import com.themoment.everygsm.global.security.handler.CustomAuthenticationEntryPointHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {
    private final JwtRequestFilter jwtRequestFilter;

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity httpSecurity)throws Exception {
        httpSecurity
                .csrf().disable();

        httpSecurity
                .cors().disable();

        httpSecurity.authorizeHttpRequests()
                .requestMatchers("/email/**").permitAll()

                .requestMatchers("/user/projects").authenticated()
                .requestMatchers("/user/bookmark/projects").authenticated()
                .requestMatchers("/user/**").permitAll()

                .requestMatchers("/project/all").permitAll()
                .requestMatchers("/project/search").permitAll()
                .requestMatchers("/project/category/**").permitAll()
                .requestMatchers("/project/sort/**").permitAll()
                .requestMatchers("/project/{projectId}/visit").permitAll()

                .requestMatchers("/project/register").authenticated()
                .requestMatchers("/project/{projectId}").authenticated()
                .requestMatchers("/project/{projectId}/heart").authenticated()
                .requestMatchers("/project/{projectId}/bookmark").authenticated()
                .requestMatchers("/project/{projectId}/approve").hasRole("ADMIN")
                .requestMatchers("/project/{projectId}/disapprove").hasRole("ADMIN")

                .anyRequest().authenticated();

        httpSecurity
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS);

        httpSecurity
                .exceptionHandling()
                .accessDeniedHandler(new CustomAccessDeniedHandler())
                .authenticationEntryPoint(new CustomAuthenticationEntryPointHandler());

        httpSecurity
                .addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class);

        return httpSecurity.build();
    }
}
