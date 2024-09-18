package com.company.projectmgt.api.config;

import io.jmix.core.JmixSecurityFilterChainOrder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration {

    @Bean
    @Order(JmixSecurityFilterChainOrder.FLOWUI - 10)
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .securityMatcher("/api/**")
                .authorizeHttpRequests(requests -> requests
                        .requestMatchers("/login").permitAll()
                        .anyRequest().hasAnyRole( "ADMIN", "ANONYMOUS", "AUTHENTICATED", "USER")
                )
                .csrf(AbstractHttpConfigurer::disable)
//                .oauth2Login((oauth2 -> oauth2
//                        .loginPage("/oauth2/authorization/google")
//                        .defaultSuccessUrl("/api/v1/", true)
//                        .failureUrl("/login?error=true")
//                ))
                .formLogin(Customizer.withDefaults());

        return http.build();
    }
}