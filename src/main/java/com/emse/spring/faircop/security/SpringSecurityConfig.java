package com.emse.spring.faircop.security;

import org.springframework.context.annotation.Bean;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

import static org.springframework.security.config.Customizer.withDefaults;

public class SpringSecurityConfig {
    private static final String ROLE_USER = "USER";
    private static final String ROLE_ADMIN = "ADMIN";


    @Bean
    public UserDetailsService userDetailsService() {
        // We create a password encoder
        PasswordEncoder encoder = PasswordEncoderFactories.createDelegatingPasswordEncoder();
        InMemoryUserDetailsManager manager = new InMemoryUserDetailsManager();
        manager.createUser(
                User.withUsername("user").password(encoder.encode("password")).roles(ROLE_USER).build()
        );
        manager.createUser(
                User.withUsername("admin").password(encoder.encode("admin")).roles(ROLE_ADMIN).build()
        );
        return manager;
    }

    @Bean
    @Order(1) // (1)
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        return http
                .antMatcher("/api/**") // (2)
                .authorizeRequests(authorize -> authorize.anyRequest().authenticated()) // (3)
                .formLogin(withDefaults())
                .httpBasic(withDefaults())
                .build();
    }


    @Bean
    @Order(2)
    public SecurityFilterChain filterChainMain(HttpSecurity http) throws Exception {
        return http
                .authorizeRequests(authorize -> authorize.anyRequest().hasAnyRole(ROLE_USER,ROLE_ADMIN) )
                .formLogin(withDefaults())
                .httpBasic(withDefaults())
                .build();
    }

    @Bean
    @Order(3)
    public SecurityFilterChain filterChainAdmin(HttpSecurity http) throws Exception {
        return http
                .antMatcher("/api/users/**")
                .authorizeRequests(authorize -> authorize.anyRequest().hasRole("ADMIN"))
                .formLogin(withDefaults())
                .httpBasic(withDefaults())
                .build();
    }

}