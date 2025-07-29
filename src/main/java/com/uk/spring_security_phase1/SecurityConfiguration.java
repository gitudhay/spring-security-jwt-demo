package com.uk.spring_security_phase1;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import javax.sql.DataSource;

/**
 * Security configuration class.
 */
@Configuration
@EnableWebSecurity
public class SecurityConfiguration {

    public final DataSource dataSource;
    public final MyUserDetailsService userDetailsService;

    public SecurityConfiguration(final DataSource dataSource, final MyUserDetailsService userDetailsService) {
        this.dataSource = dataSource;
        this.userDetailsService = userDetailsService;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return NoOpPasswordEncoder.getInstance();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity httpSecurityIn) throws Exception {
        httpSecurityIn.authorizeHttpRequests(authz -> authz
                .requestMatchers("/admin").hasRole("ADMIN")
                .requestMatchers("/user").hasAnyRole("USER", "ADMIN")
                .requestMatchers("/").permitAll()
                .anyRequest().authenticated()).formLogin(Customizer.withDefaults());
        return httpSecurityIn.build();
    }

}
